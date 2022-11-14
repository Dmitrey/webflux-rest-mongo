package com.oi.boi.controller;

import com.oi.boi.domain.Vendor;
import com.oi.boi.repo.VendorReactiveRepo;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/vendors")
public class VendorController {

    private final VendorReactiveRepo vendorReactiveRepo;

    public VendorController(VendorReactiveRepo vendorReactiveRepo) {
        this.vendorReactiveRepo = vendorReactiveRepo;
    }

    @GetMapping
    public Flux<Vendor> getAllVendors(){
        return vendorReactiveRepo.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> getVendorById(@PathVariable(name = "id") String id){
        return vendorReactiveRepo.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Flux<Vendor> create(@RequestBody Publisher<Vendor> publisher){
        return vendorReactiveRepo.saveAll(publisher);
    }

    @PutMapping("/{id}")
    Mono<Vendor> update(@RequestBody Vendor vendor, @PathVariable String id){
        vendor.setId(id);
        return vendorReactiveRepo.save(vendor);
    }
}