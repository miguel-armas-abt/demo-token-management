package com.demo.service.entrypoint.management.helper;

import java.util.List;
import java.util.Map;

import com.demo.service.entrypoint.management.enums.Platform;
import com.demo.service.entrypoint.management.repository.TokenRepository;
import com.demo.service.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCacheHelperV1 implements TokenCacheHelper {

    private static final String CACHE_KEY_PREFIX = "poc.tokens";
    private final List<TokenRepository> tokenRepositories;

    @Cacheable(value = CACHE_KEY_PREFIX, key = "#platform")
    @Override
    public TokenResponseWrapper generateToken(Map<String, String> headers, Platform platform) {
        return this.selectRepository(platform, tokenRepositories).generateToken(headers);
    }

    @CacheEvict(value = CACHE_KEY_PREFIX, key = "#platform")
    @Override
    public void cleanToken(Map<String, String> headers, Platform platform) {
    }

    @Override
    public boolean supports(Class<?> selectedClass) {
        return this.getClass().isAssignableFrom(selectedClass);
    }
}
