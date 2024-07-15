package com.oksenda.winterhold.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "Category")
public class Category {
    @Id
    @Column(name = "Name")
    private String name;
    @Column(name = "Floor")
    private Integer floor;
    @Column(name = "Bay")
    private String bay;
    @Column(name = "Isle")
    private String isle;
    @OneToMany(mappedBy = "category")
    private List<Book> books;

    public Integer getTotalBook(){
        return books.size();
    }
}
