package com.nct9.bigmac.domain.category.controller;

import com.nct9.bigmac.domain.category.dto.CategoryResponseDto;
import com.nct9.bigmac.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/controller")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> getAllCategories() {
        return categoryService.findAll();
    }
}
