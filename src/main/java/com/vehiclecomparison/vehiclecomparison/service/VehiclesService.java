package com.vehiclecomparison.vehiclecomparison.service;

import com.vehiclecomparison.vehiclecomparison.entity.Vehicles;

import java.util.List;
import java.util.Map;

public interface VehiclesService {

    List<Vehicles> getVehicles();

    Vehicles saveVehicle(Vehicles vehicle);

    Vehicles updateVehicleDataById(Vehicles vehicle);

    Vehicles getVehicleById(int vehicleId);

    void deleteVehicleDataById(int vehicleId);

    void deleteVehicle();

    List<Vehicles> getComparedData(String compareType, Map<String, String> queryParamsMap);

}
