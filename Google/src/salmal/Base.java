package salmal;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import hybridgeneric.Auto;
import hybridgeneric.Futils;
import hybridgeneric.WebDriverutils;
import hybridpage.EnterTimeTrackPage;
import hybridpage.LoginPage;

abstract public class Base implements Auto {
static 
{
System.setProperty(key, value);	
}
public WebDriver driver;
public static int pass=0,fail=0;
@BeforeClass
public void openBrowser()
{
	driver=new ChromeDriver();
}
@BeforeMethod
public void openApp()
{
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().window().maximize();
	driver.get(URL);
	LoginPage lp=new LoginPage(driver);
	lp.login();
}
@AfterMethod
public void config(ITestResult ite)
{
	int status=ite.getStatus();
	String method=ite.getName();
	
	
	if(status==1)
	{
		pass++;
		Reporter.log("the method is pass"+pass);
	}
	else
	{
		fail++;
		String path=PHOTO_PATH+method+".png";
		WebDriverutils.takescreenshot(driver, path);
		Reporter.log("fail count is"+fail);
	}
	EnterTimeTrackPage etp=new EnterTimeTrackPage(driver);
	etp.clickOnLgoutBTN();
}
@AfterClass
public void close()
{
	driver.close();
}
@AfterSuite
public void report()
{
	Reporter.log("pass:"+pass,true);
    Reporter.log("fail"+fail,true);
    Futils.write_DataInto_Xl(REPORT_PATH, "Sheet1",4, 1, pass);
    Futils.write_DataInto_Xl(REPORT_PATH, "sheet1", 4, 1, fail);
}
}
