package com.nct9.bigmac.domain.product.controller;

import com.nct9.bigmac.domain.product.dto.ProductRequestDto;
import com.nct9.bigmac.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseEntity<Void> registerPrice(@RequestBody ProductRequestDto productRequestDto) {

        productService.register(productRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED).build();

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
