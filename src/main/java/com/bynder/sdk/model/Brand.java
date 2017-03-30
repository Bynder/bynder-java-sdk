/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.bynder.sdk.api.BynderApi;

/**
 * Brand model returned by {@link BynderApi#getBrands()}.
 */
public class Brand {

    /**
     * Brand id.
     */
    private String id;
    /**
     * Brand name.
     */
    private String name;
    /**
     * Brand description.
     */
    private String description;
    /**
     * URL for the image/logo.
     */
    private String image;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
