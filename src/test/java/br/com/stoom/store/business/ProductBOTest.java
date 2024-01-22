package br.com.stoom.store.business;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.stoom.store.dto.ProductDTO;
import br.com.stoom.store.exception.ProductNotFoundException;
import br.com.stoom.store.mapper.ProductMapper;
import br.com.stoom.store.model.Product;
import br.com.stoom.store.repository.BrandRepository;
import br.com.stoom.store.repository.CategoryRepository;
import br.com.stoom.store.repository.ProductRepository;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProductBOTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductBO productBO;

    //findAll
    @Test
    public void shouldFindAllProducts() {

        when(productRepository.findByDeletionDateIsNull()).thenReturn(Collections.singletonList(new Product()));
        when(productMapper.convertToDTO(any())).thenReturn(ProductDTO.builder()
            .id(1l)
            .sku("MASC1234AZUL")
            .name("Tênis")
            .description("Tênis masculino azul")
            .build());

        List<ProductDTO> result = productBO.findAll();

        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getId(), 1l);
        assertEquals(result.get(0).getSku(), "MASC1234AZUL");
        assertEquals(result.get(0).getName(), "Tênis");
        assertEquals(result.get(0).getDescription(), "Tênis masculino azul");
    }

    //findByID
    @Test
    public void shouldFindProductById() {

        Long id = 1l;
        when(productRepository.findByIdAndDeletionDateIsNull(id)).thenReturn(Optional.of(new Product()));
        when(productMapper.convertToDTO(any())).thenReturn(ProductDTO.builder()
            .id(1l)
            .sku("MASC1234AZUL")
            .name("Tênis")
            .description("Tênis masculino azul")
            .build());

        ProductDTO result = productBO.findById(id);

        assertEquals(result.getId(), 1l);
        assertEquals(result.getSku(), "MASC1234AZUL");
        assertEquals(result.getName(), "Tênis");
        assertEquals(result.getDescription(), "Tênis masculino azul");
    }

    //findAllByCategoryID
    @Test
    public void shouldFindAllProductByCategoryId() {

        Long id = 1l;
        when(productRepository.findAllByCategoriesIdAndDeletionDateIsNull(id))
            .thenReturn(Collections.singletonList(new Product()));
        when(productMapper.convertToDTO(any())).thenReturn(ProductDTO.builder()
            .id(1l)
            .sku("MASC1234AZUL")
            .name("Tênis")
            .description("Tênis masculino azul")
            .build());

        List<ProductDTO> result = productBO.findAllByCategoryId(id);

        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getId(), 1l);
        assertEquals(result.get(0).getSku(), "MASC1234AZUL");
        assertEquals(result.get(0).getName(), "Tênis");
        assertEquals(result.get(0).getDescription(), "Tênis masculino azul");
    }

    //findAllByBrandID
    @Test
    public void shouldFindAllProductByBrandId() {

        Long id = 1l;
        when(productRepository.findAllByBrandsIdAndDeletionDateIsNull(id))
            .thenReturn(Collections.singletonList(new Product()));
        when(productMapper.convertToDTO(any())).thenReturn(ProductDTO.builder()
            .id(1l)
            .sku("MASC1234AZUL")
            .name("Tênis")
            .description("Tênis masculino azul")
            .build());

        List<ProductDTO> result = productBO.findAllByBrandId(id);

        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getId(), 1l);
        assertEquals(result.get(0).getSku(), "MASC1234AZUL");
        assertEquals(result.get(0).getName(), "Tênis");
        assertEquals(result.get(0).getDescription(), "Tênis masculino azul");
    }

    //saveProduct
    @Test
    public void shouldSaveProduct() {
        ProductDTO productDTO = ProductDTO.builder()
            .categories(new HashSet<>())
            .brands(new HashSet<>())
            .build();
        Product product = new Product();
        Product savedProduct = new Product();
        Long id = 1l;

        //when
        when(productMapper.convertToDTO(savedProduct)).thenReturn(productDTO);
        when(productMapper.convertToEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);

        ProductDTO result = productBO.saveProduct(productDTO);

        assertNotNull(result);
        assertEquals(productDTO, result);
    }

    //updateProduct
    @Test
    public void shouldUpdateProduct() {
        ProductDTO productDTO = ProductDTO.builder()
            .categories(new HashSet<>())
            .brands(new HashSet<>())
            .build();
        Product product = new Product();
        Product savedProduct = new Product();
        Long id = 1l;

        //when
        when(productMapper.convertToDTO(savedProduct)).thenReturn(productDTO);
        when(productMapper.convertToEntity(productDTO)).thenReturn(product);
        when(productRepository.findByIdAndDeletionDateIsNull(id)).thenReturn(Optional.of(new Product()));
        when(productRepository.save(product)).thenReturn(savedProduct);

        ProductDTO result = productBO.updateProduct(id, productDTO);

        assertNotNull(result);
        assertEquals(productDTO, result);
    }

    //deleteProduct
    @Test
    public void shouldDeleteProductById() {

        Long productId = 1L;
        Product mockProduct = new Product();

        when(productRepository.findByIdAndDeletionDateIsNull(productId)).thenReturn(Optional.of(mockProduct));

        assertDoesNotThrow(() -> productBO.deleteProduct(productId));

        verify(productRepository).save(mockProduct);
        assertNotNull(mockProduct.getDeletionDate());
    }

    @Test
    public void shouldReturnExceptionWhenFindProductByIdNotFound() {

        Long productId = 1L;

        when(productRepository.findByIdAndDeletionDateIsNull(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productBO.findById(productId);
        });

        verify(productRepository).findByIdAndDeletionDateIsNull(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    public void shouldReturnExceptionWhenUpdateProductByIdNotFound() {

        Long productId = 1L;

        when(productRepository.findByIdAndDeletionDateIsNull(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productBO.updateProduct(productId, any());
        });

        verify(productRepository).findByIdAndDeletionDateIsNull(productId);
        verify(productRepository, never()).save(any());
    }

    @Test
    public void shouldReturnExceptionWhenDeleteProductByIdNotFound() {

        Long productId = 1L;

        when(productRepository.findByIdAndDeletionDateIsNull(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            productBO.deleteProduct(productId);
        });

        verify(productRepository).findByIdAndDeletionDateIsNull(productId);
        verify(productRepository, never()).save(any());
    }
}
