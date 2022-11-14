package com.oi.boi.controller;

import com.oi.boi.domain.Vendor;
import com.oi.boi.repo.VendorReactiveRepo;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class VendorControllerTest {

    WebTestClient webTestClient;

    @Mock
    VendorReactiveRepo vendorReactiveRepo;

    VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorReactiveRepo = Mockito.mock(VendorReactiveRepo.class);
        VendorController vendorController = new VendorController(vendorReactiveRepo);
        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void getAllVendors() {
        BDDMockito.given(vendorReactiveRepo.findAll()).willReturn(
                Flux.just(
                        Vendor.builder().firstname("Exotic Fruits Company").lastname("Exotic").build(),
                        Vendor.builder().firstname("Nuts for Nuts Company").lastname("Nuts").build()
                )
        );

        webTestClient.get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getVendorById() {
        String id = "oidi";
        Vendor vendor = Vendor.builder().firstname("Exotic Fruits Company").lastname("Exotic").build();
        BDDMockito.given(vendorReactiveRepo.findById(id)).willReturn(
                Mono.just(
                        vendor
                )
        );

        webTestClient.get()
                .uri("/api/v1/vendors/" + id)
                .exchange()
                .expectBody(Vendor.class)
                .isEqualTo(vendor);
    }

    @Test
    void testCreate() {

        Vendor vendor = Vendor.builder().firstname("Exotic Fruits Company").lastname("Exotic").build();
        BDDMockito.given(vendorReactiveRepo.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(vendor));

        Flux<Vendor> flux = Flux.just(
                Vendor.builder().build(),
                Vendor.builder().build()
        );

        webTestClient.post()
                .uri("/api/v1/vendors")
                .body(flux,Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }


    @Test
    void testUpdate() {

        Vendor vendor = Vendor.builder().firstname("Exotic Fruits Company").lastname("Exotic").build();
        BDDMockito.given(vendorReactiveRepo.save(any(Vendor.class)))
                .willReturn(Mono.just(vendor));

        Mono<Vendor> mono = Mono.just(
                Vendor.builder().build()
        );

        webTestClient.put()
                .uri("/api/v1/vendors/vendor_id")
                .body(mono,Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}