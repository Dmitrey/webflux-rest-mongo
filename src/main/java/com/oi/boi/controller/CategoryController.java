package com.oi.boi.controller;

import com.oi.boi.domain.Category;
import com.oi.boi.repo.CategoryReactiveRepo;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryReactiveRepo categoryReactiveRepo;

    public CategoryController(CategoryReactiveRepo categoryReactiveRepo) {
        this.categoryReactiveRepo = categoryReactiveRepo;
    }

    @GetMapping
    Flux<Category> getAllCategories(){
        return categoryReactiveRepo.findAll();
    }

    @GetMapping("/{id}")
    Mono<Category> getCategoryByID(@PathVariable(name = "id") String id){
        return categoryReactiveRepo.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Flux<Category> create(@RequestBody Publisher<Category> publisher){
        return categoryReactiveRepo.saveAll(publisher);
    }

    @PutMapping("/{id}")
    Mono<Category> update(@RequestBody Category category, @PathVariable(name = "id") String id){
        category.setId(id);
        return categoryReactiveRepo.save(category);
    }

    @PatchMapping("/{id}")
    Mono<Category> patch(@RequestBody Category category, @PathVariable(name = "id") String id){

        Mono<Category> categoryMono = categoryReactiveRepo.findById(id);
//todo не работает как надо
        Mono<Category> mono = categoryMono.filter(found -> !Objects.equals(found.getDescription(), category.getDescription()))
                .flatMap(f -> {
                    f.setDescription(category.getDescription());
                    return categoryReactiveRepo.save(f);
                }).switchIfEmpty(categoryMono);

//        if (category != null){
//            if (category.getDescription() != null){
//                categoryOld.setDescription(category.getDescription());
//                categoryReactiveRepo.save(categoryOld);
//            }
//        }

        return mono;
    }

}