package com.oi.boi.controller;

import com.oi.boi.domain.Category;
import com.oi.boi.repo.CategoryReactiveRepo;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryReactiveRepo categoryReactiveRepo;

    public CategoryController(CategoryReactiveRepo categoryReactiveRepo) {
        this.categoryReactiveRepo = categoryReactiveRepo;
    }

    @GetMapping
    Flux<Category> getAllCategories() {
        return categoryReactiveRepo.findAll();
    }

    @GetMapping("/{id}")
    Mono<Category> getCategoryByID(@PathVariable(name = "id") String id) {
        return categoryReactiveRepo.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Flux<Category> create(@RequestBody Publisher<Category> publisher) {
        return categoryReactiveRepo.saveAll(publisher);
    }

    @PutMapping("/{id}")
    Mono<Category> update(@RequestBody Category category, @PathVariable(name = "id") String id) {
        category.setId(id);
        return categoryReactiveRepo.save(category);
    }

    @PatchMapping("/{id}")
    Mono<Category> patch(@RequestBody Category category, @PathVariable(name = "id") String id) {

        Mono<Category> categoryMono = categoryReactiveRepo.findById(id);

        return categoryMono
                .flatMap(f -> {
                    if (category.getDescription() != null) {
                        f.setDescription(category.getDescription());
                    }
                    if (category.getPriority() != null) {
                        f.setPriority(category.getPriority());
                    }
                    return categoryReactiveRepo.save(f);
                });
    }

}