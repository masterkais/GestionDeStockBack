package com.kais.gestiondestock.validaor;

import com.kais.gestiondestock.dto.CategoryDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class CategoryValidator {
    public static List<String> validate(CategoryDto categoryDto) {
        List<String> errors = new ArrayList<>() ;
        if ( categoryDto==null || !StringUtils.hasLength(categoryDto.getCode())){
          errors.add("Veuiller renseigner le code de la categorie");
        }


        return errors;
    }
}
