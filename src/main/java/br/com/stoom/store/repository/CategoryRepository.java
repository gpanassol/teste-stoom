package br.com.stoom.store.repository;

import br.com.stoom.store.model.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByIdAndDeletionDateIsNull(Long id);

    List<Category> findByDeletionDateIsNull();
}
