package com.nct9.bigmac.domain.store.dto;

import com.nct9.bigmac.domain.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StoreProductsDto {
    private long storeId;
    private String name;
    private List<ProductDto> products;
}
