package io.seetest.examples;

import com.experitest.appium.SeeTestCapabilityType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import utils.ELEMENTS;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;


/**
 *  Example Test for Sample App EriBank on any Android Device seetest cloud.
 *
 *
 *
 */

public class IOSApplicationTest {
    public static final boolean FULL_RESET = true;
    public static final boolean INSTRUMENT_APP = true;
    public static final String SEETEST_IO_APPIUM_URL = "https://cloud.seetest.io:443/wd/hub";
    private static final String DEVICE_QUERY = "@os='ios'";
    private static final String ACCESS_KEY =
            "eyJ4cC51IjoyNTgyODI1LCJ4cC5wIjoyNTgyODIxLCJ4cC5tIjoiTVRVME1qQXdOelkwTnpnMU1BIiwiYWxnIjoiSFMyNTYifQ.eyJleHAiOjE4NTczNjc2NDgsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.ob4AJwWuSaPkDvjI8lUHIrV_wZg4PYFfLIrJ_SuhcF0";
    private static final String IOS_APP_NAME = "com.experitest.ExperiBank";

    Logger LOGGER = new Log4jLoggerFactory().getLogger(this.getClass().getName());

    private DesiredCapabilities dc = new DesiredCapabilities();
    private RemoteWebDriver driver = null;

    private static final String TEST_DESCRIPTION = "Example Test for Sample App EriBank on any IOS Device seetest cloud.";


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
        if (ACCESS_KEY == null || ACCESS_KEY.length() < 10) {
            LOGGER.error("Access key must be set. Please set the access key in the code.");
            LOGGER.info("To get access get to to https://cloud.seetest.io or learn at " +
                    "https://docs.seetest.io/display/SEET/Execute+Tests+on+SeeTest+-+Obtaining+Access+Key", ACCESS_KEY);
            throw new RuntimeException("Access key invalid : accessKey - " + ACCESS_KEY);
        }

        dc.setCapability(SeeTestCapabilityType.ACCESS_KEY, ACCESS_KEY);
        dc.setCapability(MobileCapabilityType.FULL_RESET, FULL_RESET);
        dc.setCapability(SeeTestCapabilityType.DEVICE_QUERY, DEVICE_QUERY);
        String appName = String.format("%s%s", "cloud:", IOS_APP_NAME);
        LOGGER.info("Setting up {} as app capability", IOS_APP_NAME);
        dc.setCapability(MobileCapabilityType.APP, appName);
        LOGGER.info("Device Query = {}", DEVICE_QUERY);
        LOGGER.info("Desired Capabilities setup complete");
        dc.setCapability("testName",
                testContext.getCurrentXmlTest().getName() + "." + this.getClass().getSimpleName());
        driver =  new IOSDriver(seetestURL, dc);

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

    @AfterClass
    protected void tearDown() {
        driver.quit();
    }

}
