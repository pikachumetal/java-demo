package com.example.demo.contracts.categories.common;

import com.example.demo.domain.Category;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryResponseMapping extends PropertyMap<Category, CategoryResponse> {
    @Override
    protected void configure() {
        map().id = source.id;
        map().email = source.email;
        map().description = source.description;
        map().active = source.active;
        map().created = source.created;
        map().updated = source.updated;
    }
}