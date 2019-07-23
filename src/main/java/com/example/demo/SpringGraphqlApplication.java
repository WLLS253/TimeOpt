package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpringGraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGraphqlApplication.class, args);
    }


//    @Bean
//    public Query query(
//
//            AuthorRepository authorRepository,
//            BookRepository bookRepository
//    ){
//        return  new Query(
//                authorRepository,
//                bookRepository
//        );
//
//    }
//
//    @Bean
//    public Mutation mutation(
//            AuthorRepository authorRepository,
//            BookRepository bookRepository
//
//    ){
//        return  new Mutation(
//                authorRepository,
//                bookRepository
//        );
//    }


}
