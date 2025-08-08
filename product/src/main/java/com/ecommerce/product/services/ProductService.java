package com.ecommerce.product.services;

import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
//@RequiredArgsConstructor
//public class ProductService {
//    private final ProductRepository productRepository;
////    private  ProductRepository productRepository;
////    public ProductService(ProductRepository productRepository) {
////        this.productRepository = productRepository;
////    }
//}


// 2 Way
@Service
@RequiredArgsConstructor
public class ProductService {

    //    this is dependency injection automatically because this is a auto-inject dependency inside it by the annotation is @Spring
    private  final  ProductRepository productRepository;


    public List<ProductResponse> fetchAllProducts(){
        System.out.println("productRepository.findAll() ::::::::::::::::: "  + productRepository.findAll());
//        return  productRepository.findAll().stream()
//                .map(this::mapToProductResponce)
//                .collect(Collectors.toList());

//      JPA Knows findByActiveTrue means -> Find those active state is true
//        return productRepository.findAll().stream()
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponce)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProject = productRepository.save(product);
        return mapToProductResponce(savedProject);
    }

    public Optional<ProductResponse> updateProduct(Long id , ProductRequest productRequest){
        System.out.println("productRepository.findById(id) ::::::::::::::::::::::::: " + productRepository.findById(id));
        return productRepository.findById(id).map(existingProduct -> {
            updateProductFromRequest(existingProduct, productRequest);
            Product savedProduct = productRepository.save(existingProduct);
            return mapToProductResponce(savedProduct);
        });
    }


    public boolean deleteProduct(Long id) {
//        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found")) ;
//        product.setActive(false);
//        productRepository.save(product);

        return productRepository.findById(id).map(product -> {
            System.out.println("product ::::::::::::: " + product);
            product.setActive(false);
            Product updateStatus =  productRepository.save(product);
            System.out.println("updateStatus ::::::::::::: " + updateStatus);
            return true;
        }).orElse(false);

    }

    //  Filter Before Respond
    private ProductResponse mapToProductResponce(Product savedProject) {
        ProductResponse response =  new ProductResponse();
        response.setId(savedProject.getId());
        response.setName(savedProject.getName());
        response.setActive(savedProject.getActive());
        response.setCategory(savedProject.getCategory());
        response.setDescription(savedProject.getDescription());
        response.setPrice(savedProject.getPrice());
        response.setImageUrl(savedProject.getImageUrl());
        response.setStockQuantity(savedProject.getStockQuantity());

        return  response;
    }

    //  Filter Before Passing Request
    private void updateProductFromRequest(Product product , ProductRequest productRequest){
        product.setName(productRequest.getName());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
    }


    public List<ProductResponse> searchProducts(String keyword) {
        return  productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponce)
                .collect(Collectors.toList());
    }
}





