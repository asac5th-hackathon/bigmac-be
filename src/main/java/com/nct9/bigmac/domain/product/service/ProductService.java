package com.nct9.bigmac.domain.product.service;

import com.nct9.bigmac.domain.category.entity.Category;
import com.nct9.bigmac.domain.category.repository.CategoryRepository;
import com.nct9.bigmac.domain.product.dto.ProductRequestDto;
import com.nct9.bigmac.domain.product.entity.Product;
import com.nct9.bigmac.domain.product.repository.ProductRepository;
import com.nct9.bigmac.domain.store.entity.Store;
import com.nct9.bigmac.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;


    public void register(ProductRequestDto productRequestDto) {
        Store store = storeRepository.findById(productRequestDto.getStoreId()).orElseThrow();
        Category category = categoryRepository.findById(productRequestDto.getCategoryId()).orElseThrow();
        Product product = Product.builder()
                .category(category)
                .store(store)
                .name(productRequestDto.getName())
                .price(productRequestDto.getPrice())
                .build();
        productRepository.save(product);
    }
}
