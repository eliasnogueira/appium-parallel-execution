/*
 * Copyright 2018 Elias Nogueira
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eliasnogueira;

import static org.testng.Assert.assertEquals;

import com.eliasnogueira.po.MainScreenPageObject;
import com.eliasnogueira.support.BaseTest;

import org.testng.annotations.*;

public class TipTest  extends BaseTest {

	@Test
	public void testCalculateDefaultTip() {

		MainScreenPageObject mainScreen = new MainScreenPageObject(driver);
		
		mainScreen.fillBillAmount("100");
		mainScreen.clickCalculateTip();

        assertEquals("$15.00", mainScreen.getTipAmount());
        assertEquals("$115.00", mainScreen.getTotalAmount());
	}

}
