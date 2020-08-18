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
package com.eliasnogueira.driver.manager;

import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;
import static io.appium.java_client.remote.MobileCapabilityType.UDID;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

import com.eliasnogueira.config.Configuration;
import com.eliasnogueira.config.ConfigurationManager;
import com.eliasnogueira.driver.IDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;

public class IOSDriverManager implements IDriver {

    private static final Logger logger = LogManager.getLogger(IOSDriverManager.class);
    private AppiumDriver<MobileElement> driver;

    @Override
    public AppiumDriver<MobileElement> createInstance(String udid, String platformVersion) {
        try {
            Configuration configuration = ConfigurationManager.getConfiguration();
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability(UDID, udid);
            capabilities.setCapability(PLATFORM_VERSION, platformVersion);
            capabilities.setCapability(DEVICE_NAME, "iPhone Simulator");
            capabilities.setCapability(PLATFORM_NAME, MobilePlatform.IOS);
            capabilities.setCapability(AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);

            if (Boolean.TRUE.equals(configuration.installApp())) {
                capabilities.setCapability(APP, new File(configuration.iosAppPath()).getAbsolutePath());
            } else {
                capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, configuration.iosAppName());
            }

            driver = new IOSDriver<>(new URL(gridUrl()), capabilities);
        } catch (MalformedURLException e) {
            logger.error("Failed to initiate the tests for the IOS device", e);
        }

        return driver;
    }
}
