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
package com.eliasnogueira.support;

import com.eliasnogueira.config.Configuration;
import com.eliasnogueira.config.ConfigurationManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.URL;

public class BaseTest {

    public AppiumDriver<?> driver = null;

    @BeforeTest(alwaysRun = true)
    @Parameters( { "platform", "udid", "platformVersion"})
    public void beforeTest(String platform, String udid, String platformVersion) throws Exception {
        Configuration configuration = ConfigurationManager.getConfiguration();

        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);

        // create the complete URL based on config.properties information
        String completeURL = "http://" + configuration.serverIp() + ":" + configuration.serverPort() + "/wd/hub";

        switch (platform.toLowerCase()) {
            case "ios":
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);

                if (configuration.installApp()) {
                    capabilities.setCapability(MobileCapabilityType.APP, new File(configuration.iosAppPath()).getAbsolutePath());
                } else {
                    capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, configuration.iosAppName());
                }

                driver = new IOSDriver<MobileElement>(new URL(completeURL), capabilities);
                break;

            case "android":
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

                if (configuration.installApp()) {
                    capabilities.setCapability(MobileCapabilityType.APP, new File(configuration.androidAppPath()).getAbsolutePath());
                } else {
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, configuration.androidAppPackage());
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, configuration.androidAppActivity());
                }

                driver = new AndroidDriver<MobileElement>(new URL(completeURL), capabilities);
                break;

            default:
                throw new Exception("Platform not supported! Check if you set ios or android on the parameter.");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
