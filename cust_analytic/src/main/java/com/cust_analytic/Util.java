package com.cust_analytic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class Util {
    static SyncSequenceGenerator  seq = new SyncSequenceGenerator();
    static SyncSequenceGenerator pseq = new SyncSequenceGenerator();

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    
    static String getOrderId() {
        String orderId = "O" + seq.getNext();
        return orderId;
    }

    static String getCustId() {
        int CustIdNum = ThreadLocalRandom.current().nextInt(10000);
        String custId = "C" + CustIdNum;
        return custId;
    }

}
