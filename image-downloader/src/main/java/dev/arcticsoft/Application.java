package dev.arcticsoft;

public class Application {

	public static void main(String[] args) throws Exception {
		System.setProperty("java.util.logging.config.file", "src/main/resources/logging.properties");
		
		ImageService.downloadPages(1, 5, true);
	}
}