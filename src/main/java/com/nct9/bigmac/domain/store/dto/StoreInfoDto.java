package com.nct9.bigmac.domain.store.dto;

import com.nct9.bigmac.domain.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreInfoDto {
    private long storeId;
    private String name;
    private String address;
    private String phone;
    private ProductDto product;
}
