package com.nct9.bigmac.domain.category.dto;

import com.nct9.bigmac.domain.category.entity.Category;
import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryResponseDto {
    private final Long id;

    private final String name;

    private final String svgData;

    public static CategoryResponseDto of(Category entity) {
        return new CategoryResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getSvgData()
        );
    }
}
