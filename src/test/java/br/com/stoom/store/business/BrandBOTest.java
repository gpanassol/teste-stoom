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

import br.com.stoom.store.dto.BrandDTO;
import br.com.stoom.store.exception.BrandNotFoundException;
import br.com.stoom.store.mapper.BrandMapper;
import br.com.stoom.store.model.Brand;
import br.com.stoom.store.repository.BrandRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BrandBOTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandBO brandBO;

    @Test
    public void shouldFindAllBrands() {

        when(brandRepository.findByDeletionDateIsNull()).thenReturn(Collections.singletonList(new Brand()));
        when(brandMapper.convertToDTO(any())).thenReturn(BrandDTO.builder()
            .id(1l)
            .name("Nike")
            .build());

        List<BrandDTO> result = brandBO.findAll();

        assertFalse(result.isEmpty());
        assertEquals(result.get(0).getId(), 1l);
        assertEquals(result.get(0).getName(), "Nike");
    }

    @Test
    public void shouldFindBrandById() {

        Long BrandId = 1L;
        Brand mockBrand = new Brand();

        when(brandRepository.findByIdAndDeletionDateIsNull(BrandId)).thenReturn(Optional.of(mockBrand));
        when(brandMapper.convertToDTO(any())).thenReturn(BrandDTO.builder()
            .id(1l)
            .name("Nike")
            .build());


        BrandDTO result = brandBO.findById(BrandId);

        assertNotNull(result);
        assertEquals(result.getId(), 1l);
        assertEquals(result.getName(), "Nike");
    }

    @Test
    public void shouldSaveBrand() {
        BrandDTO BrandDTO = new BrandDTO();
        Brand brand = new Brand();
        Brand savedBrand = new Brand();

        //when
        when(brandMapper.convertToDTO(savedBrand)).thenReturn(BrandDTO);
        when(brandMapper.convertToEntity(BrandDTO)).thenReturn(brand);
        when(brandRepository.save(brand)).thenReturn(savedBrand);

        BrandDTO result = brandBO.saveBrand(BrandDTO);

        assertNotNull(result);
        assertEquals(BrandDTO, result);
    }

    //updated
    @Test
    public void shouldUpdateBrand() {
        Long id = 1l;
        BrandDTO BrandDTO = new BrandDTO();
        Brand Brand = new Brand();
        Brand savedBrand = new Brand();

        //when
        when(brandRepository.findByIdAndDeletionDateIsNull(id)).thenReturn(Optional.of(new Brand()));
        when(brandMapper.convertToDTO(savedBrand)).thenReturn(BrandDTO);
        when(brandMapper.convertToEntity(BrandDTO)).thenReturn(Brand);
        when(brandRepository.save(Brand)).thenReturn(savedBrand);

        BrandDTO result = brandBO.updateBrand(id, BrandDTO);

        assertNotNull(result);
        assertEquals(BrandDTO, result);
    }

    @Test
    public void shouldDeleteBrandById() {

        Long BrandId = 1L;
        Brand mockBrand = new Brand();

        when(brandRepository.findByIdAndDeletionDateIsNull(BrandId)).thenReturn(Optional.of(mockBrand));

        assertDoesNotThrow(() -> brandBO.deleteBrand(BrandId));

        verify(brandRepository).save(mockBrand);
        assertNotNull(mockBrand.getDeletionDate());
    }

    @Test
    public void shouldReturnExceptionWhenFindBrandByIdNotFound() {

        Long brandId = 1L;

        when(brandRepository.findByIdAndDeletionDateIsNull(brandId)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> {
            brandBO.findById(brandId);
        });

        verify(brandRepository).findByIdAndDeletionDateIsNull(brandId);
        verify(brandRepository, never()).save(any());
    }

    @Test
    public void shouldReturnExceptionWhenUpdateBrandByIdNotFound() {

        Long brandId = 1L;

        when(brandRepository.findByIdAndDeletionDateIsNull(brandId)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> {
            brandBO.updateBrand(brandId, any());
        });

        verify(brandRepository).findByIdAndDeletionDateIsNull(brandId);
        verify(brandRepository, never()).save(any());
    }

    @Test
    public void shouldReturnExceptionWhenDeleteBrandByIdNotFound() {

        Long brandId = 1L;

        when(brandRepository.findByIdAndDeletionDateIsNull(brandId)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> {
            brandBO.deleteBrand(brandId);
        });

        verify(brandRepository).findByIdAndDeletionDateIsNull(brandId);
        verify(brandRepository, never()).save(any());
    }

}
