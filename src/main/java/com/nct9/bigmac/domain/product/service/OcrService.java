package com.nct9.bigmac.domain.product.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import com.nct9.bigmac.domain.product.dto.ReceiptData;
import com.nct9.bigmac.domain.store.entity.Store;
import com.nct9.bigmac.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OcrService {

    private final StoreRepository storeRepository;

    public ReceiptData detectText(MultipartFile file) throws IOException {
        String credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");

        if (credentialsPath == null) {
            throw new IOException("Google Cloud credentials not found in environment variables.");
        }
        System.out.println("Google Cloud credentials found at: " + credentialsPath);

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
                    throw new IOException("Error: " + res.getError().getMessage());
                }

                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    detectedText.append(annotation.getDescription()).append("\n");
                }
            }

            // 상호명과 날짜를 추출합니다.
            String extractedText = detectedText.toString();
            String storeName = extractStoreName(extractedText);  // 상호명 추출
            String date = extractDate(extractedText);  // 날짜 추출

            System.out.println("이름뭐야: "+ storeName);
            System.out.println("날짜뭐야: " + date);
            return new ReceiptData(storeName, date);
        } catch (Exception e) {
            System.out.println("Google Cloud Vision API 클라이언트 생성 중 오류가 발생했습니다: " + e.getMessage());
            throw new IOException("Google Cloud Vision API 클라이언트 생성 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }

    private String extractStoreName(String text) {
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            if (text.contains(store.getName())) {
                return store.getName();
            }
        }
        return "상호명을 찾을 수 없습니다";
    }

    private String extractDate(String text) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "날짜를 찾을 수 없습니다";
    }
}
