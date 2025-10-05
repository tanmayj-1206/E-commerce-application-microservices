package com.tanmay.e_commerce_application.catalog_service.Entity;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tanmay.e_commerce_application.catalog_service.DTO.Request.CategoryRequestDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@AllArgsConstructor
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    
    private String name;

    private String description;

    @OneToMany(mappedBy = "categoryId")
    @JsonManagedReference
    private Set<Product> products;

    public static Category toEntity(CategoryRequestDTO cDto){
        return Category.builder()
            .name(cDto.getName())
            .description(cDto.getDescription())
            .build();
    }

    public static Category toEntity(CategoryRequestDTO cDto, UUID id){
        return Category.builder()
            .id(id)
            .name(cDto.getName())
            .description(cDto.getDescription())
            .build();
    }
}
