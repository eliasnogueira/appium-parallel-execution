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
package com.eliasnogueira.support;

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

    @Override
    public void onTestStart(ITestResult iTestResult) {

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {

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
                    new File(Utils.readProperty("test.screenshot.path") + "/" + composeTestName(iTestResult) + ".png"));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

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
