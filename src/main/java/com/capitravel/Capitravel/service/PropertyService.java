package com.capitravel.Capitravel.service;

import com.capitravel.Capitravel.model.Property;

import java.util.List;

public interface PropertyService {

    Property getPropertyById(Long id);

    List<Property> getAllProperty();

    Property createProperty(Property property);

    Property updateProperty(Long id, Property property);

    void deleteProperty(Long id);
}
