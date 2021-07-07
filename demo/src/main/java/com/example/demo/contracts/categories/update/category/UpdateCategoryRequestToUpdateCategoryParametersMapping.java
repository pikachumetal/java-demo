package com.example.demo.contracts.categories.update.category;

import com.example.demo.use.cases.categories.update.UpdateCategoryParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UpdateCategoryRequestToUpdateCategoryParametersMapping
        extends PropertyMap<UpdateCategoryRequest, UpdateCategoryParameters> {
    @Override
    protected void configure() {
        map().description = source.description;
        map().email = source.email;
    }
}