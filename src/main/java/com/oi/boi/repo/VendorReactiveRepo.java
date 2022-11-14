package com.oi.boi.repo;

import com.oi.boi.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorReactiveRepo extends ReactiveMongoRepository<Vendor,String> {
}
