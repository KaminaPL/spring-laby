package org.example.springlab.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
@ToString
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private double price;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> attributes;

    Object getAttribute(String key) {
        return attributes.get(key);
    }

    void removeAttribute(String key) {
        attributes.remove(key);
    }

    void addAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public Vehicle copy() {
        return Vehicle.builder()
                .id(id)
                .category(category)
                .brand(brand)
                .model(model)
                .year(year)
                .price(price)
                .attributes(attributes)
                .build();
    }
}
