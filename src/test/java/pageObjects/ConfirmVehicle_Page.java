package pageObjects;

import java.util.List;
import org.apache.log4j.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ConfirmVehicle_Page {

	private WebElement element = null;

	public WebElement ele_CarNo(WebDriver driver) {

		element = driver.findElement(By.cssSelector(".reg-mark"));

		return element;

	}

	public WebElement ele_Make(WebDriver driver) {

		List<WebElement> elementC = null;
		elementC = driver.findElements(By.cssSelector(".list-summary-item span strong")); // .list-summary-item span
																							// strong

		return elementC.get(0);

	}

	public WebElement ele_Colour(WebDriver driver) {

		List<WebElement> elementC = null;
		elementC = driver.findElements(By.cssSelector(".list-summary-item span strong")); // .list-summary-item span
																							// strong
		// System.out.println("coloMyELE"+elementC.get(1));

		return elementC.get(1);

	}

	public WebElement ele_SelectOption(WebDriver driver) {

		element = driver.findElement(By.id("Correct_False"));

		return element;

	}

	public WebElement btn_Continue(WebDriver driver) {

		element = driver.findElement(By.name("Continue"));

		return element;

	}
}
