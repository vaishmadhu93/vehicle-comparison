package com.vehiclecomparison.vehiclecomparison.service;

import com.vehiclecomparison.vehiclecomparison.entity.Vehicles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VehiclesServiceImpl implements VehiclesService {

    private static Logger logger = LoggerFactory.getLogger(VehiclesServiceImpl.class);

    @Autowired
    private VehiclesRepository vehiclesRepository;

    public List<Vehicles> getVehicles() {
        List<Vehicles> vehicleList = new ArrayList<>();
        for (Vehicles vehicle : vehiclesRepository.findAll()) {
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public Vehicles saveVehicle(Vehicles vehicle) {
        return vehiclesRepository.save(vehicle);
    }

    public Vehicles updateVehicleDataById(Vehicles vehicle) {
        return vehiclesRepository.save(vehicle);
    }

    public Vehicles getVehicleById(int vehicleId) {
        Vehicles singleVehicle = vehiclesRepository.findById(vehicleId).get();
        return singleVehicle;
    }

    public void deleteVehicleDataById(int vehicleId) {
        vehiclesRepository.deleteById(vehicleId);
    }

    public void deleteVehicle() {
        vehiclesRepository.deleteAll();
    }

    @Override
    public List<Vehicles> getComparedData(String compareType, Map<String, String> queryParamsMap) {

        int vehicleId;
        List<Vehicles> comparedVehicleDatas = new ArrayList<>();

        for (Map.Entry<String, String> queryParam : queryParamsMap.entrySet()) {
            vehicleId = Integer.parseInt(queryParam.getValue().trim());
            Vehicles vehicle = getVehicleById(vehicleId);
            comparedVehicleDatas.add(vehicle);
        }

        if (compareType.equalsIgnoreCase("doors")) {
            Comparator<Vehicles> doorComparator = Comparator.comparing(Vehicles::getDoors);
            comparedVehicleDatas.sort(doorComparator.reversed());
        }

        if (compareType.equalsIgnoreCase("wheels")) {
            Comparator<Vehicles> wheelComparator = Comparator.comparing(Vehicles::getWheels);
            comparedVehicleDatas.sort(wheelComparator.reversed());
        }

        if (compareType.equalsIgnoreCase("engineSize")) {
            Comparator<Vehicles> engineSizeComparator = Comparator.comparing(Vehicles::getEngineSize);
            comparedVehicleDatas.sort(engineSizeComparator.reversed());
        }

        if (compareType.equalsIgnoreCase("carryingCapacity")) {
            Comparator<Vehicles> carryingCapacityComparator = Comparator.comparing(Vehicles::getCarryingCapacity);
            comparedVehicleDatas.sort(carryingCapacityComparator.reversed());
        }

        if (compareType.equalsIgnoreCase("leanAngle")) {
            Comparator<Vehicles> leanAngleComparator = Comparator.comparing(Vehicles::getLeanAngle);
            comparedVehicleDatas.sort(leanAngleComparator.reversed());
        }

        return comparedVehicleDatas;
    }

}
