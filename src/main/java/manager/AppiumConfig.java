package manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.MalformedURLException;
import java.net.URL;

import static utils.PropertiesReader.getProperty;

public class AppiumConfig {

//    appiumUrl=http://127.0.0.1:4723
//    deviceName=Pixel2
//    app=apps/demo.apk
//    appPackage=com.sheygam.contactapp
//    appActivity=SplashActivity
//    os = android

    public static AppiumDriver createAppiumDriver(String fileName) {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(getProperty(fileName, "os"))
                .setAutomationName(getProperty(fileName, "automationName"))
                .setDeviceName(getProperty(fileName, "deviceName"))
                .setAppPackage(getProperty(fileName, "appPackage"))
                .setAppActivity(getProperty(fileName, "appActivity"));
        try {
            AppiumDriver appiumDriver = new AppiumDriver(new URL(getProperty(fileName, "appiumUrl")), options);
            return appiumDriver;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Bad Appium URL: " + getProperty(fileName, "appiumUrl"), e);
        }
    }
}
