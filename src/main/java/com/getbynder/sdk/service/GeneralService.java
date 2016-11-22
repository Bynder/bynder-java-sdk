package com.getbynder.sdk.service;

import java.util.List;

import com.getbynder.sdk.domain.Category;
import com.getbynder.sdk.domain.Tag;
import com.getbynder.sdk.http.BynderServiceCall;

import retrofit2.Call;

/**
 *
 * @author daniel.sequeira
 */
public class GeneralService extends BynderService {

    public BynderServiceCall<List<Category>> getCategories() {
        final Call<List<Category>> call = apiService.getCategories();

        return createServiceCall(call);
    }

    public BynderServiceCall<List<Tag>> getTags() {
        final Call<List<Tag>> call = apiService.getTags();

        return createServiceCall(call);
    }
}
