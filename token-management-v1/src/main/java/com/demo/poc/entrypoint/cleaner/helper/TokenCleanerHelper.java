package com.demo.poc.entrypoint.cleaner.helper;

import com.demo.poc.commons.custom.cache.CacheConstant;
import com.demo.poc.commons.custom.enums.Platform;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanerHelper {

    @CacheEvict(value = CacheConstant.TOKEN_CACHE_NAME, key = "#platform")
    public void cleanToken(Platform platform) {
    }
}
