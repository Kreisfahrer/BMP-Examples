package logs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.logging.Level;

public class RemoteLogTest {

    @Test
    public void logTest() throws MalformedURLException {
        //WebDriver driver = new RemoteWebDriver(new URL("http://172.16.173.34:4444/wd/hub"), DesiredCapabilities.internetExplorer());
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.OFF);
        logs.enable(LogType.CLIENT, Level.OFF);
        logs.enable(LogType.DRIVER, Level.ALL);
        logs.enable(LogType.PERFORMANCE, Level.ALL);
        logs.enable(LogType.SERVER, Level.ALL);

        DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);

        //WebDriver driver = new FirefoxDriver(desiredCapabilities);
        WebDriver driver = new RemoteWebDriver(new URL("http://172.16.173.34:4444/wd/hub"), desiredCapabilities);

        driver.get("http://the-internet.herokuapp.com");
        driver.findElement(By.linkText("File Download")).click();

        Set<String> logTypes = driver.manage().logs().getAvailableLogTypes();

        for (String type : logTypes) {
            System.out.println(type + ":");
            for (LogEntry entry : driver.manage().logs().get(type).getAll()) {
                System.out.println(entry.getMessage());
            }
            System.out.println("----------------------------------------------------");
        }

        System.out.println(logTypes.size());

        driver.quit();
    }
}
