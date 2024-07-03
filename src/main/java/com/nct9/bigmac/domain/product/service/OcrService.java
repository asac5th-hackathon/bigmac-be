package com.nct9.bigmac.domain.product.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcrService {

    public String detectText(MultipartFile file) throws IOException {
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (credentialsPath == null) {
            throw new IOException("Google Cloud credentials not found in environment variables.");
        }

        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(file.getInputStream());

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        // 클라이언트 생성 확인
        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            StringBuilder detectedText = new StringBuilder();
            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    System.out.format("Error: %s%n", res.getError().getMessage());
                    return "Error: " + res.getError().getMessage();
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    detectedText.append(annotation.getDescription()).append("\n");
                    System.out.format("Text: %s%n", annotation.getDescription());
                    System.out.format("Position : %s%n", annotation.getBoundingPoly());
                }
            }

            return detectedText.toString();
        } catch (Exception e) {
            System.out.println("Google Cloud Vision API 클라이언트 생성 중 오류가 발생했습니다: " + e.getMessage());
            return "Google Cloud Vision API 클라이언트 생성 중 오류가 발생했습니다: " + e.getMessage();
        }
    }
}
