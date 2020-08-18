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
package com.eliasnogueira.driver;

import com.eliasnogueira.driver.manager.AndroidDriverManager;
import com.eliasnogueira.driver.manager.IOSDriverManager;
import com.eliasnogueira.exception.PlatformNotSupportedException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class DriverFactory {

    public AppiumDriver<MobileElement> createInstance(String platform, String udid, String platformVersion) {
        AppiumDriver<MobileElement> driver;
        Platform mobilePlatform = Platform.valueOf(platform.toUpperCase());

        switch (mobilePlatform) {
            case IOS:
                driver = new IOSDriverManager().createInstance(udid, platformVersion);
                break;

            case ANDROID:
                driver = new AndroidDriverManager().createInstance(udid, platformVersion);
                break;

            default:
                throw new PlatformNotSupportedException(
                    "Platform not supported! Check if you set ios or android on the parameter.");
        }
        return driver;
    }
}
