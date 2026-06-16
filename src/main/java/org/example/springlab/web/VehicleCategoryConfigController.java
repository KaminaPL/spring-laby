package org.example.springlab.web;

import org.example.springlab.models.VehicleCategoryConfig;
import org.example.springlab.services.VehicleCategoryConfigServiceInterface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class VehicleCategoryConfigController {

    private final VehicleCategoryConfigServiceInterface configService;

    public VehicleCategoryConfigController(VehicleCategoryConfigServiceInterface configService) {
        this.configService = configService;
    }

    @GetMapping
    public List<VehicleCategoryConfig> getAll() {
        return configService.getAll();
    }

    @GetMapping("/{category}")
    public VehicleCategoryConfig findByCategory(@PathVariable String category) {
        return configService.findByCategory(category);
    }
}
