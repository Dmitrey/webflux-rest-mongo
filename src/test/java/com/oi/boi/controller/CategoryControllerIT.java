package com.oi.boi.controller;

import com.oi.boi.domain.Category;
import com.oi.boi.domain.Priority;
import com.oi.boi.repo.CategoryReactiveRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryControllerIT {

    WebTestClient webTestClient;

    @Autowired
    CategoryReactiveRepo categoryReactiveRepo;

    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryController = new CategoryController(categoryReactiveRepo);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void patchDescription() {

        Category category = Category.builder().description("test-cat").priority(Priority.LOW).build();

        Mono<Category> save = categoryReactiveRepo.save(category);

        Mono<Category> categoryFromRequest = Mono.just(
                Category.builder().description("cat_updated").build()
        );

        String id = Objects.requireNonNull(save.block()).getId();

        Category categoryUpdated = Category.builder().id(id).description("cat_updated").priority(Priority.LOW).build();

        webTestClient.patch().uri("/api/v1/categories/" + id)
                .body(categoryFromRequest, Category.class)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(categoryUpdated);

        categoryReactiveRepo.deleteById(id).block();
    }

    @Test
    void patchPriority() {

        Category category = Category.builder().description("test-cat").priority(Priority.LOW).build();

        Mono<Category> save = categoryReactiveRepo.save(category);

        Mono<Category> categoryFromRequest = Mono.just(
                Category.builder().priority(Priority.HIGH).build()
        );

        String id = Objects.requireNonNull(save.block()).getId();

        Category categoryUpdated = Category.builder().id(id).description("test-cat").priority(Priority.HIGH).build();

        webTestClient.patch().uri("/api/v1/categories/" + id)
                .body(categoryFromRequest, Category.class)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(categoryUpdated);

        categoryReactiveRepo.deleteById(id).block();
    }

    @Test
    void patchWithEmptyBody() {

        Category category = Category.builder().description("test-cat").priority(Priority.LOW).build();

        Mono<Category> save = categoryReactiveRepo.save(category);

        Mono<Category> categoryFromRequest = Mono.just(
                Category.builder().build()
        );

        String id = Objects.requireNonNull(save.block()).getId();

        Category categoryUpdated = Category.builder().id(id).description("test-cat").priority(Priority.LOW).build();

        webTestClient.patch().uri("/api/v1/categories/" + id)
                .body(categoryFromRequest, Category.class)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(categoryUpdated);

        categoryReactiveRepo.deleteById(id).block();
    }

}