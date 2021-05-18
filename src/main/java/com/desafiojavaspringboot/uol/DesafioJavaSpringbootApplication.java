package com.desafiojavaspringboot.uol;

import com.desafiojavaspringboot.uol.api.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = ProductRepository.class)
public class DesafioJavaSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioJavaSpringbootApplication.class, args);

    }

}
