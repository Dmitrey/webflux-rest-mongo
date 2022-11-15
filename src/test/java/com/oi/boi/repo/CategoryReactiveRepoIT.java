package com.oi.boi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class CategoryReactiveRepoIT {

    @Autowired
    CategoryReactiveRepo categoryReactiveRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testDeleteAll() {
        categoryReactiveRepo.deleteAll().block();
        assertEquals(0, categoryReactiveRepo.count().block());
    }
}