package org.example.models;

import lombok.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class VehicleCategoryConfig
{
    private String category;
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
