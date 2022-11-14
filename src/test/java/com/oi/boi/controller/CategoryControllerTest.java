package com.oi.boi.controller;

import com.oi.boi.domain.Category;
import com.oi.boi.repo.CategoryReactiveRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

import static org.mockito.ArgumentMatchers.any;

class CategoryControllerTest {

    WebTestClient webTestClient;

    @Mock
    CategoryReactiveRepo categoryReactiveRepo;

    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryReactiveRepo = Mockito.mock(CategoryReactiveRepo.class);
        categoryController = new CategoryController(categoryReactiveRepo);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void getAllCategories() {
        BDDMockito.given(categoryReactiveRepo.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().id("sdfsdf").description("cat1").build(),
                                Category.builder().id("rtyfsdf").description("cat2").build())
                );

        webTestClient.get().uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getCategoryByID() {
        Category category = Category.builder().id("sdfsdf").description("cat1").build();
        String id = "some_id";

        BDDMockito.given(categoryReactiveRepo.findById(id))
                .willReturn(Mono.just(category));

        webTestClient.get().uri("/api/v1/categories/" + id)
                .exchange()
                .expectBody(Category.class)
                .isEqualTo(category);
    }

    @Test
    void testCreate() {
        BDDMockito.given(categoryReactiveRepo.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(
                        Category.builder().build()
                        )
                );

        Mono<Category> categoryMono = Mono.just(Category.builder().id("sdfsdf").description("cat1").build());

        webTestClient.post().uri("/api/v1/categories")
                .body(categoryMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

}