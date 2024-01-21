package br.com.stoom.store.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
    @SequenceGenerator(name = "product_sequence", sequenceName = "PRODUCT_SEQ")
    @Column(name = "id")
    private Long id;

    @Column(name = "sku")
    private String sku;
}