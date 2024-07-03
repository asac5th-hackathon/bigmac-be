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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    public Boolean equalsProductName(String productName) {
       return productRepository.findByName(productName).isPresent();

    }

    public void updatePrice(Long id, Float newPrice) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setPrice(newPrice);
        productRepository.save(product);
    }


    public boolean compareStoreNameAndDate(String ocrStoreName, String ocrDate) {

        String currentDate = String.valueOf(LocalDate.now());
        boolean storeExists = storeRepository.findByName(ocrStoreName).isPresent();

        return storeExists && currentDate.equals(ocrDate);
    }
}
