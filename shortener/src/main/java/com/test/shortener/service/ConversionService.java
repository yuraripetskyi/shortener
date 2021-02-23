package com.test.shortener.service;

public interface ConversionService {

    long decode(String input);

    String encode(long input);
}
