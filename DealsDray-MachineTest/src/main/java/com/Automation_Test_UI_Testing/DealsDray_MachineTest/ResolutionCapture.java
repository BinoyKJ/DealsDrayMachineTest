package com.Automation_Test_UI_Testing.DealsDray_MachineTest;

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
import java.util.List;

import javax.imageio.ImageIO;

import org.monte.media.Format;
import org.monte.media.FormatKeys.MediaType;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ResolutionCapture {
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

	static void Chrome() throws InterruptedException {

		int[] width = { 1920, 1366, 1536 };
		int[] height = { 1080, 768, 864 };

		for (int i = 3; i <= 7; i++) {
			WebDriver driver = new ChromeDriver();
			driver.get("https://www.getcalley.com/page-sitemap.xml");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			List<WebElement> links = driver.findElements(By.tagName("a"));
			WebElement element = links.get(i);
			element.click();
			Thread.sleep(5000);
            String title = driver.getTitle();
            title = title.replace('?', 's');
            
			for (int j = 0; j < 3; j++) {
				int WidthNumber = width[j];
				int HeightNumber = height[j];
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(WidthNumber, HeightNumber));

				try {
					Robot robot = new Robot();
					BufferedImage screenshot = robot
							.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					File outputfile = new File(
							"./Images/Chrome/" + title + "-" + WidthNumber + "x" + HeightNumber + ".png");

					ImageIO.write(screenshot, "png", outputfile);
					System.out.println("Desktop screenshot saved successfully.");
				} catch (Exception e) {
					System.out.println("Failed to capture desktop screenshot: " + e.getMessage());
				}
			}
			
			driver.close();
		}

	}

	static void Firefox() throws InterruptedException {
		int[] width = { 1920, 1366, 1536 };
		int[] height = { 1080, 768, 864 };

		for (int i = 3; i <= 7; i++) {
			WebDriver driver = new FirefoxDriver();
			driver.get("https://www.getcalley.com/page-sitemap.xml");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			List<WebElement> links = driver.findElements(By.tagName("a"));
			WebElement element = links.get(i);
			element.click();
			Thread.sleep(5000);
			String title = driver.getTitle();
            title = title.replace('?', 's');
            
			for (int j = 0; j < 3; j++) {
				int WidthNumber = width[j];
				int HeightNumber = height[j];
				driver.manage().window().setSize(new org.openqa.selenium.Dimension(WidthNumber, HeightNumber));

				try {
					Robot robot = new Robot();
					BufferedImage screenshot = robot
							.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					File outputfile = new File(
							"./Images/Firefox/" + title + "-" + WidthNumber + "x" + HeightNumber + ".png");

					ImageIO.write(screenshot, "png", outputfile);
					System.out.println("Desktop screenshot saved successfully.");
				} catch (Exception e) {
					System.out.println("Failed to capture desktop screenshot: " + e.getMessage());
				}
			}
			driver.close();
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
		Chrome();
		Firefox();
		stopRecording();
	}

}
