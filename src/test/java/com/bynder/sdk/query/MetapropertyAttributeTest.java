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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link MetapropertyAttribute} class methods.
 */
public class MetapropertyAttributeTest {

    public static final String EXPECTED_METAPROPERTY_ID = "metapropertyId";
    public static final String EXPECTED_OPTION1 = "option1";
    public static final String EXPECTED_OPTION2 = "option2";

    @Test
    public void initializeMetapropertyAttribute() {
        MetapropertyAttribute metapropertyAttribute = new MetapropertyAttribute(
            EXPECTED_METAPROPERTY_ID, new String[]{EXPECTED_OPTION1, EXPECTED_OPTION2});

        assertEquals(EXPECTED_METAPROPERTY_ID, metapropertyAttribute.getMetapropertyId());
        assertEquals(2, metapropertyAttribute.getOptionsIds().length);
        assertEquals(EXPECTED_OPTION1, metapropertyAttribute.getOptionsIds()[0]);
        assertEquals(EXPECTED_OPTION2, metapropertyAttribute.getOptionsIds()[1]);
    }
}
