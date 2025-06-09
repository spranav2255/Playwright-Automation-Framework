package Configurations;

import com.microsoft.playwright.*;

import java.awt.*;
import java.util.Arrays;

public class DriverFactory {
	private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
	private static ThreadLocal<Browser> browser = new ThreadLocal<>();
	private static ThreadLocal<Page> page = new ThreadLocal<>();

	public static void initDriver() {
		playwright.set(Playwright.create());

		// Get screen dimensions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		// Launch browser with fullscreen arguments
		browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)
				.setArgs(Arrays.asList("--start-maximized", "--window-size=" + width + "," + height))));

		// Set the context with matching viewport size
		Browser.NewContextOptions contextOptions = new Browser.NewContextOptions().setViewportSize(width, height);
		BrowserContext context = browser.get().newContext(contextOptions);

		page.set(context.newPage());
	}

	public static Page getPage() {
		return page.get();
	}

	public static void closeDriver() {
		if (browser.get() != null) {
			browser.get().close();
			playwright.get().close();
		}
	}
}
