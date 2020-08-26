package com.bynder.sdk.query.decoder;

import com.bynder.sdk.query.MetapropertyAttribute;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MetapropertyAttributesDecoder implements
        ParameterDecoder<String, List<MetapropertyAttribute>> {

    @Override
    public Map<String, String> decode(final String name, final List<MetapropertyAttribute> metapropertyAttributes) {
        Map<String, String> parameters = new HashMap<>();

        metapropertyAttributes.stream().forEach(metapropertyAttribute -> {
            parameters.put(
                    String.format("%s.%s", name, metapropertyAttribute.getMetapropertyId()),
                    String.join(",", metapropertyAttribute.getOptionsIds())
                          );
        });

        return parameters;
    }
}
