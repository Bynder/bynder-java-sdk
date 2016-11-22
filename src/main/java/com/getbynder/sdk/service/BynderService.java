package com.getbynder.sdk.service;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.getbynder.sdk.api.BynderApi;
import com.getbynder.sdk.http.BynderServiceCall;
import com.getbynder.sdk.http.BynderServiceCallback;
import com.getbynder.sdk.util.Constants;
import com.getbynder.sdk.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * @author daniel.sequeira
 */
public class BynderService {

    protected final BynderApi apiService;

    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    public BynderService() {
        this.apiService = Utils.createApiService(BynderApi.class, Constants.BASE_URL, Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET, Constants.ACCESS_TOKEN_KEY, Constants.ACCESS_TOKEN_SECRET);
    }

    protected final <T> BynderServiceCall<T> createServiceCall(final Call<T> call) {
        return new BynderServiceCall<T>() {
            @Override
            public T execute() throws RuntimeException {
                try {
                    return call.execute().body();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public Future<Response<T>> executeAsync(final BynderServiceCallback<? super T> callback) {
                call.enqueue(new Callback<T>() {
                    @Override
                    public void onResponse(final Call<T> call, final Response<T> response) {
                        try {
                            callback.onResponse(response.body());
                        } catch (Exception e) {
                            callback.onFailure(e);
                        }
                    }

                    @Override
                    public void onFailure(final Call<T> call, final Throwable t) {
                        callback.onFailure(new Exception(t));
                    }
                });
                return null;
            }

            // @Override
            // public Observable<Response<T>> reactive() {
            // Future<Response<T>> future = startRequest(call);
            //
            // Callable<Response<T>> callable = new Callable<Response<T>>() {
            // @Override
            // public Response<T> call() throws Exception {
            // return call.execute();
            // }
            // };
            //
            // FutureTask<Response<T>> futureTask = new FutureTask<Response<T>>(callable);
            //
            // pool.execute(futureTask);
            //
            // Observable<Response<T>> observable = Observable.from(future);
            // return observable;
            // }
        };
    }

    private <T> Future<Response<T>> startRequest(final Call<T> call) {
        return pool.submit(new Callable<Response<T>>() {
            @Override
            public Response<T> call() throws Exception {
                return call.execute();
            }
        });
    }
}
