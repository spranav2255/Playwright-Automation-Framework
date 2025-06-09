package Configurations;

import com.microsoft.playwright.*;

public class DriverFactory {
	private static ThreadLocal<Playwright> playwright = new ThreadLocal<>();
	private static ThreadLocal<Browser> browser = new ThreadLocal<>();
	private static ThreadLocal<Page> page = new ThreadLocal<>();

	public static void initDriver() {
		playwright.set(Playwright.create());
		browser.set(playwright.get().chromium().launch(new BrowserType.LaunchOptions().setHeadless(false)));
		page.set(browser.get().newPage());
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
