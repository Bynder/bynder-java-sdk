/**
 * Copyright (c) 2017 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 */
package com.bynder.sdk.query;

public class MetapropertyQuery {

    private Boolean count;

    public MetapropertyQuery(final Boolean count) {
        this.count = count;
    }

    public Boolean getCount() {
        return count;
    }
}
