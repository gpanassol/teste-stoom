package br.com.stoom.store.repository;

import br.com.stoom.store.model.Product;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoriesId(Long categoryId);

    List<Product> findAllByBrandsId(Long brandId);
}