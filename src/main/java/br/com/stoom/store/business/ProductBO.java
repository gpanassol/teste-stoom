package br.com.stoom.store.business;

import br.com.stoom.store.business.interfaces.IProductBO;
import br.com.stoom.store.dto.ProductDTO;
import br.com.stoom.store.exception.BrandNotFoundException;
import br.com.stoom.store.exception.CategoryNotFoundException;
import br.com.stoom.store.exception.ProductNotFoundException;
import br.com.stoom.store.mapper.ProductMapper;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductBO implements IProductBO {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        if(!products.isEmpty()) {
            return products.stream()
                .map(productMapper::convertToDTO)
                .collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return productMapper.convertToDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {

        this.categoriesValidation(productDTO);
        this.brandsValidation(productDTO);

        Product product = productMapper.convertToEntity(productDTO);
        product.getPrices().forEach(price -> price.setProduct(product));

        Product saved = productRepository.save(product);

        return productMapper.convertToDTO(saved);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {

        productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        this.categoriesValidation(productDTO);
        this.brandsValidation(productDTO);

        Product product = productMapper.convertToEntity(productDTO);
        product.getPrices().forEach(price -> price.setProduct(product));
        product.setId(id);

        Product saved = productRepository.save(product);

        return productMapper.convertToDTO(saved);

    }

    private void categoriesValidation(ProductDTO productDTO) {
        productDTO.getCategories().forEach(categoryDTO -> categoryRepository.findById(categoryDTO.getId())
            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + categoryDTO.getId())));
    }

    private void brandsValidation(ProductDTO productDTO) {
        productDTO.getBrands().forEach(brandDTO -> brandRepository.findById(brandDTO.getId())
            .orElseThrow(() -> new BrandNotFoundException("Brand not found with id: " + brandDTO.getId())));
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDTO> findAllByCategoryId(Long categoryId) {

        List<Product> products = productRepository.findAllByCategoriesId(categoryId);
        if(!products.isEmpty()) {
            return products.stream()
                .map(productMapper::convertToDTO)
                .collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public List<ProductDTO> findAllByBrandId(Long brandId) {
        List<Product> products = productRepository.findAllByBrandsId(brandId);
        if(!products.isEmpty()) {
            return products.stream()
                .map(productMapper::convertToDTO)
                .collect(Collectors.toList());
        }

        return List.of();
    }

}
