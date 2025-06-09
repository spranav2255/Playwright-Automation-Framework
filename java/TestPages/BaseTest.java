package TestPages;

import java.lang.reflect.Method;

import org.testng.annotations.*;
import MainUtilities.TestListener;

public class BaseTest {

	@BeforeSuite
	public void beforeSuite() {
		TestListener.onSuiteStart();
	}

	@AfterSuite
	public void afterSuite() {
		TestListener.onSuiteFinish();
	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		TestListener.onTestStart(method.getName());
	}

	@AfterMethod
	public void afterMethod(org.testng.ITestResult result) {
		if (result.isSuccess()) {
			TestListener.onTestSuccess(result.getMethod().getMethodName());
		} else {
			TestListener.onTestFailure(result.getMethod().getMethodName(), result.getThrowable());
		}
	}
}
