package com.ecomerse.usermode.repository;

import com.ecomerse.usermode.entity.Catalog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalCatalogRepository extends JpaRepository<Catalog, Integer> {
}
