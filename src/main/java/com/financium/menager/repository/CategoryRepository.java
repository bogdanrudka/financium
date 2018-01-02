package com.financium.menager.repository;

import com.financium.menager.domain.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>{
}
