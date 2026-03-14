package sanlab.itv.aquariuxtrd.constant;

import org.apache.commons.lang3.StringUtils;

public enum ESide {

    BUY, SELL;

    public static ESide fromStr(String side) {
        String sideUpper = StringUtils.upperCase(side);
        try {
            return ESide.valueOf(sideUpper);
        } catch (IllegalArgumentException e) {
            return ESide.BUY;
        }
    }

}
