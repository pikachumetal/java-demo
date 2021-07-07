package com.example.demo.contracts.categories.add.category;

import com.example.demo.use.cases.categories.add.AddCategoryParameters;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AddCategoryRequestToAddCategoryParametersMapping extends PropertyMap<AddCategoryRequest, AddCategoryParameters> {
    @Override
    protected void configure() {
        map().description = source.description;
        map().email = source.email;
    }
}