package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.ProductDTO;
import br.com.stoom.store.exception.ProductNotFoundException;
import br.com.stoom.store.mapper.ProductMapper;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.ProductRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductBO implements IProductBO {

    private ProductRepository productRepository;

    private ProductMapper productMapper;

    @Autowired
    public ProductBO(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        if(!products.isEmpty()) {
            return products.stream()
                .map(productMapper::convertoToDTO)
                .collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public ProductDTO findById(Long id){

        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return productMapper.convertoToDTO(product);
    }

}
