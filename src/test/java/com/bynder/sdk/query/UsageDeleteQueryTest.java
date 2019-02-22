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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link UsageDeleteQuery} class methods.
 */
public class UsageDeleteQueryTest {

    public static final String EXPECTED_INTEGRATION_ID = "integrationId";
    public static final String EXPECTED_ASSET_ID = "assetId";
    public static final String EXPECTED_LOCATION = "location";

    private UsageDeleteQuery usageDeleteQueryQuery;

    @Before
    public void setUp() {
        this.usageDeleteQueryQuery = new UsageDeleteQuery(EXPECTED_INTEGRATION_ID,
            EXPECTED_ASSET_ID);
    }

    @Test
    public void initializeUsageCreateQuery() {
        assertEquals(EXPECTED_INTEGRATION_ID, usageDeleteQueryQuery.getIntegrationId());
        assertEquals(EXPECTED_ASSET_ID, usageDeleteQueryQuery.getAssetId());
        assertNull(usageDeleteQueryQuery.getLocation());
    }

    @Test
    public void setValuesForUsageCreateQuery() {
        usageDeleteQueryQuery.setLocation(EXPECTED_LOCATION);
        assertEquals(EXPECTED_LOCATION, usageDeleteQueryQuery.getLocation());
    }
}
