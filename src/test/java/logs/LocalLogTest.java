package logs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.testng.annotations.Test;

import java.util.Set;

/**
 * Created by andreystakhievich on 2/13/2015.
 */
public class LocalLogTest {
    @Test
    public void logTest() {
        WebDriver driver = new FirefoxDriver();

        driver.get("http://the-internet.herokuapp.com");
        driver.findElement(By.linkText("File Download")).click();

        Set<String> a = driver.manage().logs().getAvailableLogTypes();

        for (String type : a) {
            System.out.println(type + ":");
            for (LogEntry entry : driver.manage().logs().get(type).getAll()) {
                System.out.println(entry);
            }
            System.out.println("----------------------------------------------------");
        }

        System.out.println(a.size());

        driver.quit();
    }
}
