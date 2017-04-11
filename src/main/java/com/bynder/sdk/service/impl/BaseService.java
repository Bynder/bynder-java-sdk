package com.bynder.sdk.service.impl;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by diegobarrerarodriguez on 11/04/17.
 */
public abstract class BaseService {

    public BaseService() {
        RxJavaPlugins.setErrorHandler(e -> { });
    }
}

