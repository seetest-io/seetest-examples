package io.seetest.examples;

import com.experitest.appium.SeeTestCapabilityType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileBrowserType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Seetest example to load a URL  in a browser any a specific unreserved device in seetest cloud.
 *
 * Unreserved here means if the device is not used then the test will be executed. Otherwise there will
 * be a timeout failure.
 *
 *
 * Set the access key for seetest cloud in variable ACCESS_KEY.
 * To change the OS of device, change @Optional in setUP function to "ios"
 * To change the device, change the value of SERIAL_NUMBER  variable to id of the device.
 *    Make sure value of OS variable is same as operating system of device.
 *
 *
 *
 */
public class WebUrlSpecificDeviceTest {

    private static final String TEST_DESCRIPTION = "Seetest example to load a URL  in a browser in a" +
            " specific unreserved device in seetest cloud.";

    private DesiredCapabilities dc = new DesiredCapabilities();
    private RemoteWebDriver driver = null;
    private String os;

    public Logger LOGGER = new Log4jLoggerFactory().getLogger(this.getClass().getName());
    public static final String SEETEST_IO_APPIUM_URL = "https://cloud.seetest.io:443/wd/hub";
    public static final String URL = "https://edition.cnn.com/";
    public static final String XPATH_QUERY = "//*[contains(text(),'In focus')]";
    public static final String EXPECTED_RESPONSE = "In focus";
    private static final String ACCESS_KEY =
            "";

    private static final String SERIAL_NUMBER = "";
    //private static final String SERIAL_NUMBER = "";
    private static final String OS = "android";


    /**
     * Core setup function, which sets up the selenium/appium drivers.
     *
     * @param testContext Test Context for the Test.
     */

    @BeforeClass
    public void setUp(ITestContext testContext) {
        LOGGER.info("Enter TestBase setUp");
        LOGGER.info(TEST_DESCRIPTION);
        URL seetestURL = null;
        this.os = os;
        if (ACCESS_KEY == null || ACCESS_KEY.length() < 10) {
            LOGGER.error("Access key must be set. Please set the access key in the code.");
            LOGGER.info("To get access get to to https://cloud.seetest.io or learn at " +
                    "https://docs.seetest.io/display/SEET/Execute+Tests+on+SeeTest+-+Obtaining+Access+Key", ACCESS_KEY);
            throw new RuntimeException("Access key invalid : accessKey - " + ACCESS_KEY);
        }

        dc.setCapability(SeeTestCapabilityType.ACCESS_KEY, ACCESS_KEY);
        dc.setCapability(CapabilityType.VERSION, "Any");
        dc.setCapability(CapabilityType.PLATFORM, Platform.ANY);
        String deviceQuery = String.format("@os='%s' and @serialnumber='%s'", OS , SERIAL_NUMBER);
        if (OS.contains("android")) {
            dc.setBrowserName(MobileBrowserType.CHROME);
        } else {
            dc.setBrowserName(MobileBrowserType.SAFARI);
        }

        dc.setCapability(SeeTestCapabilityType.DEVICE_QUERY, deviceQuery);
        LOGGER.info("Device Query = {}", deviceQuery);
        LOGGER.info("Desired Capabilities setup complete");
        dc.setCapability("testName",
                testContext.getCurrentXmlTest().getName() + "." + this.getClass().getSimpleName());

        try {
            seetestURL = new URL(SEETEST_IO_APPIUM_URL);
        } catch (MalformedURLException e) {
            LOGGER.error("Invalid URL -" + seetestURL);
        } ;
        checkMandatoryVars();
        driver = OS.equals("android") ?
                new AndroidDriver(seetestURL, dc) :
                new IOSDriver(seetestURL, dc);
    }

    @Test
    public void urlWebTest() {
        LOGGER.info("Enter urlWebTest");
        LOGGER.info("url = " + URL);
        driver.get(URL);

        String actualTitle = driver.getTitle();
        LOGGER.info("Title = " + actualTitle);

        LOGGER.info("XPATH Query = " + XPATH_QUERY);

        // Search the element.
        By searchElementBy = By.xpath(XPATH_QUERY);
        String actualVal = driver.findElement(searchElementBy).getText();

        LOGGER.info("Expected = " + EXPECTED_RESPONSE + "; Actual = " + actualVal);
        Assert.assertEquals(EXPECTED_RESPONSE, actualVal);
        LOGGER.info("Exit urlWebTest");
    }

    /**
     * Checks Mandatory variables.
     */
    private void checkMandatoryVars () {

        if (ACCESS_KEY == null || ACCESS_KEY.length() < 10) {
            LOGGER.error("Access key must be set. Please set the ACCESS_KEY in the code.");
            LOGGER.info("To get access get to to https://cloud.seetest.io or learn at " +
                    "https://docs.seetest.io/display/SEET/Execute+Tests+on+SeeTest+-+Obtaining+Access+Key", ACCESS_KEY);
            throw new RuntimeException("Invalid ACCESS_KEY  or SERIAL_NUMBER");
        }

        if (SERIAL_NUMBER == null || SERIAL_NUMBER.isEmpty()) {
            LOGGER.error("SERIAL_NUMBER  variable must be set to id of a device in seetest cloud.");
            throw new RuntimeException("Invalid ACCESS_KEY  or SERIAL_NUMBER");
        }


    }

    @AfterClass
    protected void tearDown() {
        driver.quit();
    }

}