package com.bynder.sdk.query.decoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OAuthScopesEncoder implements ParameterDecoder<String, List<String>> {

    @Override
    public Map<String, String> decode(final String name, final List<String> value) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(name, value != null ? String.join(" ", value) : null);
        return parameters;
    }

}
