package com.tanmay.e_commerce_application.catalog_service.Entity;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tanmay.e_commerce_application.catalog_service.DTO.Request.CategoryRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Category parentId;

    @OneToMany(mappedBy = "parentId", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonManagedReference
    private Set<Category> children;

    @OneToMany(mappedBy = "categoryId")
    @JsonManagedReference
    private Set<Product> products;

    public static Category toEntity(CategoryRequestDTO cDto, Category parent){
        return Category.builder()
            .name(cDto.getName())
            .parentId(parent)
            .build();
    }

    public static Category toEntity(CategoryRequestDTO cDto, Category parent, UUID id){
        return Category.builder()
            .id(id)
            .name(cDto.getName())
            .parentId(parent)
            .build();
    }
}
