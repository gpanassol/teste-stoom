package br.com.stoom.store.repository;

import br.com.stoom.store.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategoriesIdAndDeletionDateIsNull(Long categoryId);

    List<Product> findAllByBrandsIdAndDeletionDateIsNull(Long brandId);

    List<Product> findByDeletionDateIsNull();

    Optional<Product> findByIdAndDeletionDateIsNull(Long productId);
}