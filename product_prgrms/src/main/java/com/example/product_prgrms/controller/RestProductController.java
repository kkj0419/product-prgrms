package com.example.product_prgrms.controller;

import com.example.product_prgrms.model.product.ProductListDTO;
import com.example.product_prgrms.model.product.ProductRequest;
import com.example.product_prgrms.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController()
@RequestMapping("/api/v1")
public class RestProductController {

    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<ProductListDTO> getProductList() {
        return productService.findAllProducts();
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest request) {
        try {
            productService.createProduct(request);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new String("생성 성공".getBytes(), StandardCharsets.UTF_8), HttpStatus.OK);
    }

    @GetMapping("/products/search")
    public List<ProductListDTO> getSearchProductList(@RequestParam String searchType, @RequestParam String searchKeyword) {
        return productService.search(searchType, searchKeyword);
    }
}
