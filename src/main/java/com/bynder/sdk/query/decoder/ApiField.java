/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query.decoder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation class to specify if property needs to be converted and sent as query/attribute
 * parameter to the API.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ApiField {

    String DEFAULT_NAME = "";

    /**
     * @return Name defined in the API for the property.
     */
    String name() default DEFAULT_NAME;

    Class<?> decoder() default void.class;
}
