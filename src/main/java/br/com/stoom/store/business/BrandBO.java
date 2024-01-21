package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IBrandBO;
import br.com.stoom.store.dto.BrandDTO;
import br.com.stoom.store.exception.BrandNotFoundException;
import br.com.stoom.store.mapper.BrandMapper;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandBO implements IBrandBO {

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<BrandDTO> findAll() {
        List<Brand> brands = brandRepository.findAll();
        if(!brands.isEmpty()) {
            return brands.stream()
                .map(brandMapper::convertToDTO)
                .collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public BrandDTO findById(Long id) {
        Brand brand = brandRepository.findById(id)
            .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + id));
        return brandMapper.convertToDTO(brand);
    }

    @Override
    public BrandDTO saveBrand(BrandDTO brandDTO) {
        Brand brand = brandMapper.convertToEntity(brandDTO);
        Brand saved = brandRepository.save(brand);
        return brandMapper.convertToDTO(saved);
    }

    @Override
    public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
        brandRepository.findById(id)
            .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + id));

        Brand brand = brandMapper.convertToEntity(brandDTO);
        brand.setId(id);

        Brand saved = brandRepository.save(brand);

        return brandMapper.convertToDTO(saved);
    }

    @Override
    public void deleteBrand(Long id) {
        brandRepository.findById(id)
            .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + id));
        brandRepository.deleteById(id);
    }
}
