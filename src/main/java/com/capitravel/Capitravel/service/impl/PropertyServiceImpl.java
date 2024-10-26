package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.model.Property;
import com.capitravel.Capitravel.repository.PropertyRepository;
import com.capitravel.Capitravel.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyServiceImpl(PropertyRepository propertyRepository){
        this.propertyRepository = propertyRepository;
    }

    @Override
    public Property getPropertyById(Long id) {
        Property idProperty = propertyRepository.findById(id).orElse(null);

        if(idProperty!=null){
            return idProperty;
        }
        throw new IllegalArgumentException("Category for id:" + id + " not found.");
    }

    @Override
    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }

    @Override
    public Property createProperty(Property property) {

        if(validateProperty(property)) {
            return propertyRepository.save(property);
        }
        throw new IllegalArgumentException("Property does not follow basic structure.");

    }

    @Override
    public Property updateProperty(Long id, Property property) {
        Optional<Property> existingProperty = propertyRepository.findById(id);

        if (existingProperty.isPresent() && validateProperty(property)) {
            Property updateProperty = existingProperty.get();
            updateProperty.setName(property.getName());
            updateProperty.setDescription(property.getDescription());
            updateProperty.setImage(property.getImage());
            return propertyRepository.save(updateProperty);
        }
        throw new IllegalArgumentException("The Property for id:" + id + " not found.");
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    public boolean validateProperty (Property property){
        if (property.getName() == null || property.getName().isEmpty() || property.getName().length() < 3 || property.getName().length() > 30) {
            throw new IllegalArgumentException("Property name must be between 3 and 30 characters long.");
        }
        else if (property.getDescription() == null || property.getDescription().isEmpty() || property.getDescription().length() < 5 || property.getDescription().length() > 50) {
            throw new IllegalArgumentException("Property description must be between 5 and 50 characters long.");
        }
        return true;
    }
}
