package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.model.Property;
import com.capitravel.Capitravel.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    @Autowired
    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long id){
        Property property = propertyService.getPropertyById(id);
        if (property != null){
            return ResponseEntity.ok(property);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Property>> getAllPropertys(){
        List<Property> properties = propertyService.getAllProperty();
        return ResponseEntity.ok(properties);
    }

    @PostMapping
    public ResponseEntity<Property> createProperty(@RequestBody Property property){
        Property createdProperty = propertyService.createProperty(property);
        return ResponseEntity.status(201).body(createdProperty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Property> updateProperty(@PathVariable Long id, @RequestBody Property property){
        Property updateProperty = propertyService.updateProperty(id, property);
        if (updateProperty != null) {
            return ResponseEntity.ok(updateProperty);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long id){
        propertyService.deleteProperty(id);
        return ResponseEntity.noContent().build();
    }
}
