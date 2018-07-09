package com.kramer.ocrsandbox;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.DetectTextRequest;
import com.amazonaws.services.rekognition.model.DetectTextResult;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.TextDetection;
import com.amazonaws.util.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

@SpringBootApplication
public class OcrSandboxApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OcrSandboxApplication.class, args);

		String photo = "/Users/joekramer/Desktop/nba-promo.png";

		ByteBuffer imageBytes;
		try (InputStream inputStream = new FileInputStream(new File(photo))) {
			imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
		}

		AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder.defaultClient();

		DetectTextRequest detectTextRequest = new DetectTextRequest()
				.withImage(new Image()
						.withBytes(imageBytes));

		DetectTextResult result = rekognitionClient.detectText(detectTextRequest);
		List<TextDetection> textDetections = result.getTextDetections();

		System.out.println("Detected text:");
		for (TextDetection textDetection : textDetections) {
			System.out.println(textDetection.getDetectedText());
		}

		System.exit(0);
	}
}
