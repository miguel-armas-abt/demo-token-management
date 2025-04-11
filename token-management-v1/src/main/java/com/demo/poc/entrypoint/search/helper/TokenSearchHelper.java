package com.demo.poc.entrypoint.search.helper;

import java.util.List;

import com.demo.poc.commons.custom.cache.CacheConstant;
import com.demo.poc.commons.custom.enums.Platform;
import com.demo.poc.commons.custom.exceptions.NoSuchTokenRepositoryException;
import com.demo.poc.entrypoint.search.repository.TokenRepository;
import com.demo.poc.entrypoint.search.repository.wrapper.TokenResponseWrapper;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenSearchHelper {

    private final List<TokenRepository> tokenRepositories;

    @Cacheable(value = CacheConstant.TOKEN_CACHE_NAME, key = "#platform")
    public TokenResponseWrapper getToken(Platform platform) {
        return selectRepository(platform).getToken();
    }

    private TokenRepository selectRepository(Platform platform) {
        return tokenRepositories.stream()
                .filter(tokenRepository -> tokenRepository.supports(platform))
                .findFirst()
                .orElseThrow(NoSuchTokenRepositoryException::new);
    }
}
