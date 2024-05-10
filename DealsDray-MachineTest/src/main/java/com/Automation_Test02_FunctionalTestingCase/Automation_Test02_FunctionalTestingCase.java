package com.Automation_Test02_FunctionalTestingCase;

import static org.monte.media.FormatKeys.EncodingKey;
import static org.monte.media.FormatKeys.FrameRateKey;
import static org.monte.media.FormatKeys.KeyFrameIntervalKey;
import static org.monte.media.FormatKeys.MIME_AVI;
import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.VideoFormatKeys.CompressorNameKey;
import static org.monte.media.VideoFormatKeys.DepthKey;
import static org.monte.media.VideoFormatKeys.ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE;
import static org.monte.media.VideoFormatKeys.QualityKey;

import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.Duration;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Automation_Test02_FunctionalTestingCase {

	private static ScreenRecorder screenRecorder;	
	
	private static void startRecording() {
		File file = new File("./VideoRecord");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screenSize.width;
		int height = screenSize.height;

		Rectangle captureSize = new Rectangle(0, 0, width, height);

		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		try {
			screenRecorder = new ScreenRecorder(gc, captureSize,
					new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
							CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, DepthKey, 24, FrameRateKey,
							Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
					new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
					null, file);

			screenRecorder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void stopRecording() {
		try {
			screenRecorder.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {

		startRecording();
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://demo.dealsdray.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/form/div[1]/div/div/input")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/form/div[2]/div/div/input")).sendKeys("prexo.mis@dealsdray.com");
		driver.findElement(By.xpath("/html/body/div/div/div/div/div[2]/div/form/div[3]/div/button")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/div[1]/div[2]/button/div[1]/span[2]")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[1]/div/div[2]/div[1]/div[2]/div/div[1]/a/button/span[1]")).click();
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/div[2]/div[2]/button")).click();
		
	    File uploadFile = new File("C:\\Users\\BINOY\\Music\\selenium\\demo-data.xlsx");

		WebElement fileInput = driver.findElement(By.cssSelector("input[type=file]"));
		fileInput.sendKeys(uploadFile.getAbsolutePath());
		driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/div[2]/div[3]/button")).click();
		Thread.sleep(3000);
		
		try {
			Robot robot = new Robot(); 
			BufferedImage screenshot = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			File outputfile = new File("./images/FileUploadScreenshot/Fileupload.png");
			ImageIO.write(screenshot, "png", outputfile);
			System.out.println("screenshot saved successfully.");
			} catch (Exception e) {
				System.out.println("Failed to capture screenshot: " + e.getMessage());
				}

		 driver.close();
		
		 stopRecording();

	}

}
