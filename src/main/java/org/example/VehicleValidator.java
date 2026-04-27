package org.example;

import org.example.models.Vehicle;
import org.example.models.VehicleCategoryConfig;
import org.example.services.VehicleCategoryConfigService;

import java.util.Map;

public class VehicleValidator {

    private final VehicleCategoryConfigService configService;

    public VehicleValidator(VehicleCategoryConfigService configService) {
        this.configService = configService;
    }

    public void validate(Vehicle vehicle) {
        if(vehicle == null) throw new IllegalStateException("Vehicle cannot be null.");
        validateBaseFields(vehicle);
        validateAttributes(vehicle.getAttributes(), configService.findByCategory(vehicle.getCategory()));
    }

    private void validateBaseFields(Vehicle vehicle) {
        requireNotBlank(vehicle.getCategory(), "Category required.");
        requireNotBlank(vehicle.getBrand(), "Brand required");
        requireNotBlank(vehicle.getModel(), "Model required.");
        if(vehicle.getYear() <= 0) throw new IllegalArgumentException("Year cannot be zero nor negative");
        if(vehicle.getPrice() < 0) throw new IllegalArgumentException("Year cannot be zero");
    }

    private void validateAttributes(Map<String, Object> actualAttributes, VehicleCategoryConfig config) {
        Map<String, String> expectedAttributes = config.getAttributes();
        for(String actualName : actualAttributes.keySet()) {
            if(!expectedAttributes.containsKey(actualName)) {
                throw new IllegalArgumentException("Illegal attribute: " + config.getCategory() + ": " + actualName);
            }
        }
        expectedAttributes.forEach((attrName, expectedType) -> {
            Object value = actualAttributes.get(attrName);
            if(value == null) throw new IllegalArgumentException("Missing required attribute: " + attrName);
            if(expectedType.equalsIgnoreCase("string") && value instanceof String) {
                requireNotBlank(String.valueOf(value), "Attribute " + attrName + " cannot be empty.");
            }
            boolean isValidType = switch(expectedType.toLowerCase()) {
                case "string" -> value instanceof String;
                case "number" -> value instanceof Number;
                case "boolean" -> value instanceof Boolean;
                case "integer" -> value instanceof Integer;
                default -> throw new IllegalArgumentException("Illegal type in config: " + expectedType);
            };
            if(!isValidType) throw new IllegalArgumentException("Attribute " + attrName +
                    " must be of type " + expectedType + ".");
        });
    }

    private void requireNotBlank(String value, String message) {
        if(value == null || value.isBlank()) throw new IllegalStateException(message);
    }
}
