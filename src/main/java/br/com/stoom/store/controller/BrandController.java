package br.com.stoom.store.controller;

import br.com.stoom.store.business.BrandBO;
import br.com.stoom.store.dto.BrandDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/brand")
public class BrandController {

    @Autowired
    private BrandBO brandService;

    @GetMapping
    public ResponseEntity<List<BrandDTO>> findAll() {
        List<BrandDTO> brandDTOs = brandService.findAll();
        if(!brandDTOs.isEmpty())
            return new ResponseEntity<>(brandDTOs, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BrandDTO> findById(@PathVariable Long id) {
        BrandDTO brandDTO = brandService.findById(id);
        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BrandDTO> create(@RequestBody BrandDTO brandDTO) {
        BrandDTO saveBrand = brandService.saveBrand(brandDTO);
        return new ResponseEntity<>(saveBrand, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BrandDTO> updated(@PathVariable Long id, @RequestBody BrandDTO brandDTO) {
        BrandDTO updateBrand = brandService.updateBrand(id, brandDTO);
        return new ResponseEntity<>(updateBrand, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
