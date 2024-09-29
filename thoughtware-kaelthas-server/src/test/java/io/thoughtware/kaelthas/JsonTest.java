package io.thoughtware.kaelthas;

import io.thoughtware.kaelthas.common.util.ConversionDateUtil;

public class JsonTest {
    public static void main(String[] args) {
        String localDateTime = ConversionDateUtil.findLocalDateTime(2, 20, null);
        System.out.println(localDateTime);
    }
}
