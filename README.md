# Appium parallel test execution
A lean, up-to-date, and cross-platform solution to run your mobile tests into a Selenium Grid using Appium.

* [How you can use this repo](#how-you-can-use-this-repo)
* [Technical Decisions](#technical-decisions)
   * [Languages and Frameworks](#languages-and-frameworks)
   * [Test architecture](#test-architecture)
* [Pre-conditions](#pre-conditions)
   * [What you need](#what-you-need)
      * [Android specific](#android-specific)
         * [Configurations](#configurations)
         * [Inspect elements on Android](#inspect-elements-on-android)
      * [iOS specific](#ios-specific)
         * [Configurations](#configurations-1)
         * [Inspect elements on iOS](#inspect-elements-on-ios)
* [About the apps](#about-the-apps)
* [How to run the tests](#how-to-run-the-tests)
   * [Pre-conditions](#pre-conditions-1)
   * [Appium](#appium)
   * [Project execution](#project-execution)
   * [About the test](#about-the-test)
   * [Having trouble?](#having-trouble)
   * [Any question, error or feedback?](#any-question-error-or-feedback)

## How you can use this repo
You can use this repo as a start point to implement your own cross-platform parallel execution in a grid.
It uses different libraries and design pater to make it lean, easy to maintain and extend.

## Technical Decisions
This section will show to you the libraries, frameworks and design decisions that made a lean architecture possible.

### Languages and Frameworks

This project using the following languages and frameworks:

* [Java 11](https://openjdk.java.net/projects/jdk/11/) as the programming language
* [TestNG](https://testng.org/doc/) as the UnitTest framework to support the test creation
* [Appium](http://appium.io/) as the mobile test automation framework
* [Seleium Grid](https://www.selenium.dev/documentation/en/grid/) as the grid and paralellism solution
* [AsseertJ](https://joel-costigliola.github.io/assertj/) as the fluent assertion library
* [Allure Report](https://docs.qameta.io/allure/) as the testing report strategy
* [Log4J2](https://logging.apache.org/log4j/2.x/) as the logging manage strategy
* [Owner](http://owner.aeonbits.org/) to minimize the code to handle the properties file

### Test architecture

We know that any automation project starting with a good test architecture.
This project can be your initial test architecture for a faster start.
You will see the following items in this architecture:

* Page Objects pattern
* Factory
* BaseTest
* Logging
* Configuration files
* Parallel execution

## Pre-conditions

### What you need
1. Java JDK 11 installed and configured (with `JAVA_HOME` and `PATH` configured)
2. IDE (to import this project using Maven)
3. Android SDK (for Android execution | with `ANDROID_HOME` and `PATH` configured)
4. Android AVD created (or Genymotion)
5. XCode and the iPhone Simulator (for iOS execution)
6. Appium installed through npm

#### Android specific

##### Configurations
To execute the examples over the Android platform you'll need:
* Android SDK
* Updated _Build Tools_, _Platform Tools_ and, at least, one _System Image (Android Version)_
* Configure Android Path on your environment variables
   * ANDROID_HOME: root android sdk directory
   * PATH: ANDROID_HOME + the following paths = _platform-tools_, _tools_, _tools/bin_ 
* And Android Virtual Device
   * AVD or Genymotion
   
##### Inspect elements on Android
You can use the [uiautomatorviewer](https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html) to inspect elements on Android devices.
 or you can use [Appium Desktop](https://github.com/appium/appium-desktop)

#### iOS specific

##### Configurations
To execute the examples over the iOS platform you'll need:
* MacOS machine :-)
* Xcode installed
* iPhone simulator (I recommend, for these tests iOS version > 10)
* Follow all the steps on [https://github.com/appium/appium-xcuitest-driver](https://github.com/appium/appium-xcuitest-driver)

##### Inspect elements on iOS
You also can use [Appium Desktop](https://github.com/appium/appium-desktop)
or you can use the [Macaca App Inspector](https://macacajs.github.io/app-inspector/)

## About the apps
The Fastip app can be downloaded by this repository
[https://github.com/ptraeg/mobile-apps-4-ways](https://github.com/ptraeg/mobile-apps-4-ways)

I have built the apps for Android and iOS platform, and these are located in app folder.

## How to run the tests

### Pre-conditions
On a MacOs machine give write access to _node_modules_:
`sudo chmod -R 777 /usr/local/lib/node_modules`

### Appium
Try to always have Appium and libraries updated.
* verify the core Appium version on [npm appium site](https://www.npmjs.com/package/appium). To see your Appium version run `appium --version` on the Terminal/Command Prompt
* verify the Appium library version on [https://github.com/appium/java-client](https://github.com/appium/java-client)
   * if it differs from `pom.xml` file, update it!

### Project execution
First you'll need to start the hub and the nodes.
Please read the [Start commands](https://github.com/eliasnogueira/appium-parallel-execution/blob/master/json/start-commands.md) to lear now to start it.

Each node is configured through a json file in _json_ folder.
Remember you gonna need to change some values, like `browserName`, `version`, `platform`, `url`, `host`, `port`, `hubPort` and `hubHost`.

The _port_ information is also linked on `launch_grid.sh` file, that pass this and other informations by parameter.

After change all this information for your execution, execute the suite.xml file.
Each _test_ have 3 parameters:
* platform
* udid
* platformVersion

These parameters are linked to the test files using TestNG, so when you execute the suite.xml file all these parameters will be used on test file.
   
### About the test
On the package `com.eliasnogueira` you'll find the test script `TipTest.java` that uses the information on `config.properties` and `suite.xml`to execute the tests in Android or iOS

### Having trouble?

#### CapabilityNotPresentOnTheGridException
Please, read this post: [https://medium.com/@eliasnogueira/got-capabilitynotpresentonthegridexception-66cbc1aa06b7](https://medium.com/@eliasnogueira/got-capabilitynotpresentonthegridexception-66cbc1aa06b7)

### Any question, error or feedback?
Please fill an [issue](https://github.com/eliasnogueira/appium-parallel-execution/issues)



