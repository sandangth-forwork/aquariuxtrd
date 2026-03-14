package sanlab.itv.aquariuxtrd.controller.exceptionadvisor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sanlab.itv.aquariuxtrd.dto.response.ErrorResponseDto;
import sanlab.itv.aquariuxtrd.exception.AbstractException;
import sanlab.itv.aquariuxtrd.exception.DataNotFoundException;
import sanlab.itv.aquariuxtrd.exception.InvalidTradingRequestException;
import sanlab.itv.aquariuxtrd.exception.ServiceUnavailableException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler({DataNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> notFound(AbstractException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.message()), NOT_FOUND);
    }

    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity<ErrorResponseDto> serviceUnavailable(AbstractException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.message()), SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler({InvalidTradingRequestException.class})
    public ResponseEntity<ErrorResponseDto> badRequest(AbstractException ex) {
        return new ResponseEntity<>(new ErrorResponseDto(ex.message()), BAD_REQUEST);
    }

}
