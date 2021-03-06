package com.desafiojavaspringboot.uol.api.controller;

import com.desafiojavaspringboot.uol.api.model.Product;
import com.desafiojavaspringboot.uol.api.service.ProductService;
import com.desafiojavaspringboot.uol.api.service.RabbitMQSender;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductService service;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @CrossOrigin
    @ApiOperation(value = "Create a product")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Returns when a product is successfully created"),
            @ApiResponse(code = 400, message = "Validation error")
    })
    @PostMapping
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
        Product product1 = this.service.save(product);
        rabbitMQSender.save(product1);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @CrossOrigin
    @ApiOperation(value = "Updates a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the product was successfully updated"),
            @ApiResponse(code = 404, message = "Returns when the product does not exist"),
            @ApiResponse(code = 400, message = "Validation error"),

    })
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") String id, @Valid @RequestBody Product product) {
        this.service.updateProduct(id, product);
        rabbitMQSender.update(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @CrossOrigin
    @ApiOperation(value = "Search for a product by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the product was found successfully"),
            @ApiResponse(code = 404, message = "Returns when the product does not exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") String id) {
        Optional<Product> product = this.service.findById(id);
        return product.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @ApiOperation(value = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the product is successfully deleted"),
            @ApiResponse(code = 404, message = "Returns when the product does not exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String  id) {
        Optional<Product> product = this.service.findById(id);
        return product.map(value -> {
            this.service.delete(value);
            return new ResponseEntity<>(HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin
    @ApiOperation(value = "Product list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Returns when the products were found successfully or the product list is empty")
    })
    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = this.service.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @CrossOrigin
    @ApiOperation(value = "Filter")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success")
    })
    @GetMapping(value = "/search")
    public ResponseEntity<List<Product>> findByFilter(@RequestParam(required = false) Double min_price,
                                                                @RequestParam(required = false) Double max_price,
                                                                @RequestParam(required = false) String q) {

        List<Product> products = this.service.findByFilter(min_price, max_price, q);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


}
