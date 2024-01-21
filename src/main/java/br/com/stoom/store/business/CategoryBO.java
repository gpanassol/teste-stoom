package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.ICategoryBO;
import br.com.stoom.store.dto.CategoryDTO;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.mapper.CategoryMapper;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryBO implements ICategoryBO {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findByDeletionDateIsNull();
        if(!categories.isEmpty()) {
            return categories.stream()
                .map(categoryMapper::convertToDTO)
                .collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findByIdAndDeletionDateIsNull(id)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return categoryMapper.convertToDTO(category);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.convertToEntity(categoryDTO);
        Category saved = categoryRepository.save(category);
        return categoryMapper.convertToDTO(saved);
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        categoryRepository.findByIdAndDeletionDateIsNull(id)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        Category category = categoryMapper.convertToEntity(categoryDTO);
        category.setId(id);

        Category saved = categoryRepository.save(category);

        return categoryMapper.convertToDTO(saved);
    }

    @Override
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        category.setId(id);
        category.setDeletionDate(LocalDateTime.now());

        categoryRepository.save(category);
    }

}
