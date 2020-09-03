/*
 * Copyright (c) 2019 Bynder B.V. All rights reserved.
 *
 * Licensed under the MIT License. See LICENSE file in the project root for full license
 * information.
 *
 * JUnit framework component copyright (c) 2002-2017 JUnit. All Rights Reserved. Licensed under
 * Eclipse Public License - v 1.0. You may obtain a copy of the License at
 * https://www.eclipse.org/legal/epl-v10.html.
 */
package com.bynder.sdk.query;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link UsageQuery} class methods.
 */
public class UsageQueryTest {

    public static final String EXPECTED_ASSET_ID = "assetId";

    private UsageQuery usageQuery;

    @Before
    public void setUp() {
        this.usageQuery = new UsageQuery();
    }

    @Test
    public void initializeEmptyUsageQuery() {
        assertNull(usageQuery.getAssetId());
    }

    @Test
    public void setAssetIdForUsageQuery() {
        usageQuery.setAssetId(EXPECTED_ASSET_ID);
        assertEquals(EXPECTED_ASSET_ID, usageQuery.getAssetId());
    }
}
