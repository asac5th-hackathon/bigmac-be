package com.nct9.bigmac.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private float price;
    private Long storeId;
    private Long categoryId;

}
