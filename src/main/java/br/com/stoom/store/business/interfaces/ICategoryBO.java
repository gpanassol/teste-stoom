package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.CategoryDTO;
import java.util.List;

public interface ICategoryBO {

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
