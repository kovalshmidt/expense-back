package com.example.codeengine.expense.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
public class User {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    public User() {
        this.expenses = new ArrayList<>();
    }

}




