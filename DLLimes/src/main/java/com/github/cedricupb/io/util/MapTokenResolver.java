package com.github.cedricupb.io.util;

import java.util.Map;

/**
 * Created by cedricrichter on 28.04.17.
 */
public class MapTokenResolver implements ITokenResolver {
    private Map<String, String> tokens;

    public MapTokenResolver(Map<String, String> tokens) {
        this.tokens = tokens;
    }

    @Override
    public String resolveToken(String tokenName) {
        return tokens.get(tokenName);
    }
}
