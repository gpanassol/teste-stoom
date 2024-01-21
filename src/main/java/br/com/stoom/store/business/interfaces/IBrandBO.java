package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.BrandDTO;
import java.util.List;

public interface IBrandBO {

    List<BrandDTO> findAll();

    BrandDTO findById(Long id);

    BrandDTO saveBrand(BrandDTO brandDTO);

    BrandDTO updateBrand(Long id, BrandDTO brandDTO);

    void deleteBrand(Long id);
}
