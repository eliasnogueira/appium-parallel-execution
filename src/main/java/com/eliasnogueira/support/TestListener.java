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
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

public class TestListener implements ITestListener {

    private Configuration configuration = ConfigurationManager.getConfiguration();

    @Override
    public void onTestStart(ITestResult iTestResult) {
        // empty
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        // empty
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Class clazz = iTestResult.getTestClass().getRealClass();
        try {
            // this field name must be present and equals in any testcase
            Field field = clazz.getDeclaredField("driver");
            field.setAccessible(true);

            AppiumDriver<?> driver = (AppiumDriver<?>) field.get(iTestResult.getInstance());

            File file = driver.getScreenshotAs(OutputType.FILE);

            // the filename is the folder name on test.screenshot.path property plus the completeTestName
            FileUtils.copyFile(file,
                    new File(configuration.screenshotPath() + System.getProperty("file.separator") + composeTestName(iTestResult) + ".png"));

        } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        // empty
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        // empty
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        // empty
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        // empty
    }


    private String composeTestName(ITestResult iTestResult) {
        StringBuffer completeFileName = new StringBuffer();

        completeFileName.append(iTestResult.getTestClass().getRealClass().getSimpleName()); // simplified class name
        completeFileName.append("_");
        completeFileName.append(iTestResult.getName()); // method name

        // all the parameters information
        Object[] parameters = iTestResult.getParameters();
        for(Object parameter : parameters) {
            completeFileName.append("_");
            completeFileName.append(parameter);
        }

        // return the complete name and replace : by - (in the case the emulator have port as device name)
        return completeFileName.toString().replace(":", "-");
    }
}
