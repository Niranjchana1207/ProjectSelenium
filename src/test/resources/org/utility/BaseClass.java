package org.utility;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
    public static WebDriver driver;
	public static WebDriver browserLanuch(String browsername) {
		if(browsername.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
	}
		else if (browsername.equalsIgnoreCase("firefox")) {
			 WebDriverManager.firefoxdriver().setup();
	         driver=new FirefoxDriver();
		}
		else if (browsername.equalsIgnoreCase("edge")) {
			 WebDriverManager.edgedriver().setup();
	         driver=new EdgeDriver();
		}
		return driver;
	}
	public static void urlLanuch(String url) {
		driver.get(url);
		driver.manage().window().maximize();
    }
	public static void implictWait(long sec) {
		driver.manage().timeouts().implicitlyWait(sec,TimeUnit.SECONDS);
    }
	public static void sendKeys(WebElement e,String value) {
		e.sendKeys(value);
    }
	public static void click(WebElement e) {
		e.click();
    }
	public static String getText(WebElement e) {
		String text = e.getText();
        return text;
	}
	public static String getTitle() {
		String title = driver.getTitle();
        return title;
	}
	public static String getCurrentUrl() {
		String currentUrl = driver.getCurrentUrl();
        return currentUrl;
	}
	public static void quit() {
		driver.quit();
	}
	public static void close() {
		driver.close();
	}
	public static String getAttribute(WebElement e) {
		String values = e.getAttribute("value");
        return values;
	}
	public static String getAttributeInner(WebElement e) {
		String innertext = e.getAttribute("innertext");
        return innertext;
	}
	public static void moveToElement(WebElement e) {
		Actions a=new Actions(driver);
		a.moveToElement(e).perform();
	}
	public static void dragAndDrop(WebElement src,WebElement des ) {
		Actions a=new Actions(driver);
		a.dragAndDrop(src,des).perform();
    }
	public static void contextClick(WebElement e) {
		Actions a=new Actions(driver);
        a.contextClick(e).perform();
	}
	public static void doubleClick(WebElement e) {
		Actions a=new Actions(driver);
        a.doubleClick(e).perform();
	}
	public static void clickAndHold(WebElement e) {
		Actions a=new Actions(driver);
		a.clickAndHold(e).perform();
    }
	public static void release(WebElement e) {
		Actions a=new Actions(driver);
		a.release(e).perform();
    }
	public static void enterKey() throws AWTException {
		Robot r=new Robot();
		r.keyPress(KeyEvent.VK_ENTER);
		r.keyRelease(KeyEvent.VK_ENTER);
	}
	public static void selectByIndex(WebElement e,int index) {
		Select s=new Select(e);
		s.selectByIndex(index);
    }
	public static void selectByValue(WebElement e,String value) {
		Select s=new Select(e);
		s.selectByValue(value);
	}
	public static void selectByVisibleText(WebElement e,String text) {
		Select s=new Select(e);
        s.selectByVisibleText(text);
	}
	public static List<String> getOptions(WebElement e) {
		Select s=new Select(e);
        List<WebElement> op = s.getOptions();
        List<String> optext=new LinkedList<>();
        for(int i=0;i<op.size();i++) {
        	optext.add(op.get(i).getText());
        }
		return optext;
	}
	public static boolean isMultiple(WebElement e) {
		Select s=new Select(e);
		boolean multiple = s.isMultiple();
		return multiple;
	}
	public static List<String> getAllSelectedOptions(WebElement e) {
		Select s=new Select(e);
		List<WebElement> all = s.getAllSelectedOptions();
		List<String> allselect=new LinkedList<>();
		for(int i=0;i<all.size();i++)  {
			WebElement w = all.get(i);
			String text = w.getText();
			allselect.add(text);
			//allselect.add(all.get(i).getText());
		}
		return allselect;
	}
	public static String getFirstSelectedOption(WebElement e) {
		Select s=new Select(e);
		WebElement first = s.getFirstSelectedOption();
		String text = first.getText();
		return text;
	}
	public static void deSelectByIndex(WebElement e,int index) {
		Select s=new Select(e);
		s.deselectByIndex(index);
	}
	public static void deSelectByValue(WebElement e,String value) {
		Select s=new Select(e);
		s.deselectByValue(value);
	}
	public static void deSelectByVisibleText(WebElement e,String text) {
		Select s=new Select(e);
        s.deselectByVisibleText(text);
	}
	public static void deSelectAll(WebElement e) {
		Select s=new Select(e);
        s.deselectAll();
	}
	public static void selectAll(WebElement e) {
		Select s=new Select(e);
		List<WebElement> op = s.getOptions();
		for(int i=0;i<op.size();i++)  {
			s.selectByIndex(i);
		}
	}
	public static void screenShot(String filename) throws IOException {
		TakesScreenshot ts=(TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File des=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshots"+filename+"_"+System.currentTimeMillis()+".png");
        FileUtils.copyFile(src, des);
	}
	public static void jsSendKeys(WebElement e,String value) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].setAttribute('value','"+value+"')",e);
	}
	public static String jsGettext(WebElement e) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
        String text = js.executeScript("return arguments[0].getAttribute('value')",e).toString();
        return text;
	}
	public static void jsClick(WebElement e) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
		js.executeScript("arguments[0].click()",e);
	}
	public static void scrollDown(WebElement e) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(true)",e);
	}
	public static void scrollUp(WebElement e) {
		JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView(false)",e);
	}
	public static void frameByIndex(int index) {
		driver.switchTo().frame(index);
	}
	public static void frameByIdorName(String value) {
		driver.switchTo().frame(value);
	}
	public static void frameByWebelement(WebElement e) {
		driver.switchTo().frame(e);
	}
	public static void defaultContent() {
		driver.switchTo().defaultContent();
	}
	public static void  parentFrame() {
		driver.switchTo().parentFrame();
	}
	public static void windowsHandling(int index) {
		
		Set<String> all = driver.getWindowHandles();
        List<String> li=new LinkedList<>();
        li.addAll(all);
        String st = li.get(index);
        driver.switchTo().window(st);
	}
	public static void navigateTo(String url) {
		driver.navigate().to(url);
	}
	public static void navigateBack() {
		driver.navigate().back();
	}
	private static void navigateRefresh() {
		driver.navigate().refresh();
	}
	public static boolean isDisplayed(WebElement e) {
		boolean displayed = e.isDisplayed();
        return displayed;
	}
	public static boolean isEnabled(WebElement e) {
		boolean enabled = e.isEnabled();
        return enabled;
	}
	public static boolean isSelected(WebElement e) {
		boolean selected = e.isSelected();
        return selected;
	}
	public static String excelRead(String filename,String sheetname,int row,int cell) throws IOException {
		File f=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Excel\\"+filename+".xlsx");
		FileInputStream fs=new FileInputStream(f);
		Workbook w=new XSSFWorkbook(fs);
		Sheet sheet = w.getSheet(sheetname);
		Row r = sheet.getRow(row);
		Cell c = r.getCell(cell);
		int type = c.getCellType();
		String value=null;
		if(type==1) {
		    value = c.getStringCellValue();
		} else if (DateUtil.isCellDateFormatted(c)) {
			Date date = c.getDateCellValue();
			SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yyyy");
		    value = sf.format(date);
		}else   {
			double db = c.getNumericCellValue();
			long num=(long)db;
			value = String.valueOf(num);	
		}
        return value;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
