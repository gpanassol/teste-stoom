package br.com.stoom.store.controller;

import br.com.stoom.store.business.ProductBO;
import br.com.stoom.store.dto.ProductDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductBO productService;

    @GetMapping(value = "/")
    public ResponseEntity<List<ProductDTO>> findAll() {
        List<ProductDTO> products = productService.findAll();
        if(!products.isEmpty())
            return new ResponseEntity<>(products, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findById(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

}
