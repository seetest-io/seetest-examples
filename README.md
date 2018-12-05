# seetest.io - Seetest Examples

This project demonstrates various tests like Android Test, IOS Test, Browser test using Java code snippets.
Listed below are test and short descriptions.

1. WebUrlSpecificDeviceTest - Seetest example to load a URL in a browser in a specific unreserved device in seetest cloud.
2. WebUrlDeviceTest - Seetest example to load a URL in a browser any device (IOS/Android) in seetest cloud.
3. WebUrlResevedDeviceTest - Seetest example to load a URL in a browser in a specific reserved device in seetest cloud.
4. IOSApplicationTest - Example Test for Sample App EriBank on any IOS Device in seetest cloud.
5. AppReservedDeviceTest - Example Test for Sample App EriBank on a specific device for Android/IOS OS in seetest cloud.
6. AndroidApplicationTest - Example Test for Sample App EriBank on any Android Device in seetest cloud.

### Steps to run demo test

1. Clone this git repository

	```
	git clone
	```

2. Obtain access key from seetest.io cloud

    https://docs.seetest.io/display/SEET/Execute+Tests+on+SeeTest+-+Obtaining+Access+Key

    note :  you need to have a valid subscription or a trial to run this test (Trial \ paid)

3. Upload the eribank application to your project
    Download the Android app : https://experitest.s3.amazonaws.com/eribank.apk
    Download the iOS app : https://experitest.s3.amazonaws.com/EriBank.ipa

    Go to the cloud "Mobile Application Center" and upload both apps
    https://cloud.seetest.io/index.html#/applications

4. For tests WebUrlResevedDeviceTest, AppReservedDeviceTest, Set DEVICE_ID in code with the device id from the seetest cloud.

5. For test WebUrlSpecificDeviceTest, Set SERIAL_NUMBER in the code with the device id got from the seetest cloud.

6. To run the tests,
    Please ensure that following environment variables are set.

    1. JAVA_HOME to JDK/JRE HOME and update it in the PATH variable.
    2. Set the variable ACCESS_KEY in the code with your access key (got from  step 2)

    Run all tests using following command in command line.
    ```
    gradlew runTests
    ```

    To Run Individual tests. See below.s

    WebUrlSpecificDeviceTest - gradlew runWebUrlSpecificDeviceTest
    WebUrlDeviceTest - gradlew runWebUrlDeviceTest
    WebUrlResevedDeviceTest - gradlew runWebUrlResevedDeviceTest
    IOSApplicationTest - gradlew runIOSApplicationTest
    AppReservedDeviceTest - gradlew runAppReservedDeviceTest
    AndroidApplicationTest - gradlew runAndroidApplicationTest







