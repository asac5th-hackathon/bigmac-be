package com.nct9.bigmac.domain.product.controller;

import com.nct9.bigmac.domain.product.dto.ProductRequestDto;
import com.nct9.bigmac.domain.product.dto.ReceiptData;
import com.nct9.bigmac.domain.product.service.OcrService;
import com.nct9.bigmac.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final OcrService ocrService;

//    @PostMapping("/create")
//    public ResponseEntity<Void> registerPrice(@RequestBody ProductRequestDto productRequestDto) {
//
//        productService.register(productRequestDto);
//        return ResponseEntity
//                .status(HttpStatus.CREATED).build();
//
//    }

    @PostMapping("/create")
    public ResponseEntity<ReceiptData> registerPrice(
            @RequestPart("file") MultipartFile file,
            @RequestPart("productRequestDto") ProductRequestDto productRequestDto) {

        try {
            // OCR 처리
            ReceiptData receiptData = ocrService.detectText(file);

            // 영수증 검증 로직 추가
            boolean isValid = productService.compareStoreNameAndDate(receiptData.getStoreName(), receiptData.getDate());
            if (!isValid) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            productService.register(productRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateName(@RequestParam("productName") String productName) {
        Boolean validateResult = productService.equalsProductName(productName);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(validateResult);
    }

    @PatchMapping("/update/{id}")
        public ResponseEntity<Void> updatePrice(@PathVariable("id") Long id, @RequestParam("newPrice") Float newPrice) {
          productService.updatePrice(id, newPrice);
          return ResponseEntity
                  .status(HttpStatus.ACCEPTED).build();
        }
}
