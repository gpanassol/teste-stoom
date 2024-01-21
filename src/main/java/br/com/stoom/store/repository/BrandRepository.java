package br.com.stoom.store.repository;

import br.com.stoom.store.model.Brand;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByIdAndDeletionDateIsNull(Long id);

    List<Brand> findByDeletionDateIsNull();
}
