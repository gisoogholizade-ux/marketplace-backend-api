package com.gisoo.marketplace.category;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByActiveTrueOrderByNameAsc();
    boolean existsBySlugIgnoreCase(String slug);
}
