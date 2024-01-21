package br.com.stoom.store.mapper;

import br.com.stoom.store.dto.BrandDTO;
import br.com.stoom.store.model.Brand;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public BrandDTO convertToDTO(Brand entity) {
        return modelMapper.map(entity, BrandDTO.class);
    }

    public Brand convertToEntity(BrandDTO brandDTO) {
        return modelMapper.map(brandDTO, Brand.class);
    }

}
