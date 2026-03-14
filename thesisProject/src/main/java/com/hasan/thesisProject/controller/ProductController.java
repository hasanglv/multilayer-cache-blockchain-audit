package com.hasan.thesisProject.controller;

import com.hasan.thesisProject.dto.CacheAuditRecordDTO;
import com.hasan.thesisProject.dto.ProductRequestDTO;
import com.hasan.thesisProject.dto.ProductResponseDTO;
import com.hasan.thesisProject.service.BlockchainService;
import com.hasan.thesisProject.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final BlockchainService blockchainService;


    @PostMapping
    public ProductResponseDTO create(@RequestBody ProductRequestDTO request) {
        return productService.create(request);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping
    public List<ProductResponseDTO> getAll() {
        return productService.findAll();
    }

    @GetMapping("/blockchain/count")
    public String getCount() throws Exception {
        return blockchainService.getRecordCount().toString();
    }
    @GetMapping("/blockchain/record/{index}")
    public CacheAuditRecordDTO getRecord(@PathVariable BigInteger index) throws Exception {
        BigInteger count = blockchainService.getRecordCount();
        if (index.compareTo(count) >= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Record not found. Current count is " + count + ". Try index 0.");
        }
        return blockchainService.getRecord(index);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(@PathVariable Long id,
                                     @RequestBody ProductRequestDTO request) {
        return productService.update(id, request);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}