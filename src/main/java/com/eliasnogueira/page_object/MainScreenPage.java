/*
 * MIT License
 *
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.eliasnogueira.page_object;

import com.eliasnogueira.locators.AndroidLocators;
import com.eliasnogueira.locators.IOSLocators;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WithTimeout;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.qameta.allure.Step;
import java.time.temporal.ChronoUnit;
import org.openqa.selenium.support.PageFactory;

public class MainScreenPage {

    @AndroidFindBy(id = AndroidLocators.BILL_AMOUNT)
    @iOSXCUITFindBy(xpath = IOSLocators.BILL_AMOUNT)
    @WithTimeout(time = 10, chronoUnit = ChronoUnit.SECONDS)
    MobileElement billAmount;

    @AndroidFindBy(id = AndroidLocators.CALCULATE_TIP)
    @iOSXCUITFindBy(xpath = IOSLocators.CALCULATE_TIP)
    MobileElement calculateTip;

    @AndroidFindBy(id = AndroidLocators.TIP_AMOUNT)
    @iOSXCUITFindBy(xpath = IOSLocators.TIP_AMOUNT)
    @WithTimeout(time = 10, chronoUnit = ChronoUnit.SECONDS)
    MobileElement tipAmount;

    @AndroidFindBy(id = AndroidLocators.TOTAL_AMOUNT)
    @iOSXCUITFindBy(xpath = IOSLocators.TOTAL_AMOUNT)
    @WithTimeout(time = 10, chronoUnit = ChronoUnit.SECONDS)
    MobileElement totalAmount;

    public MainScreenPage(AppiumDriver<?> driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @Step
    public void fillBillAmount(String amount) {
        billAmount.sendKeys(amount);
    }

    @Step
    public void clickCalculateTip() {
        calculateTip.click();
    }

    @Step
    public String getTipAmount() {
        return tipAmount.getText();
    }

    @Step
    public String getTotalAmount() {
        return totalAmount.getText();
    }

}
