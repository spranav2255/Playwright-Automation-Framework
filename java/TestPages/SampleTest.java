package TestPages;

import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import Configurations.DriverFactory;

public class SampleTest extends BaseTest {

    @Test
    public void testAmazonTitle() {
        Page page = DriverFactory.getPage();
        page.navigate("https://www.amazon.com");
        String title = page.title();
        System.out.println("Page title: " + title);
        Assert.assertTrue(title.contains("Amazon"), "Title does not contain 'Amazon'");
    }

    @Test
    public void testGoogleTitle() {
        Page page = DriverFactory.getPage();
        page.navigate("https://www.google.com");
        String title = page.title();
        System.out.println("Page title: " + title);
        Assert.assertTrue(title.contains("Google"), "Title does not contain 'Google'");
    }
}
