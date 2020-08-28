package com.bynder.sdk.query.decoder;

import com.bynder.sdk.query.MetapropertyAttribute;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class MetapropertyAttributesDecoder implements
        ParameterDecoder<String, List<MetapropertyAttribute>> {

    @Override
    public Map<String, String> decode(final String name, final List<MetapropertyAttribute> metapropertyAttributes) {
       return metapropertyAttributes.stream().collect(Collectors.toMap(
                metapropertyAttribute -> String.format("%s.%s", name, metapropertyAttribute.getMetapropertyId()),
                metapropertyAttribute -> String.join(",", metapropertyAttribute.getOptionsIds())
       ));
    }
}
