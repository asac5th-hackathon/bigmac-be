package com.nct9.bigmac.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoreListResponseDto implements StoreListResponseInterface {
    private long id;
    private String name;
    private double latitude;
    private double longitude;
    private Long price;
}
