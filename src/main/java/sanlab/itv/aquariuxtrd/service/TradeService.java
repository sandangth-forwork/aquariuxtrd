package sanlab.itv.aquariuxtrd.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sanlab.itv.aquariuxtrd.constant.ESide;
import sanlab.itv.aquariuxtrd.dto.request.TradeRequestDto;
import sanlab.itv.aquariuxtrd.dto.response.WalletResponseDto;
import sanlab.itv.aquariuxtrd.exception.InvalidTradingRequestException;
import sanlab.itv.aquariuxtrd.exception.ServiceUnavailableException;
import sanlab.itv.aquariuxtrd.model.Asset;
import sanlab.itv.aquariuxtrd.model.AuditLog;
import sanlab.itv.aquariuxtrd.repository.crud.AggregatedPriceRepository;
import sanlab.itv.aquariuxtrd.repository.crud.AssetRepository;
import sanlab.itv.aquariuxtrd.repository.crud.AuditLogRepository;
import sanlab.itv.aquariuxtrd.repository.crud.WalletRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final WalletRepository walletRepository;
    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final AssetRepository assetRepository;
    private final AuditLogRepository auditLogRepository;

    public WalletResponseDto getWallet() {
        var wallet = walletRepository.findFirstByOrderByCreatedAt()
            .orElseThrow(() -> new ServiceUnavailableException("Failed to initialize wallet. Reset the application"));
        List<Asset> assets = assetRepository.findAllByUserId(wallet.getUserId());
        var walletDto = WalletResponseDto.builder()
            .userId(wallet.getUserId())
            .balance(wallet.getBalance())
            .updatedAt(wallet.getUpdatedAt())
            .build();
        if (CollectionUtils.isNotEmpty(assets)) {
            walletDto.setAssets(
                assets.stream()
                    .map(inner -> new WalletResponseDto.AssetDto(inner.getSymbol(), inner.getQuantity()))
                    .toList()
            );
        }
        return walletDto;
    }

    @Transactional
    public void executeTrade(TradeRequestDto req) {
        ESide side = ESide.fromStr(req.side());
        String symbol = StringUtils.upperCase(req.symbol());
        var aggregatedPrice = aggregatedPriceRepository.findFirstBySymbolOrderByCreatedAtDesc(symbol)
            .orElseThrow(() -> new InvalidTradingRequestException("Crypto not supported"));
        Asset asset;
        BigDecimal newBalance, newQuantity;
        var wallet = walletRepository.findFirstByOrderByCreatedAt()
            .orElseThrow(() -> new ServiceUnavailableException("Failed to initialize wallet. Reset the application"));
        Optional<Asset> assetOptional = assetRepository.findFirstByUserIdAndSymbol(wallet.getUserId(), symbol);
        BigDecimal marketPrice = ESide.BUY.equals(side) ? aggregatedPrice.getAsk() : aggregatedPrice.getBid();
        BigDecimal total = marketPrice.multiply(req.quantity());
        if (ESide.BUY.equals(side)) {
            if (wallet.getBalance().compareTo(total) < 0) throw new InvalidTradingRequestException("Insufficient balance");
            asset = assetOptional.orElse(Asset.builder()
                .userId(wallet.getUserId())
                .symbol(symbol)
                .quantity(BigDecimal.ZERO)
                .build());
            newBalance = wallet.getBalance().subtract(total);
            newQuantity = asset.getQuantity().add(req.quantity());
        } else {
            asset = assetOptional.orElse(null);
            if (asset == null ||
                asset.getQuantity().compareTo(req.quantity()) < 0
            ) throw new InvalidTradingRequestException("Not enough to sell");
            newBalance = wallet.getBalance().add(total);
            newQuantity = asset.getQuantity().subtract(req.quantity());
        }
        wallet.setBalance(newBalance);
        asset.setQuantity(newQuantity);
        walletRepository.save(wallet);
        assetRepository.save(asset);

        auditLogRepository.save(AuditLog.builder()
            .userId(wallet.getUserId())
            .symbol(symbol)
            .side(side.name())
            .price(marketPrice)
            .quantity(req.quantity())
            .total(total)
            .build());
    }

    public List<AuditLog> getHistory() {
        var wallet = walletRepository.findFirstByOrderByCreatedAt()
            .orElseThrow(() -> new ServiceUnavailableException("Failed to initialize wallet. Reset the application"));
        return auditLogRepository.findAllByUserIdOrderByCreatedAtDesc(wallet.getUserId());
    }

}
