package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StartNow_Page {

	private static WebElement element = null;

	public WebElement btn_StartNow(WebDriver driver) {

		element = driver.findElement(By.linkText("Start now"));

		return element;

	}

}
