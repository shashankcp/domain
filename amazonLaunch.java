package main.java;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

class amazonLaunch {
	public static void main(String[] args) {
		final  String driverPath = "C:\\chromedriver\\chromedriver.exe";
		final String launchUrl = "https://www.amazon.com";
		System.setProperty("webdriver.chrome.driver", driverPath);  
		// Instantiate a ChromeDriver class.     
		WebDriver driver=new ChromeDriver();  
		driver.get(launchUrl);
		WebElement searchBox=driver.findElement(By.cssSelector(("#twotabsearchtextbox")));
		searchBox.sendKeys("Headphones");
		WebElement searchButton=driver.findElement(By.cssSelector((".nav-search-submit")));
		searchButton.click();	

		//to search bestseller items from searchresults
		List<WebElement> bestSellers = driver.findElements(
				By.xpath("//span[text()='Best Seller']" +
						"/ancestor::div[@data-asin and not(.//span[.='Sponsored'])][1]" +
						"//span[@data-component-type='s-product-image']//a"));

		//adds all best-seller items to the shopping cart					
		List<String> bestSellersHrefs = bestSellers.stream().map(element -> element.getAttribute("href")).collect(Collectors.toList());
		bestSellersHrefs.forEach(href -> {
		driver.get(href);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button"))).click();

			/* boolean success = Wait.until(
    		ExpectedConditions.elementToBeClickable(By.className("success-message")),
    		//ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='attachDisplayAddBaseAlert']//h4[normalize-space(.)='Added to Cart']")),
    		//ExpectedConditions.elementToBeClickable(By.xpath("//h1[normalize-space(.)='Added to Cart']"))
    		);
			});*/

		});
		
		endSession(driver);
	}
	public static void endSession(WebDriver driver) {
		driver.close();
	}
}