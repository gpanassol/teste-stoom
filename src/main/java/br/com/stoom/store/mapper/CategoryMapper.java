package br.com.stoom.store.mapper;

import br.com.stoom.store.dto.CategoryDTO;
import br.com.stoom.store.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public CategoryDTO convertToDTO(Category entity) {
        return modelMapper.map(entity, CategoryDTO.class);
    }

    public Category convertToEntity(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }

}
