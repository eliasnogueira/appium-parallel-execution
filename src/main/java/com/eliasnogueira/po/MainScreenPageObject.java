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
package com.eliasnogueira.po;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSFindBy;

public class MainScreenPageObject {

	@AndroidFindBy(id = "org.traeg.fastip:id/billAmtEditText")
	@iOSFindBy(xpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeTextField[1]")
	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	MobileElement billAmount;
	
	@AndroidFindBy(id = "org.traeg.fastip:id/calcTipButton")
	@iOSFindBy(xpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeButton[1]")
	@FindBy(id = "calcTip")
	MobileElement calculateTip;

	@AndroidFindBy(id = "org.traeg.fastip:id/tipAmtTextView")
	@iOSFindBy(xpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[2]")
	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	MobileElement tipAmount;

	@AndroidFindBy(id = "org.traeg.fastip:id/totalAmtTextView")
	@iOSFindBy(xpath = "//XCUIElementTypeApplication[1]/XCUIElementTypeWindow[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeOther[1]/XCUIElementTypeStaticText[4]")
	@WithTimeout(time = 10, unit = TimeUnit.SECONDS)
	MobileElement totalAmount;

	public MainScreenPageObject(AppiumDriver<?> driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	public void fillBillAmount(String amount) {
		billAmount.sendKeys(amount);
	}

	public void clickCalculateTip() {
		calculateTip.click();
	}
	
	public String getTipAmount() {
		return tipAmount.getText();
	}
	
	public String getTotalAmount() {
		return totalAmount.getText();
	}
	
}
