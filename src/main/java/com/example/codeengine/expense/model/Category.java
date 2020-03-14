package com.example.codeengine.expense.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;
    //Travel, Grocery
    private String name;
}
