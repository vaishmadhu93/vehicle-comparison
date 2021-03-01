package com.vehiclecomparison.vehiclecomparison.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehiclecomparison.vehiclecomparison.entity.Vehicles;
import com.vehiclecomparison.vehiclecomparison.service.VehiclesService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class VehiclesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehiclesService vehiclesService;

    @Test
    public void getVehiclesTest() throws Exception {

        List<Vehicles> vehiclesList = getVehiclesList();

        when(vehiclesService.getVehicles()).thenReturn(vehiclesList);

        MvcResult result = mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicles"))
                .andExpect(model().attribute("vehiclesList", vehiclesList))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Assert.assertTrue(response.contains("Duke"));
        Assert.assertTrue(response.contains("Renault"));
    }

    @Test
    public void addVehicleTest() throws Exception {
        Vehicles vehicle = getVehicle();
        when(vehiclesService.saveVehicle(vehicle)).thenReturn(vehicle);
        mockMvc.perform(post("/add-vehicle")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("wheels", String.valueOf(vehicle.getWheels()))
                .param("engineSize", String.valueOf(vehicle.getEngineSize()))
                .param("colour", String.valueOf(vehicle.getColour()))
                .param("modelName", String.valueOf(vehicle.getModelName()))
                .param("carryingCapacity", String.valueOf(vehicle.getCarryingCapacity()))
                .param("leanAngle", String.valueOf(vehicle.getLeanAngle()))
                .param("doors", String.valueOf(vehicle.getDoors()))
                .param("vehicleType", String.valueOf(vehicle.getVehicleType())))
                .andExpect(view().name("redirect:/vehicles"));
    }

    @Test
    public void compareVehicles() throws Exception {
        List<Vehicles> vehiclesList = getVehiclesList();

        String compareType = "wheels";
        Map<String, String> queryParamsMap = getQueryParams("24", "25");

        if (compareType.equalsIgnoreCase("wheels")) {
            Comparator<Vehicles> doorComparator = Comparator.comparing(Vehicles::getWheels);
            vehiclesList.sort(doorComparator.reversed());
        }

        when(vehiclesService.getComparedData(compareType, queryParamsMap)).thenReturn(vehiclesList);

        MvcResult result = mockMvc.perform(get("/compare/vehicles/{compareType}", "wheels")
                .param("vid1", String.valueOf(queryParamsMap.get("vid1")))
                .param("vid2", String.valueOf(queryParamsMap.get("vid2"))))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicle-comparison"))
                .andExpect(model().attribute("comparedVehiclesData", vehiclesList))
                .andExpect(model().attribute("compareType", compareType))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Assert.assertTrue(response.contains("Duke"));
        Assert.assertTrue(response.contains("Renault"));

    }

    public List<Vehicles> getVehiclesList() {

        List<Vehicles> vehiclesList = new ArrayList<>();

        Vehicles vehicleBike = new Vehicles();
        vehicleBike.setWheels(2);
        vehicleBike.setVehicleType("Bike");
        vehicleBike.setEngineSize(100);
        vehicleBike.setColour("Red");
        vehicleBike.setModelName("Duke");
        vehicleBike.setCarryingCapacity(0);
        vehicleBike.setLeanAngle((float) 22.55);
        vehicleBike.setDoors(0);

        Vehicles vehicleCar = new Vehicles();
        vehicleCar.setWheels(4);
        vehicleCar.setVehicleType("Car");
        vehicleCar.setEngineSize(200);
        vehicleCar.setColour("Blue");
        vehicleCar.setModelName("Renault");
        vehicleCar.setCarryingCapacity(0);
        vehicleCar.setLeanAngle(0);
        vehicleCar.setDoors(4);

        vehiclesList.add(vehicleBike);
        vehiclesList.add(vehicleCar);

        return vehiclesList;

    }

    public Vehicles getVehicle() {

        Vehicles vehicleCar = new Vehicles();
        vehicleCar.setWheels(4);
        vehicleCar.setVehicleType("Car");
        vehicleCar.setEngineSize(200);
        vehicleCar.setColour("Blue");
        vehicleCar.setModelName("Renault");
        vehicleCar.setCarryingCapacity(0);
        vehicleCar.setLeanAngle(0);
        vehicleCar.setDoors(4);

        return vehicleCar;
    }

    public String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public Map<String,String> getQueryParams(String vid1, String vid2) {
        Map<String,String> queryParams = new HashMap<>();

        queryParams.put("vid1", vid1);
        queryParams.put("vid2", vid2);

        return queryParams;
    }

}
