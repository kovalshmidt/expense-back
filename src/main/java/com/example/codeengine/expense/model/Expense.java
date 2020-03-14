package com.example.codeengine.expense.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    @GeneratedValue
    private UUID id;
    private LocalDateTime expenseDate;
    private String description;
    private String location;
    @JsonIgnore
    @ManyToOne
    private User user;
    @ManyToOne
    private Category category;
}
