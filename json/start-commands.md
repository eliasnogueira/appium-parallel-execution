# Start commands
If you want to run this code in your machine you can run the following commands in a new Terminal/Command Prompt.

## Pre-conditions
* Get your IP address
* Open the `android-1.json` file and update the `hubHost` value with your IP
* Open the `android-2.json` file and update the `hubHost` value with your IP
* Open the `ios.json` file and update the `hubHost` value with your IP

## Commands

> Remember that you need to run it in the project folder
>
**1. Start the grid**
Note that I already have the `selenium-server-standalone` in my project folder.
You can do the same or point to the file location.
```
java -jar selenium-server-standalone-3.141.59.jar -role hub -hubConfig json/grid.json
```

**2. Start the first node**
```
appium --nodeconfig json/android-1.json -p 4723 -bp 5523 --default-capabilities '{"udid":"emulator-5554", "systemPort": 8203}'
```

**3. Start the second node**
```
appium --nodeconfig json/android-2.json -p 4724 -bp 5524 --default-capabilities '{"udid":"emulator-5556", "systemPort": 8204}'
```

**4. Start the third node**
Start this one if you are running in a MacOS machine and XCode installed
```
appium --nodeconfig json/ios.json -p 4725 -bp 5525 --default-capabilities '{"udid":"A311ED6A-05A8-4484-BE18-BA87A2B05F5E", "wdaLocalPort": 8205}'
```

## Helpful commands

### Discoveting the iPhone simulator UDID opened
Open the Simulator and run the following command:
```
xcrun simctl list | egrep '(Booted)'
```

### List active Android devices
Start an Android emulator and run the following command:
```
adb devices
```