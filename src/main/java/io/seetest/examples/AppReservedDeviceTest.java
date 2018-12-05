package io.seetest.examples;

import com.experitest.appium.SeeTestCapabilityType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.ELEMENTS;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Example Test for Sample App EriBank on a specific device for Android/IOS OS in seetest cloud.
 *
 Reserved here means you have reserved it using following navigation,
 *  Click device button on the device in seestest cloud
 *    -> Click Automation tab
 *    -> Click on "Lets Start Automating"  button.
 *
 * Set the access key for seetest cloud in variable ACCESS_KEY.
 * To change the OS of device, change @Optional in setUP function to "ios"
 * To change the device, change the DEVICE_ID to the if of the device in seetest cloud.
 *   Make sure value of OS variable is same as operating system of device.
 *
 */

public class AppReservedDeviceTest {

    public static final boolean FULL_RESET = true;
    public static final boolean INSTRUMENT_APP = true;
    public static final String SEETEST_IO_APPIUM_URL = "https://cloud.seetest.io:443/wd/hub";
    private static final String ACCESS_KEY =
            "";
    private static final String IOS_APP_NAME = "com.experitest.ExperiBank";
    private static final String ANDROID_APP_NAME = "com.experitest.ExperiBank/.LoginActivity";


    Logger LOGGER = new Log4jLoggerFactory().getLogger(this.getClass().getName());

    private DesiredCapabilities dc = new DesiredCapabilities();
    private RemoteWebDriver driver = null;

    private static final String TEST_DESCRIPTION = "Example Test for Sample App EriBank on IOS or Android for device which " +
            "is reserved in seetest cloud";

    //private static final String OS = "ios";
    //private static final String DEVICE_ID = "e5b1be805dfc50b8f25837da50591b701fd5534c";

    private static final String OS = "android";
    private static final String DEVICE_ID = "";

    /**
     * Core setup function, which sets up the selenium/appium drivers.
     * @param testContext Test Context for the Test.
     */

    @BeforeClass
    public void setUp(ITestContext testContext) {
        LOGGER.info("Enter  setUp");
        LOGGER.info(TEST_DESCRIPTION);
        URL seetestURL = null;
        try {
            seetestURL = new URL(SEETEST_IO_APPIUM_URL);
        } catch (MalformedURLException e) {
            LOGGER.error("Invalid URL -" + seetestURL);
        } ;
        dc.setCapability(SeeTestCapabilityType.ACCESS_KEY, ACCESS_KEY);
        dc.setCapability(MobileCapabilityType.FULL_RESET, FULL_RESET);
        dc.setCapability(MobileCapabilityType.UDID, DEVICE_ID);
        String appName = null;
        if ("android".equals(OS)) {
            appName = String.format("%s%s", "cloud:", ANDROID_APP_NAME);
        } else {
            appName = String.format("%s%s", "cloud:", IOS_APP_NAME);
        }
        dc.setCapability(MobileCapabilityType.APP, appName);

        LOGGER.info("Targetting device - " + DEVICE_ID);
        LOGGER.info("Desired Capabilities setup complete");
        dc.setCapability("testName",
                testContext.getCurrentXmlTest().getName() + "." + this.getClass().getSimpleName());

        checkMandatoryVars();
        if ("android".equals(OS)) {
            driver = new AndroidDriver(seetestURL, dc);
        } else {
            driver = new IOSDriver(seetestURL, dc);
        }
    }

    /**
     * Login Request before every test, because it is a step for any independent test.
     *
     *
     * @param userName userName
     * @param password password
     */
    @Parameters({"userName","password"})
    @Test
    public void eriBankLoginTest(@Optional("company") String userName, @Optional("company") String password) {
        LOGGER.info("Enter eriBankLogin - " + "userName = " + userName + " password = " + password);
        // Find Element commands for Find Login elements.
        driver.findElement(ELEMENTS.LOGIN_USER.getBy(ELEMENTS.TYPE.ANDROID)).sendKeys(userName);
        driver.findElement(ELEMENTS.LOGIN_PASS.getBy(ELEMENTS.TYPE.ANDROID)).sendKeys(password);
        driver.findElement(ELEMENTS.LOGIN_BUTTON.getBy(ELEMENTS.TYPE.ANDROID)).click();
        driver.findElement(ELEMENTS.LOGOUT_BUTTON.getBy(ELEMENTS.TYPE.ANDROID)).click();
        LOGGER.info("Exit eriBankLogin");
    }


    /**
     * Checks Mandatory variables.
     */
    private void checkMandatoryVars () {

        if (ACCESS_KEY == null || ACCESS_KEY.length() < 10) {
            LOGGER.error("Access key must be set. Please set the ACCESS_KEY in the code.");
            LOGGER.info("To get access get to to https://cloud.seetest.io or learn at " +
                    "https://docs.seetest.io/display/SEET/Execute+Tests+on+SeeTest+-+Obtaining+Access+Key", ACCESS_KEY);
            throw new RuntimeException("Invalid ACCESS_KEY or DEVICE_ID");
        }

        if (DEVICE_ID == null || DEVICE_ID.isEmpty()) {
            LOGGER.error("DEVICE_ID  variable must be set to id of a device in seetest clouds.");
            throw new RuntimeException("Invalid ACCESS_KEY or DEVICE_ID");
        }
    }

    @AfterClass
    protected void tearDown() {
        driver.quit();
    }
}
