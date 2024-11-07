package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.model.Category;
import com.capitravel.Capitravel.service.CategoryService;
import com.capitravel.Capitravel.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.capitravel.Capitravel.dto.CategoryDTO;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final EmailService emailService;

    @Autowired
    public CategoryController(CategoryService categoryService, EmailService emailService) {
        this.categoryService = categoryService;
        this.emailService = emailService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);

        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        Category createdCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(201).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,@Valid @RequestBody CategoryDTO categoryDTO) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDTO);

        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register/{id}")
    public String registerUser(@PathVariable Long id) {
        // Lógica de registro del usuario
        
        try {
            String confirmationLink = "http://tu-aplicacion.com/confirmar?token=ejemploToken";
            String body = "<h1>Bienvenido a nuestra aplicación</h1>"
                    + "<p>Por favor, haz clic en el siguiente enlace para confirmar tu cuenta:</p>"
                    + "<a href='" + confirmationLink + "'>Confirmar cuenta</a>";

            emailService.sendConfirmationEmail("musacchioromina@gmail.com", "Confirma tu cuenta", body);
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error al enviar el correo";
        }

        return "Registro exitoso. Por favor revisa tu correo para confirmar tu cuenta.";
    }
}
