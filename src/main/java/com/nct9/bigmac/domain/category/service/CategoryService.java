package com.nct9.bigmac.domain.category.service;

import com.nct9.bigmac.domain.category.dto.CategoryResponseDto;
import com.nct9.bigmac.domain.category.entity.Category;
import com.nct9.bigmac.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryResponseDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponseDto::of)
                .collect(Collectors.toList());
    }

}
