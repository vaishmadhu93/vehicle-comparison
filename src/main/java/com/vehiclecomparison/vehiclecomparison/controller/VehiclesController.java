package com.vehiclecomparison.vehiclecomparison.controller;

import com.vehiclecomparison.vehiclecomparison.entity.*;
import com.vehiclecomparison.vehiclecomparison.service.VehiclesService;
import com.vehiclecomparison.vehiclecomparison.validator.VehiclesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class VehiclesController {

    @Autowired
    private VehiclesService vehiclesService;

    @Autowired
    private VehiclesValidator vehiclesValidator;

    @RequestMapping(value = "/")
    public String startUp() {
        return "home";
    }

    @GetMapping(value = "/compare/vehicles/{compareType}")
    public String compareVehicle(@PathVariable("compareType") String compareType, @RequestParam Map<String, String> queryParamsMap, ModelMap model) {

        List<Vehicles> comparedVehicleDatas = new ArrayList<>();

        comparedVehicleDatas = vehiclesService.getComparedData(compareType, queryParamsMap);

        model.addAttribute("comparedVehiclesData", comparedVehicleDatas);
        model.addAttribute("compareType", compareType);
        return "vehicle-comparison";

    }

    @GetMapping(value = "/vehicles")
    public String getVehicles(ModelMap model) {

        List<Vehicles> vehiclesList = vehiclesService.getVehicles();
        model.addAttribute("vehiclesList", vehiclesList);
        return "vehicles";

    }

    @GetMapping(value = "/add-vehicle")
    public String addVehicle(ModelMap model) {
        model.addAttribute("type", "new");
        return "add-vehicles";
    }

    @PostMapping(value = "/add-vehicle")
    public String saveVehicle(@ModelAttribute Vehicles vehicles, RedirectAttributes attr, ModelMap model) {
        boolean validData = validateData(vehicles, model);
        if (validData) {
            vehiclesService.saveVehicle(vehicles);
            attr.addFlashAttribute("message", "Created");
            return "redirect:/vehicles";
        } else {
            attr.addFlashAttribute("message", "Failed");
            if (model.containsAttribute("wheelCountError")) {
                attr.addFlashAttribute("wheelCountError", true);
            }
            if (model.containsAttribute("engineSizeError")) {
                attr.addFlashAttribute("engineSizeError", true);
            }
            return "redirect:/add-vehicle";
        }
    }

    @GetMapping(value = "/vehicle/update/{id}")
    public String updateVehicleGetById(@PathVariable("id") int vehicleId, ModelMap model) {
        Vehicles vehicle = vehiclesService.getVehicleById(vehicleId);
        model.addAttribute("type", "edit");
        model.addAttribute("vehicle", vehicle);
        return "add-vehicles";
    }

    @RequestMapping(value = "/update-vehicle/{id}")
    public String updateVehicleDataById(@ModelAttribute Vehicles vehicles, @PathVariable("id") int vehicleId, RedirectAttributes attr, ModelMap model) {
        vehicles.setVehicleId(vehicleId);
        boolean validData = validateData(vehicles, model);
        if (validData) {
            vehiclesService.updateVehicleDataById(vehicles);
            attr.addFlashAttribute("message", "Updated");
            return "redirect:/vehicles";
        } else {
            attr.addFlashAttribute("vehicle", vehicles);
            attr.addFlashAttribute("message", "Failed");
            if (model.containsAttribute("wheelCountError")) {
                attr.addFlashAttribute("wheelCountError", true);
            }
            if (model.containsAttribute("engineSizeError")) {
                attr.addFlashAttribute("engineSizeError", true);
            }
            return "redirect:/vehicle/update/" + vehicleId;
        }
    }

    @RequestMapping(value = "/vehicle/delete/{id}")
    public String deleteVehicleDataById(@PathVariable("id") int vehicleId, RedirectAttributes attr) {
        vehiclesService.deleteVehicleDataById(vehicleId);
        attr.addFlashAttribute("message", "Deleted");
        return "redirect:/vehicles";
    }

    @DeleteMapping(value = "/vehicles")
    public void deleteVehicle() {
        vehiclesService.deleteVehicle();
    }

    public boolean validateData(Vehicles vehicles, ModelMap model) {
        Map<String, String> errorsInData = vehiclesValidator.validate(vehicles);

        if (errorsInData.size() > 0) {
            for (Map.Entry<String, String> error : errorsInData.entrySet()) {
                model.addAttribute(error.getKey(), true);
            }
            return false;
        } else {
            return true;
        }
    }

}
