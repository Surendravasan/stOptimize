package pageMethods;  

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import objRepository._dashboardPage;
import pageUtilities._base;
import pageUtilities._databaseUtils;
import pageUtilities._queries;
import pageUtilities._utils;
import tests._smokeTestsExcel;

public class _dashboard extends _dashboardPage {
	
	ExtentTest test = _base.test;
	ExtentTest node;
	public static String getUnitName = $unitName(_base.driver, 1).getText();;
	
	public _dashboard() {
		super();
		_utils.waitForElementVisibleByLocator($unitDetails);
//		getUnitName = $unitName(_base.driver, 1).getText();
	}
	
	public void getAllValues() {
		
		_utils.waitForElementInVisibleByLocator(loader);
		_utils.waitForElementClickable($preDefBox);

		for(int i=1; i<=$preDefBoxCount.size(); i++) {
			TreeSet<Integer> rawdbValues = new TreeSet<>();
			String unitName = $unitName(_base.driver, i).getText();
			System.out.println("Dashboard verification completed for: "+unitName);
			test.log(Status.INFO, MarkupHelper.createLabel(unitName, ExtentColor.BLUE));
			
			/* Stores Offered */
			
			if(!unitName.equalsIgnoreCase("10x25")) {
				String uiStOff = repSymbolText($unitStoresOffered(_base.driver, unitName).getText());
				if(!unitName.contains("RV"))
					rawdbValues = _databaseUtils.getValue(_queries.dashStoresOffered(unitName));
				else
					rawdbValues = _databaseUtils.getValue(_queries.dashStoresOfferedRV());
				String dbStOff = dbResult(rawdbValues);
				compareUiDbValues("Stores Offered: ", uiStOff, dbStOff);
				
				
				/* Average Price */
				
 				String uiAvgPrice = repSymbolText($unitAvgPrice(_base.driver, unitName).getText());
				if(!unitName.contains("RV"))
					rawdbValues = _databaseUtils.getValue(_queries.dashAvgPrice(unitName));
				else
					rawdbValues = _databaseUtils.getValue(_queries.dashAvgPriceRV());
				String dbAvgPrice = dbResult(rawdbValues);
				compareUiDbValues("Avg Price: ", uiAvgPrice, dbAvgPrice);
				
				
				/* Your Price */
				String uiYourPrice = repSymbolText($unitYourPrice(_base.driver, unitName).getText());
				if(!unitName.contains("RV"))
					rawdbValues = _databaseUtils.getValue(_queries.dashYourPrice(unitName));
				else
					rawdbValues = _databaseUtils.getValue(_queries.dashYourPriceRV());
				String dbYourPrice = dbResult(rawdbValues);
				compareUiDbValues("Your Price: ", uiYourPrice, dbYourPrice);
			}
		}
	}
	
	private String dbResult(TreeSet<Integer> dbValues) {
		
		TreeSet<Integer> set = new TreeSet<>(dbValues); 
		String value = null;
		int count = set.size();
		if(count==0) {
			value = "N/A";
		} else if(count==1) {
			value = String.valueOf(set.first());
		} else if(count>1){
			value = set.first()+" - "+set.last();
		} 
		
		if(count==1 && Integer.valueOf(value)==0) {
			value = "N/A";
		}
		
		return value;
	}
	
	
	private String repSymbolText(String uiText) {
		return uiText.replace("Offered by ", "").replace(" stores", "").replace(" store", "").replace("A$", "")
				.replace("NZ$", "").replace("$", "").replace("£", "").replace("€", "")
				.replace("*", "");
	}
	
	private void compareUiDbValues(String header, String uiText, String DbValue) {
		if(!uiText.equals(DbValue)) {
			test.log(Status.FAIL, header+"Site:"+uiText+"\t DB:"+DbValue+" VALUE MISMATCH");
		} else {
			test.log(Status.PASS, header+"Site:"+uiText+" DB:"+DbValue);
			}
		}
	
	public void unitViewDetails(String unitName) {
		_utils.waitForElementVisibleByLocator($unitDetails);
		_utils.submit($unitViewDetails(_base.driver, unitName));
	}
	
	public void addNewUnit() {
		_utils.clickAction($addUnit);
	}
	
	
	public List<String> getSpecificUnitValue(String unitName) {
		String uiStOff = repSymbolText($unitStoresOffered(_base.driver, unitName).getText());
		String uiAvgPrice = repSymbolText($unitAvgPrice(_base.driver, unitName).getText());
		String uiYourPrice = repSymbolText($unitYourPrice(_base.driver, unitName).getText());
		List<String> list = new LinkedList<>();
		list.add(uiStOff);
		list.add(uiAvgPrice);
		list.add(uiYourPrice);
		return list;
	}
	
	
	
}
