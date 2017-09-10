package com.eliasnogueira.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

public class Utils {

	/*
	 * Read a property from the config.properties file and return the value
     * If value not exist return a 'Value not set or empty' exception
	 */
	public static String readProperty(String property) {
		Properties prop;
		String value = null;
		try {
			prop = new Properties();
			prop.load(new FileInputStream(new File("config.properties")));
			
			value = prop.getProperty(property);
			
			if (value == null || value.isEmpty()) {
				throw new Exception("Value not set or empty");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	/*
	 * Return the proper driver based on platform parameter (android or ios)
	 * Also set the udid and platform version to be able to execute in a grid environment
	 */
	public static AppiumDriver<?> returnDriver(String platform, String udid, String platformVersion) throws Exception {
		AppiumDriver<?> driver;
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);

		// create the complete URL based on config.properties information
		String completeURL = "http://" + Utils.readProperty("run.ip") + ":" + Utils.readProperty("run.port") + "/wd/hub";

        switch (platform.toLowerCase()) {
            case "ios":
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
                capabilities.setCapability(MobileCapabilityType.PLATFORM, MobilePlatform.IOS);

                // if iOS 9+ use XCUITest
                if (Boolean.parseBoolean(Utils.readProperty("platform.ios.xcode8"))) {
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                }

                if (Boolean.parseBoolean(Utils.readProperty("install.app"))) {
                    capabilities.setCapability(MobileCapabilityType.APP, new File(Utils.readProperty("app.ios.path")).getAbsolutePath());
                } else {
                    capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, Utils.readProperty("app.ios.appName"));
                }

                driver = new IOSDriver<>(new URL(completeURL), capabilities);
                break;

		case "android":
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(MobileCapabilityType.PLATFORM, MobilePlatform.ANDROID);

            if (Boolean.parseBoolean(Utils.readProperty("install.app"))) {
                capabilities.setCapability(MobileCapabilityType.APP, new File(Utils.readProperty("app.android.path")).getAbsolutePath());
            } else {
                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, Utils.readProperty("app.android.appPackage"));
                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, Utils.readProperty("app.android.appActivity"));
            }

            driver = new AndroidDriver<>(new URL(completeURL), capabilities);
            break;

         default:
             throw new Exception("Platform not supported! Check if you set ios or android on the parameter.");
        }

		return driver;
	}
}
