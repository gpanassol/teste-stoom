package br.com.stoom.store.mapper;

import br.com.stoom.store.dto.ProductDTO;
import br.com.stoom.store.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public ProductDTO convertToDTO(Product entity) {
        return modelMapper.map(entity, ProductDTO.class);
    }

    public Product convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
