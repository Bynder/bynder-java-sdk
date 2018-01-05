/*
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation class to specify if property needs to be converted and sent as query/field parameter
 * to the API.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ApiField {

    /**
     * @return Name of the property defined in the API.
     */
    String name();

    /**
     * @return ConversionType to be used to convert the property.
     */
    ConversionType conversionType() default ConversionType.NONE;
}
