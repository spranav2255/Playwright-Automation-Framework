package TestPages;

import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.Test;
import Configurations.DriverFactory;

public class SampleTest {

	@Test
	public void testAmazonTitle() throws InterruptedException {
		Page page = DriverFactory.getPage();
		page.navigate("https://www.amazon.com");
		Thread.sleep(5000);
		String title = page.title();
		System.out.println("Page title: " + title);
		Assert.assertTrue(title.contains("Amazon"), "Title does not contain 'Amazon'");
	}

	@Test
	public void testGoogleTitle() throws InterruptedException {
		Page page = DriverFactory.getPage();
		page.navigate("https://www.google.com");
		Thread.sleep(5000);
		String title = page.title();
		System.out.println("Page title: " + title);
		Assert.assertTrue(title.contains("Google"), "Title does not contain 'Google'");
	}
}
