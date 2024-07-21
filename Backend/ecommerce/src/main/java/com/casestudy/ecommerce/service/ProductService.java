package com.casestudy.ecommerce.service;

import com.casestudy.ecommerce.config.JavaConstant;
import com.casestudy.ecommerce.config.ResourceBO;
import com.casestudy.ecommerce.entity.Product;
import com.casestudy.ecommerce.exception.ProductAlreadyExistsException;
import com.casestudy.ecommerce.exception.ProductNotFoundException;
import com.casestudy.ecommerce.repository.ProductRepository;
import com.casestudy.ecommerce.to.ProductTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SequenceGeneratorService generatorService;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public String addProduct(ProductTO product) throws ProductAlreadyExistsException {
        if(!productRepository.existsByName(product.getName()))
        {
            Product productEntity = product.toEntity();
            productEntity.setId(generatorService.getSequenceNumber(Product.SEQUENCE_NAME));
            productRepository.save(productEntity);
            logger.info(ResourceBO.getKey(JavaConstant.PRODUCT_ADD_SUCCESS));
            return ResourceBO.getKey(JavaConstant.PRODUCT_ADD_SUCCESS);
        }
        else {
            logger.error(ResourceBO.getKey(JavaConstant.PRODUCT_ALREADY_EXISTS));
            throw new ProductAlreadyExistsException(ResourceBO.getKey(JavaConstant.PRODUCT_ALREADY_EXISTS));
        }
    }
    public List<Product> getAllProducts() throws ProductNotFoundException
    {
        List<Product> allProducts = productRepository.findAll();
        if(allProducts.isEmpty())
        {
            logger.error(ResourceBO.getKey(JavaConstant.PRODUCT_NOT_FOUND));
            throw new ProductNotFoundException(ResourceBO.getKey(JavaConstant.PRODUCT_NOT_FOUND));

        }
        logger.info(allProducts.toString());
        return allProducts;
    }
    public Product getProductByName(String name) throws ProductNotFoundException
    {
        Optional<Product> product = productRepository.findByName(name);
        if(product.isPresent())
        {
            logger.info(product.toString());
            return product.get();
        }
        else{
            logger.error(ResourceBO.getKey(JavaConstant.PRODUCT_NOT_FOUND));
            throw new ProductNotFoundException(ResourceBO.getKey(JavaConstant.PRODUCT_NOT_FOUND));
        }
    }

    public Product getProductById(Integer productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);
        if(product.isPresent())
        {
            logger.info(product.toString());
            return product.get();
        }
        else{
            logger.error(ResourceBO.getKey(JavaConstant.PRODUCT_NOT_FOUND));
            throw new ProductNotFoundException(ResourceBO.getKey(JavaConstant.PRODUCT_NOT_FOUND));
        }
    }
}
