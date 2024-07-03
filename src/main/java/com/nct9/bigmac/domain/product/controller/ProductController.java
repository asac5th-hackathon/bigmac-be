package com.nct9.bigmac.domain.product.controller;

import com.nct9.bigmac.domain.product.dto.ProductRequestDto;
import com.nct9.bigmac.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("create")
    public ResponseEntity<Void> registerPrice(@RequestBody ProductRequestDto productRequestDto) {

        productService.register(productRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED).build();

    }
}
