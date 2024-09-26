package io.thoughtware.kaelthas;

import org.apache.commons.lang3.RandomStringUtils;

public class Test {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            String random = RandomStringUtils.randomAlphabetic(12);
            System.out.println(random);
        }
    }
}
