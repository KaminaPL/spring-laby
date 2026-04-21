package org.example.models;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of="id")
@ToString
public class Vehicle
{
    private String id;
    private String category;
    private String brand;
    private String model;
    private int year;
    private double price;
    private Map<String, Object> attributes;

    Object getAttribute(String key)
    {
        return attributes.get(key);
    }

    void removeAttribute(String key)
    {
        attributes.remove(key);
    }

    void addAttribute(String key, Object value)
    {
        attributes.put(key, value);
    }

    public Vehicle copy()
    {
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
