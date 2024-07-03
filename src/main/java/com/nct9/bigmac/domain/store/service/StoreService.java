package com.nct9.bigmac.domain.store.service;

import com.nct9.bigmac.domain.product.dto.ProductDto;
import com.nct9.bigmac.domain.product.entity.Product;
import com.nct9.bigmac.domain.product.repository.ProductRepository;
import com.nct9.bigmac.domain.store.dto.*;
import com.nct9.bigmac.domain.store.entity.Store;
import com.nct9.bigmac.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final ProductRepository productRepository;

    public StoreInfoDto getStoreInfo(Long storeId, Long categoryId) {
        Store store = storeRepository.findById(storeId).orElse(null);

        ProductDto productDto = null;
        if (categoryId != null) {
            Product product = productRepository.findByStoreIdAndCategoryId(storeId, categoryId);
            if (product != null) {
                productDto = new ProductDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getCategory().getName()
                );
            }
        }

        return new StoreInfoDto(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getPhone(),
                productDto
        );
    }

    public StoreProductsDto getStoreProducts(Long storeId) {
        Store store = storeRepository.findById(storeId).orElse(null);

        List<Product> products = productRepository.findByStoreId(storeId);
        List<ProductDto> productDto = new ArrayList<>();

        for (Product product : products) {
            ProductDto dto = new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategory().getName()
            );
            productDto.add(dto);
        }

        return new StoreProductsDto(
                store.getId(),
                store.getName(),
                productDto
        );
    }

    public List<StoreListResponseDto> getStoreListByFilter(StoreListRequestDto request) {
        List<StoreListResponseInterface> storeResponses = storeRepository.getStoreListByDistance(request.getLatitude(),
                request.getLongitude(),
                request.getDistance(),
                request.getCategoryId());

        List<StoreListResponseDto> dtos = storeResponses.stream()
                .map(response -> new StoreListResponseDto(
                        response.getId(),
                        response.getName(),
                        response.getLatitude(),
                        response.getLongitude(),
                        response.getPrice()
                ))
                .collect(Collectors.toList());

        return dtos;
    }
}
