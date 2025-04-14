package com.demo.poc.entrypoint.management.helper;

import java.util.List;

import com.demo.poc.entrypoint.management.constants.CacheConstant;
import com.demo.poc.entrypoint.management.enums.Platform;
import com.demo.poc.entrypoint.management.repository.TokenRepository;
import com.demo.poc.entrypoint.management.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCacheHelperV1 implements TokenCacheHelper {

    private final List<TokenRepository> tokenRepositories;

    @Cacheable(value = CacheConstant.TOKEN_CACHE_NAME, key = "#platform")
    @Override
    public TokenResponseWrapper getToken(Platform platform) {
        return this.selectRepository(platform, tokenRepositories).getToken();
    }

    @CacheEvict(value = CacheConstant.TOKEN_CACHE_NAME, key = "#platform")
    @Override
    public void cleanToken(Platform platform) {
    }

    @Override
    public boolean supports(Class<?> selectedClass) {
        return this.getClass().isAssignableFrom(selectedClass);
    }
}
