/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.model;

import com.bynder.sdk.api.BynderApi;

/**
 * Derivative model returned by {@link BynderApi#getDerivatives()}.
 */
public class Derivative {

    /**
     * Derivative prefix.
     */
    private String prefix;
    /**
     * Derivative width.
     */
    private int width;
    /**
     * Derivative height.
     */
    private int height;
    /**
     * Derivative dpi.
     */
    private int dpi;
    /**
     * Derivative public status.
     */
    private Boolean isPublic;
    /**
     * Derivative crop status.
     */
    private Boolean isCrop;
    /**
     * Derivative on the fly status.
     */
    private Boolean isOnTheFly;
    /**
     * Derivative extent status.
     */
    private Boolean isExtent;
    /**
     * Derivative allowed file types.
     */
    private String allowedTypes;

    public String getPrefix() {
        return prefix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDpi() {
        return dpi;
    }

    public Boolean isPublic() {
        return isPublic;
    }

    public Boolean isCrop() {
        return isCrop;
    }

    public Boolean isOnTheFly() {
        return isOnTheFly;
    }

    public Boolean isExtent() {
        return isExtent;
    }

    public String getAllowedTypes() {
        return allowedTypes;
    }
}
