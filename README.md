##Appium parallel test execution
Here you'll find out how to execute tests in parallel using Appium and TestNG.

### What you need
1. Java JDK (with _JAVA_HOME_ and _PATH_ configured)
2. IDE (and import this project using Maven)
3. Android SDK (for Android execution | with _ANDROID_HOME_ and _PATH_ configured)
4. Android AVD created (or Genymotion)
5. XCode and an iPhone Simulator (for iOS execution)
6. Appium installed through npm

### About the apps
The Fastip app can be downloaded by this repository
[https://github.com/ptraeg/mobile-apps-4-ways](https://github.com/ptraeg/mobile-apps-4-ways)

I have built the apps for Android and iOS platform, and these are located in app folder.


### Android

#### Configurations
To execute the examples over the Android platform you'll need:
* Android SDK
* Updated _Build Tools_, _Platform Tools_ and, at least, one _System Image (Android Version)_
* Configure Android Path on your environment variables
   * ANDROID_HOME: root android sdk directory
   * PATH: ANDROID_HOME + the following paths = _platform-tools_, _tools_, _tools/bin_ 
* And Android Virtual Device
   * AVD or Genymotion
   

#### Inspect elements on Android
You can use the [uiautomatorviewer](https://developer.android.com/training/testing/ui-testing/uiautomator-testing.html) to inspect elements on Android devices.
 or you can use [Appium Desktop](https://github.com/appium/appium-desktop)

### iOS

#### Configurations
To execute the examples over the iOS platform you'l need:
* MacOS machine :-)
* Xcode installed
* iPhone simulator (I recommend, for these tests iOS version > 10)
* Follow all the steps on [https://github.com/appium/appium-xcuitest-driver](https://github.com/appium/appium-xcuitest-driver)


#### Inspect elementos on iOS
You also can use [Appium Desktop](https://github.com/appium/appium-desktop)
or you can use the [Macaca App Inspector](https://macacajs.github.io/app-inspector/)

#### Execution
On a MacOs machine give write access to _node_modules_:
`sudo chmod -R 777 /usr/local/lib/node_modules`

### Appium
Try to always have Appium and libraries updated.
* Verify the core Appium version on [npm appium site](https://www.npmjs.com/package/appium). To see your Appium version run `appium --version` on Terminal
* Verify the Appium library version on [https://github.com/appium/java-client](https://github.com/appium/java-client)
   * If it differ from _pom.xml_ file, update it!

### Project execution
First you'll need to start the hub and the nodes.
There is a file in _executor_ folder called `launch_grid.sh`. This file open the hub (selenium server) and the nodes.

Each node is configured through a json file in _json_ folder. Remember you gonna need to change some values, like _browserName_, _version_, _platform_, _url_, _host_, _port_, _hubPort_ and _hubHost_

The _port_ information is also linked on `launch_grid.sh` file, that pass this and other informations by parameter.

After change all this information for your execution, execute the suite.xml file.
Each _test_ have 3 parameters:
* platform
* udid
* platformVersion

This parameters are linked to the test files using TestNG, so when you execute the suite.xml file all these parameter will be used on test file.
   
### About the test
On the package _com.eliasnogueira_ you'l find the test script `TipTest.java`_ that uses the information on `config.properties` and `suite.xml`to execute the tests in Android or iOS

### Have trouble?

#### CapabilityNotPresentOnTheGridException
Please, read this post: [https://medium.com/@eliasnogueira/got-capabilitynotpresentonthegridexception-66cbc1aa06b7](https://medium.com/@eliasnogueira/got-capabilitynotpresentonthegridexception-66cbc1aa06b7)

### Any question, error or feedback?
Please fill an issue ;-)



