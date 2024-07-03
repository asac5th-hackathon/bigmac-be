package com.nct9.bigmac.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StoreListRequestDto {
    private double latitude;
    private double longitude;
    private Integer distance;
    private Long categoryId;
    // primitive vs reference
    // - primitive : 값 그 자체 = int, long, double -> NULL 이 불가능
    // - reference : 값이 담긴 객체 = Integer, Long, Double, String -> 객체니까 NULL 이 가능
}
