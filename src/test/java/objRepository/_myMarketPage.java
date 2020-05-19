package objRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pageUtilities._browser;

public class _myMarketPage {
	
	public _myMarketPage() {
		PageFactory.initElements(_browser.driver, this);
	}
	
	protected String loading = "div.loading";
	protected String dashboard = "td[class*='market-name'] a[href*='";
	
	@FindBy(css="button[class*='add']")
	public WebElement $addMarket;
	
	@FindBy(css="div.drag-view h5 span")
	public WebElement $unitName;
	
	public WebElement $dashboardLink(WebDriver driver, int userStoreId) {
		return driver.findElement(By.cssSelector("td[class*='market-name'] a[href*='"+userStoreId+"'"));
	}
}