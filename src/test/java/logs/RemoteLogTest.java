package logs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

public class RemoteLogTest {

    @Test
    public void logTest() throws MalformedURLException {
        //WebDriver driver = new RemoteWebDriver(new URL("http://172.16.173.34:4444/wd/hub"), DesiredCapabilities.internetExplorer());
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level);
        logs.enable(LogType.CLIENT, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        logs.enable(LogType.PERFORMANCE, Level.ALL);
        logs.enable(LogType.SERVER, Level.ALL);

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);

        //WebDriver driver = new FirefoxDriver(desiredCapabilities);
        WebDriver driver = new RemoteWebDriver(new URL("http://172.16.173.34:4444/wd/hub"), desiredCapabilities);

        driver.get("http://the-internet.herokuapp.com");
        driver.findElement(By.linkText("File Download")).click();

        //Set<String> a = driver.manage().logs().getAvailableLogTypes();

        LogEntries b = driver.manage().logs().get("Server");

//        for (String type : a) {
//            System.out.println(type + ":");
//            for (LogEntry entry : driver.manage().logs().get(type).getAll()) {
//                System.out.println(entry.getMessage());
//            }
//            System.out.println("----------------------------------------------------");
//        }
//
//        System.out.println(a.size());

        driver.quit();
    }
}
