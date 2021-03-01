package com.vehiclecomparison.vehiclecomparison.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehiclecomparison.vehiclecomparison.entity.Vehicles;
import com.vehiclecomparison.vehiclecomparison.service.VehiclesService;
import org.junit.Assert;
import org.junit.Ignore;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        Assert.assertTrue(response.contains("Mercedez"));
    }

    @Ignore
    @Test
    public void addVehicleTest() throws Exception {
        Vehicles vehicle = getVehicle();
        when(vehiclesService.saveVehicle(vehicle)).thenReturn(vehicle);
        mockMvc.perform(post("/add-vehicle")
                .content(asJsonString(vehicle))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(view().name("redirect:/vehicles"));
    }

    @Ignore
    @Test
    public void compareVehicles() throws Exception {
        List<Vehicles> vehiclesList = getVehiclesList();

        String compareType = "doors";

        if (compareType.equalsIgnoreCase("doors")) {
            Comparator<Vehicles> doorComparator = Comparator.comparing(Vehicles::getDoors);
            vehiclesList.sort(doorComparator.reversed());
        }

        when(vehiclesService.getVehicles()).thenReturn(vehiclesList);

        MvcResult result = mockMvc.perform(get("/compare/vehicles/{compareType}", "doors"))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicle-comparison"))
                .andExpect(model().attribute("comparedVehiclesData", vehiclesList))
                .andExpect(model().attribute("compareType", compareType))
                .andReturn();


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

        Vehicles vehicleLorry = new Vehicles();
        vehicleLorry.setWheels(8);
        vehicleLorry.setVehicleType("Lorry");
        vehicleLorry.setEngineSize(300);
        vehicleLorry.setColour("Green");
        vehicleLorry.setModelName("Mercedez");
        vehicleLorry.setCarryingCapacity(2000);
        vehicleLorry.setLeanAngle(0);
        vehicleLorry.setDoors(2);

        vehiclesList.add(vehicleBike);
        vehiclesList.add(vehicleCar);
        vehiclesList.add(vehicleLorry);

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

}
