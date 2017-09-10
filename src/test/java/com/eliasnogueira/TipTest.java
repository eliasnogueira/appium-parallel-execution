package com.eliasnogueira;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.testng.Assert.assertEquals;

import com.eliasnogueira.po.MainScreenPageObject;
import com.eliasnogueira.support.Utils;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TipTest {

	private AppiumDriver<?> driver;

	@Test
    @Parameters( { "platform", "udid", "platformVersion"})
	public void testCalculateDefaultTip(String platform, String udid, String platformVersion) throws Exception {
		driver = Utils.returnDriver(platform, udid, platformVersion);

		MainScreenPageObject mainScreen = new MainScreenPageObject(driver);
		
		mainScreen.fillBillAmount("100");
		mainScreen.clickCalculateTip();

        assertEquals("$15.00", mainScreen.getTipAmount());
        assertEquals("$115.00", mainScreen.getTotalAmount());
	}

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
