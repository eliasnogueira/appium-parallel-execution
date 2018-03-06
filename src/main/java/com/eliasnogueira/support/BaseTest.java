package com.eliasnogueira.support;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
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
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);

        // create the complete URL based on config.properties information
        String completeURL = "http://" + Utils.readProperty("run.ip") + ":" + Utils.readProperty("run.port") + "/wd/hub";

        switch (platform.toLowerCase()) {
            case "ios":
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);

                // if iOS 9+ use XCUITest
                if (Boolean.parseBoolean(Utils.readProperty("platform.ios.xcode8"))) {
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                }

                if (Boolean.parseBoolean(Utils.readProperty("install.app"))) {
                    capabilities.setCapability(MobileCapabilityType.APP, new File(Utils.readProperty("app.ios.path")).getAbsolutePath());
                } else {
                    capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, Utils.readProperty("app.ios.appName"));
                }

                driver = new IOSDriver<MobileElement>(new URL(completeURL), capabilities);
                break;

            case "android":
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);

                if (Boolean.parseBoolean(Utils.readProperty("install.app"))) {
                    capabilities.setCapability(MobileCapabilityType.APP, new File(Utils.readProperty("app.android.path")).getAbsolutePath());
                } else {
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Utils.readProperty("app.android.appPackage"));
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Utils.readProperty("app.android.appActivity"));
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
