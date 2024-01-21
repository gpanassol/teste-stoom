package br.com.stoom.store.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PriceDTO {

    private Long id;
    private Integer amount;
    private BigDecimal price;
}
