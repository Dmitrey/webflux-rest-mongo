package com.oi.boi.repo;

import com.oi.boi.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryReactiveRepo extends ReactiveMongoRepository<Category, String> {
}
