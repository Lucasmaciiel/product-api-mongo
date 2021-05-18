package com.desafiojavaspringboot.uol.api.repository;
import com.desafiojavaspringboot.uol.api.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    @Query("{ $and: [ { 'price': { $gte: ?0 } },{ 'price': { $lte: ?1 } } ] }")
    List<Product> findByMinPriceMaxPrice(Double min, Double max);

}
