package com.manibala.framework.question;

import org.apache.commons.math3.util.Precision;

public class PrecisionQn {

    public static boolean areAmountsApproximatelyEqual(String amount1, String amount2, double precision) {
        return Precision.equals(
          Double.parseDouble(amount1.replaceAll(",", "")),
          Double.parseDouble(amount2.replaceAll(",", "")),
          precision
        );
    }

}
