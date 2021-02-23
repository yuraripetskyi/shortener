package com.test.shortener.service.impl;

import com.test.shortener.service.ConversionService;
import org.springframework.stereotype.Service;

@Service
public class Base62ConversionService implements ConversionService {
    private static final String allowedSymbols = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private final char[] allowedCharacters = allowedSymbols.toCharArray();
    private final int base = allowedCharacters.length;

    @Override
    public long decode(String input) {
        char[] characters = input.toCharArray();
        int length = characters.length;

        long decoded = 0;

        var counter = 1;
        for (char character : characters) {
            decoded += allowedSymbols.indexOf(character) * Math.pow(base, length - counter);
            counter++;
        }
        return decoded;
    }

    @Override
    public String encode(long input){
        var encodedString = new StringBuilder();

        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }
        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }
        return encodedString.reverse().toString();
    }
}
