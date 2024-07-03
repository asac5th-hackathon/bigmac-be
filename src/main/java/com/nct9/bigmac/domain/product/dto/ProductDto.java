package com.nct9.bigmac.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
    private long productId;
    private String name;
    private float price;
    private String category;
}
