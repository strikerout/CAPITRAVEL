package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.dto.PropertyDTO;
import com.capitravel.Capitravel.exception.DuplicatedResourceException;
import com.capitravel.Capitravel.exception.ResourceNotFoundException;
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
        Property property = propertyRepository.findById(id).orElse(null);

        if(property != null){
            return property;
        }
        throw new ResourceNotFoundException("Property for id: " + id + " not found.");
    }

    @Override
    public List<Property> getAllProperty() {
        return propertyRepository.findAll();
    }


    @Override
    public Property createProperty(PropertyDTO propertyDTO) {
        Property property = new Property();
        property.setName(propertyDTO.getName().trim());
        property.setDescription(propertyDTO.getDescription().trim());
        property.setImage(propertyDTO.getImage().trim());

        if(propertyExistsByName(property.getName()).isEmpty()) {
            return propertyRepository.save(property);
        }
        throw new DuplicatedResourceException("Property with name '" + property.getName() + "' already exists.");

    }

    @Override
    public Property updateProperty(Long id, PropertyDTO propertyDTO) {
        Optional<Property> existingProperty = propertyRepository.findById(id);

        if (existingProperty.isEmpty()) {
            throw new ResourceNotFoundException("The Property for id: " + id + " not found.");
        }

        Optional<Property> sameNameProperty = propertyRepository.findByName(propertyDTO.getName());

        if (sameNameProperty.isPresent() && !sameNameProperty.get().getId().equals(id)) {
            throw new DuplicatedResourceException("Property with name '" + propertyDTO.getName() + "' already exists.");
        }

        Property updatedProperty = existingProperty.get();
        updatedProperty.setName(propertyDTO.getName().trim());
        updatedProperty.setDescription(propertyDTO.getDescription().trim());
        updatedProperty.setImage(propertyDTO.getImage().trim());

        return propertyRepository.save(updatedProperty);
    }

    @Override
    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }

    public Optional<Property> propertyExistsByName(String name) {
        return propertyRepository.findByName(name.trim());
    }
}
