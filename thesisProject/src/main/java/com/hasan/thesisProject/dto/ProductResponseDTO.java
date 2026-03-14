package com.hasan.thesisProject.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDTO implements Serializable {

    private Long id;
    private String name;
    private String category;
    private Double price;
    private Integer stock;
}
