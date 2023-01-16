package com.kais.gestiondestock.conroller.api;

import com.kais.gestiondestock.conroller.CategoryApi;
import com.kais.gestiondestock.dto.CategoryDto;
import com.kais.gestiondestock.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController implements CategoryApi {

    CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public CategoryDto save(CategoryDto dto) {
        return this.categoryService.save(dto);
    }

    @Override
    public CategoryDto findById(Integer id) {
        return this.categoryService.findById(id);
    }

    @Override
    public List<CategoryDto> findAll() {
        return this.categoryService.findAll();
    }

    @Override
    public void delete(Integer id) {
        this.categoryService.delete(id);
    }
}
