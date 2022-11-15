package com.oi.boi.bootstrap;

import com.oi.boi.domain.Category;
import com.oi.boi.domain.Priority;
import com.oi.boi.domain.Vendor;
import com.oi.boi.repo.CategoryReactiveRepo;
import com.oi.boi.repo.VendorReactiveRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryReactiveRepo categoryReactiveRepo;
    private final VendorReactiveRepo vendorReactiveRepo;

    public Bootstrap(CategoryReactiveRepo categoryReactiveRepo, VendorReactiveRepo vendorReactiveRepo) {
        this.categoryReactiveRepo = categoryReactiveRepo;
        this.vendorReactiveRepo = vendorReactiveRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryReactiveRepo.count().block() == 0)
        loadCategories();
        if (vendorReactiveRepo.count().block() == 0)
        loadVendors();
    }

    private void loadVendors() {
        Vendor vendor = Vendor.builder().firstname("Western Tasty Fruits Ltd").lastname("WTFL").build();
        Vendor vendor1 = Vendor.builder().firstname("Exotic Fruits Company").lastname("Exotic").build();
        Vendor vendor2 = Vendor.builder().firstname("Nuts for Nuts Company").lastname("Nuts").build();
        vendorReactiveRepo.saveAll(Arrays.asList(vendor,vendor1,vendor2)).blockFirst();
        log.debug("vendors loaded");
    }

    private void loadCategories() {
        Category category = Category.builder().description("cat 1").priority(Priority.LOW).build();
        Category category1 = Category.builder().description("cat 2").priority(Priority.MEDIUM).build();
        Category category2 = Category.builder().description("cat 3").priority(Priority.HIGH).build();
        categoryReactiveRepo.saveAll(Arrays.asList(category,category1,category2)).blockFirst();
        log.debug("categories loaded");
    }
}
