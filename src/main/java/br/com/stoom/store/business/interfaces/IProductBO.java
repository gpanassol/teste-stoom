package br.com.stoom.store.business.interfaces;

import br.com.stoom.store.dto.ProductDTO;
import br.com.stoom.store.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductBO {

    List<ProductDTO> findAll();

    ProductDTO findById(Long id);
}
