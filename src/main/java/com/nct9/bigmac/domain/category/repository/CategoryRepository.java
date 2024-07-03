package com.nct9.bigmac.domain.category.repository;

import com.nct9.bigmac.domain.category.entity.Category;
import com.nct9.bigmac.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
