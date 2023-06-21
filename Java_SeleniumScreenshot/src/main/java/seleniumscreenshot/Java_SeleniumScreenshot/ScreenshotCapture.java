package seleniumscreenshot.Java_SeleniumScreenshot;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import io.github.bonigarcia.wdm.WebDriverManager;


public  class ScreenshotCapture 
{
	private static String currentDirectory,ssFileSavePath;
    private static  WebDriver driver;
    private static TakesScreenshot scrShot;
    private static File SrcFile,DestFile;
    private static LocalDateTime currentDateTime = LocalDateTime.now();
    private static String TDformat = currentDateTime.format(DateTimeFormatter.ofPattern("d-M-yyyy hh:mm:ss a"));
    
    //Screenshot File storing Path
    public static String ScreenshotFileSavingPath()
    {
    	 currentDirectory = System.getProperty("user.dir");
    	 ssFileSavePath = currentDirectory + "\\Screenshots\\";
         return ssFileSavePath;
    }
    
   
  //Take Screenshot
    public static void TakeScreenshot(WebDriver driver, String ssName)
    {
        try
        {
        	ScreenshotFileSavingPath();
        	//Convert web driver object to TakeScreenshot
            scrShot =((TakesScreenshot)driver);
        	//Call getScreenshotAs method to create image file
        	 SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        	 Thread.sleep(3000);
        	//Move image file to new destination
             DestFile=new File(ssFileSavePath);
          
            if (!DestFile.exists())
            {
            	//Creating a File object
            	File file = new File(ssFileSavePath);
            	
            	//Creating the directory
                boolean bool = file.mkdir();
                
                if(bool)
                {
                    System.out.println("Screenshot Directory created successfully");
                 }else
                 {
                    System.out.println("Creating Screenshot Directory is failed");
                 }
            	 //Copy file at destination
                FileHandler.copy(SrcFile, new File(DestFile+ ssName + "_" +TDformat+ ".png"));
                System.out.println(ssName +" Sceenshot saved successfully");
            }
            else
            {
            	//Screenshot folder exists already
            	FileHandler.copy(SrcFile, new File(DestFile+ ssName + "_" +TDformat+ ".png"));
            }
           
        }
        catch (Exception ex)
        {
        	System.out.println("Error in Capturing Screenshot " + ex.getMessage());
        }
    }
    public static void main( String[] args )
    {
    	
		WebDriverManager.chromedriver().setup();
    	//System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
				
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
		driver.get("https://www.google.com/");
		
		TakeScreenshot(driver,"googlepage");
    	driver.quit();
    }
}
