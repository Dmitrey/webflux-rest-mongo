package com.oi.boi.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@DataMongoTest
class VendorReactiveRepoIT {

    @Autowired
    VendorReactiveRepo vendorReactiveRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testCountVendors(){
        assertEquals(3, vendorReactiveRepo.count().block());
    }
}