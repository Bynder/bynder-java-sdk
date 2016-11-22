package com.getbynder.sdk.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.getbynder.sdk.domain.Metaproperty;
import com.getbynder.sdk.http.BynderServiceCall;
import com.getbynder.sdk.util.Utils;

import retrofit2.Call;

/**
 *
 * @author daniel.sequeira
 */
public class MetapropertyService extends BynderService {

    public BynderServiceCall<Map<String, Metaproperty>> getMetaproperties() {
        final Call<Map<String, Metaproperty>> call = apiService.getMetaproperties();

        return createServiceCall(call);
    }

    public BynderServiceCall<Void> addMetapropertyToAsset(final String assetId, final String metapropertyId, final String... optionsIds) {
        Map<String, String> metapropertyOptions = new HashMap<>();
        metapropertyOptions.put(String.format("metaproperty.%s", metapropertyId), StringUtils.join(optionsIds, Utils.STR_COMMA));

        final Call<Void> call = apiService.addMetapropertyToAsset(assetId, metapropertyOptions);

        return createServiceCall(call);
    }
}
