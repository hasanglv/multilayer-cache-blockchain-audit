package com.hasan.thesisProject.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {

    private String name;
    private Double price;
    private Integer stock;
    private String category;
}
