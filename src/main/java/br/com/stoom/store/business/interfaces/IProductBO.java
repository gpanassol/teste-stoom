package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.ProductDTO;

import java.util.List;

public interface IProductBO {

    List<ProductDTO> findAll();

    ProductDTO findById(Long id);

    ProductDTO saveProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    List<ProductDTO> findAllByCategoryId(Long categoryId);

    List<ProductDTO> findAllByBrandId(Long brandId);
}
