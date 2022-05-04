package com.example.product_prgrms.service;

import com.example.product_prgrms.exception.InvalidProductStockException;
import com.example.product_prgrms.model.order.OrderItem;
import com.example.product_prgrms.model.product.Product;
import com.example.product_prgrms.model.product.ProductListDTO;
import com.example.product_prgrms.model.product.ProductRequest;
import com.example.product_prgrms.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product findProductById(long productId){
        return productRepository.findById(productId).orElseThrow(IllegalArgumentException::new);
    }

    public List<ProductListDTO> findAllProducts(){
        return productRepository.findAll().stream().map(Product::toListDTO).collect(Collectors.toList());
    }

    public void createProduct(ProductRequest productRequest){
        productRepository.insert(productRequest.toEntity());
    }

    public void changeProductStock(OrderItem item) throws InvalidProductStockException {
        Product updatedProduct = findProductById(item.productId());
        updatedProduct.changeStock(updatedProduct.getStock() - item.quantity());
        updateProduct(updatedProduct);
    }

    public Product updateProduct(Product product){
        return productRepository.update(product);
    }
}
