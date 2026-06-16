package org.example.springlab.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name="vehicle_configs")
public class VehicleCategoryConfig
{
    @Id
    @Column(nullable = false, unique = true)
    private String category;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSONB")
    private Map<String, String> attributes;

    public Map<String, String> getAttributes()
    {
        return Collections.unmodifiableMap(attributes);
    }

    public void addAttribute(String name, String type)
    {
        attributes.put(name, type);
    }

    public void removeAttribute(String name)
    {
        attributes.remove(name);
    }

    public VehicleCategoryConfig copy()
    {
        return VehicleCategoryConfig.builder()
                .category(category)
                .attributes(new HashMap<>(attributes))
                .build();
    }
}
