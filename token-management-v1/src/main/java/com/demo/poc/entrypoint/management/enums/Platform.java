package com.demo.poc.entrypoint.management.enums;

import java.util.Arrays;

import com.demo.poc.entrypoint.management.exceptions.InvalidPlatformException;

public enum Platform {
    WEB,
    APP;

    public static Platform parse(String platform) {
        return Arrays.stream(Platform.values())
                .filter(platformType -> platformType.name().equals(platform))
                .findFirst()
                .orElseThrow(InvalidPlatformException::new);
    }
}
