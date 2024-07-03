package com.nct9.bigmac.domain.product.controller;

import com.nct9.bigmac.domain.product.service.OcrService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class OcrController {
    private final OcrService ocrService;

    @PostMapping("/receipt")
    public ResponseEntity<String> verifyReceipt(@RequestParam("file") MultipartFile file) {
        try {
            String result = ocrService.detectText(file);
            return ResponseEntity.ok(result);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("파일 처리 실패: " + e.getMessage());
        }
    }
}
