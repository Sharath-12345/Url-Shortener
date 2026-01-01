package com.example.urlshortener.util;

public final class Base62Util {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private Base62Util() {}

    public static String encode(long id) {
        if (id == 0) {
            return String.valueOf(BASE62.charAt(0));
        }

        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            int index = (int) (id % 62);
            sb.append(BASE62.charAt(index));
            id /= 62;
        }
        return sb.reverse().toString();
    }
}
