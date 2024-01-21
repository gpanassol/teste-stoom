package br.com.stoom.store.dto;

import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String sku;
    private String name;
    private String description;

    private List<PriceDTO> prices;
    private Set<CategoryDTO> categories;
    private Set<BrandDTO> brands;

}
