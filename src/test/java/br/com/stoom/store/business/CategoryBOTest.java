package br.com.stoom.store.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.stoom.store.dto.CategoryDTO;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.mapper.CategoryMapper;
import br.com.stoom.store.model.Category;
import br.com.stoom.store.repository.CategoryRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CategoryBOTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryBO categoryBO;

    @Test
    public void shouldFindAllCategories() {

        when(categoryRepository.findByDeletionDateIsNull()).thenReturn(Collections.singletonList(new Category()));
        when(categoryMapper.convertToDTO(any())).thenReturn(CategoryDTO.builder()
                .id(1l)
                .name("Tênis")
            .build());

        List<CategoryDTO> result = categoryBO.findAll();

        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getId(), 1l);
        assertEquals(result.get(0).getName(), "Tênis");
    }

    @Test
    public void shouldFindCategoryById() {

        Long categoryId = 1L;
        Category mockCategory = new Category();

        when(categoryRepository.findByIdAndDeletionDateIsNull(categoryId)).thenReturn(Optional.of(mockCategory));
        when(categoryMapper.convertToDTO(any())).thenReturn(CategoryDTO.builder()
            .id(1l)
            .name("Tênis")
            .build());


        CategoryDTO result = categoryBO.findById(categoryId);

        assertNotNull(result);
        assertEquals(result.getId(), 1l);
        assertEquals(result.getName(), "Tênis");
    }

    @Test
    public void shouldSaveCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder().build();
        Category category = new Category();
        Category savedCategory = new Category();

        //when
        when(categoryMapper.convertToDTO(savedCategory)).thenReturn(categoryDTO);
        when(categoryMapper.convertToEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);

        CategoryDTO result = categoryBO.saveCategory(categoryDTO);

        assertNotNull(result);
        assertEquals(categoryDTO, result);
    }

    //updated
    @Test
    public void shouldUpdateCategory() {
        Long id = 1l;
        CategoryDTO categoryDTO = CategoryDTO.builder().build();
        Category category = new Category();
        Category savedCategory = new Category();

        //when
        when(categoryRepository.findByIdAndDeletionDateIsNull(id)).thenReturn(Optional.of(new Category()));
        when(categoryMapper.convertToDTO(savedCategory)).thenReturn(categoryDTO);
        when(categoryMapper.convertToEntity(categoryDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);

        CategoryDTO result = categoryBO.updateCategory(id, categoryDTO);

        assertNotNull(result);
        assertEquals(categoryDTO, result);
    }

    @Test
    public void shouldDeleteCategoryById() {

        Long categoryId = 1L;
        Category mockCategory = new Category();

        when(categoryRepository.findByIdAndDeletionDateIsNull(categoryId)).thenReturn(Optional.of(mockCategory));

        assertDoesNotThrow(() -> categoryBO.deleteCategory(categoryId));

        verify(categoryRepository).save(mockCategory);
        assertNotNull(mockCategory.getDeletionDate());
    }

    @Test
    public void shouldReturnExceptionWhenFindCategoryByIdNotFound() {

        Long categoryId = 1L;

        when(categoryRepository.findByIdAndDeletionDateIsNull(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryBO.findById(categoryId);
        });

        verify(categoryRepository).findByIdAndDeletionDateIsNull(categoryId);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void shouldReturnExceptionWhenUpdateCategoryByIdNotFound() {

        Long categoryId = 1L;

        when(categoryRepository.findByIdAndDeletionDateIsNull(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryBO.updateCategory(categoryId, any());
        });

        verify(categoryRepository).findByIdAndDeletionDateIsNull(categoryId);
        verify(categoryRepository, never()).save(any());
    }

    @Test
    public void shouldReturnExceptionWhenDeleteCategoryByIdNotFound() {

        Long categoryId = 1L;

        when(categoryRepository.findByIdAndDeletionDateIsNull(categoryId)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryBO.deleteCategory(categoryId);
        });

        verify(categoryRepository).findByIdAndDeletionDateIsNull(categoryId);
        verify(categoryRepository, never()).save(any());
    }

}
