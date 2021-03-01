package com.vehiclecomparison.vehiclecomparison.service;

import com.vehiclecomparison.vehiclecomparison.entity.Vehicles;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehiclesServiceTest {

    @Autowired
    private VehiclesService vehiclesService;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Test
    public void saveVehicle_happyPath() {

        Vehicles vehicle = getVehicle();

        Vehicles vehicleSavedResponse = vehiclesService.saveVehicle(vehicle);

        Vehicles vehicleFromDb = vehiclesService.getVehicleById(vehicleSavedResponse.getVehicleId());

        vehiclesService.deleteVehicleDataById(vehicleSavedResponse.getVehicleId());

        Assert.assertEquals(vehicleSavedResponse.toString(), vehicleFromDb.toString());

    }

    @Test
    public void updateVehicleDataById_happyPath() {

        Vehicles vehicle = getVehicle();

        Vehicles vehicleSavedResponse = vehiclesService.saveVehicle(vehicle);

        Vehicles vehicleFromDb = vehiclesService.getVehicleById(vehicleSavedResponse.getVehicleId());

        int vehicleId = vehicleFromDb.getVehicleId();

        Vehicles updatedVehicle = getVehicle();
        updatedVehicle.setVehicleId(vehicleId);
        updatedVehicle.setLeanAngle((float) 25.67);

        Vehicles updatedVehicleResponse = vehiclesService.updateVehicleDataById(updatedVehicle);

        Vehicles updatedVehicleFromDb = vehiclesService.getVehicleById(updatedVehicleResponse.getVehicleId());

        vehiclesService.deleteVehicleDataById(updatedVehicleResponse.getVehicleId());

        Assert.assertEquals(updatedVehicleResponse.toString(), updatedVehicleFromDb.toString());

    }

    @Test
    public void getVehicleById_happyPath() {

        Vehicles vehicle = getVehicle();

        Vehicles vehicleSavedResponse = vehiclesService.saveVehicle(vehicle);

        Vehicles VehicleFromDb = vehiclesService.getVehicleById(vehicleSavedResponse.getVehicleId());

        vehiclesService.deleteVehicleDataById(vehicleSavedResponse.getVehicleId());

        Assert.assertNotNull(VehicleFromDb);
    }

    @Test(expected = NoSuchElementException.class)
    public void deleteVehicleDataById_happyPath() {

        Vehicles vehicle = getVehicle();

        Vehicles vehicleSavedResponse = vehiclesService.saveVehicle(vehicle);

        vehiclesService.deleteVehicleDataById(vehicleSavedResponse.getVehicleId());

        Vehicles VehicleFromDb = vehiclesService.getVehicleById(vehicleSavedResponse.getVehicleId());

    }

    @Test
    public void getComparedDataDoors_happyPath() {

        Vehicles vehicle1 = getSecondVehicle();
        Vehicles vehicle2 = getThirdVehicle();

        int vehicle1Id = vehiclesService.saveVehicle(vehicle1).getVehicleId();
        int vehicle2Id = vehiclesService.saveVehicle(vehicle2).getVehicleId();

        Map<String,String> queryParams = getQueryParams(String.valueOf(vehicle1Id), String.valueOf(vehicle2Id));

        String compareType = "doors";

        List<Vehicles> comparedData = vehiclesService.getComparedData(compareType, queryParams);

        if (compareType.equalsIgnoreCase("doors")) {
            Comparator<Vehicles> doorComparator = Comparator.comparing(Vehicles::getDoors);
            comparedData.sort(doorComparator.reversed());
        }

        vehiclesService.deleteVehicleDataById(vehicle1Id);
        vehiclesService.deleteVehicleDataById(vehicle2Id);

        Vehicles comparedVehicle1 = comparedData.get(0);
        Vehicles ComparedVehicle2 = comparedData.get(1);

        Assert.assertTrue(comparedVehicle1.getDoors() > ComparedVehicle2.getDoors());

    }

    @Test
    public void getComparedDataWheels_happyPath() {

        Vehicles vehicle1 = getSecondVehicle();
        Vehicles vehicle2 = getFourthVehicle();

        int vehicle1Id = vehiclesService.saveVehicle(vehicle1).getVehicleId();
        int vehicle2Id = vehiclesService.saveVehicle(vehicle2).getVehicleId();

        Map<String,String> queryParams = getQueryParams(String.valueOf(vehicle1Id), String.valueOf(vehicle2Id));

        String compareType = "wheels";

        List<Vehicles> comparedData = vehiclesService.getComparedData(compareType, queryParams);

        if (compareType.equalsIgnoreCase("wheels")) {
            Comparator<Vehicles> wheelComparator = Comparator.comparing(Vehicles::getWheels);
            comparedData.sort(wheelComparator.reversed());
        }

        vehiclesService.deleteVehicleDataById(vehicle1Id);
        vehiclesService.deleteVehicleDataById(vehicle2Id);

        Vehicles comparedVehicle1 = comparedData.get(0);
        Vehicles comparedVehicle2 = comparedData.get(1);

        Assert.assertTrue(comparedVehicle1.getWheels() > comparedVehicle2.getWheels());

    }

    @Test
    public void getComparedDataEngineSize_happyPath() {

        Vehicles vehicle1 = getSecondVehicle();
        Vehicles vehicle2 = getThirdVehicle();

        int vehicle1Id = vehiclesService.saveVehicle(vehicle1).getVehicleId();
        int vehicle2Id = vehiclesService.saveVehicle(vehicle2).getVehicleId();

        Map<String,String> queryParams = getQueryParams(String.valueOf(vehicle1Id), String.valueOf(vehicle2Id));

        String compareType = "engineSize";

        List<Vehicles> comparedData = vehiclesService.getComparedData(compareType, queryParams);

        if (compareType.equalsIgnoreCase("engineSize")) {
            Comparator<Vehicles> engineSizeComparator = Comparator.comparing(Vehicles::getEngineSize);
            comparedData.sort(engineSizeComparator.reversed());
        }

        vehiclesService.deleteVehicleDataById(vehicle1Id);
        vehiclesService.deleteVehicleDataById(vehicle2Id);

        Vehicles comparedVehicle1 = comparedData.get(0);
        Vehicles comparedVehicle2 = comparedData.get(1);

        Assert.assertTrue(comparedVehicle1.getEngineSize() > comparedVehicle2.getEngineSize());

    }

    @Test
    public void getComparedDataCarryingCapacity_happyPath() {

        Vehicles vehicle1 = getThirdVehicle();
        Vehicles vehicle2 = getFourthVehicle();

        int vehicle1Id = vehiclesService.saveVehicle(vehicle1).getVehicleId();
        int vehicle2Id = vehiclesService.saveVehicle(vehicle2).getVehicleId();

        Map<String,String> queryParams = getQueryParams(String.valueOf(vehicle1Id), String.valueOf(vehicle2Id));

        String compareType = "carryingCapacity";

        List<Vehicles> comparedData = vehiclesService.getComparedData(compareType, queryParams);

        if (compareType.equalsIgnoreCase("carryingCapacity")) {
            Comparator<Vehicles> carryingCapacityComparator = Comparator.comparing(Vehicles::getCarryingCapacity);
            comparedData.sort(carryingCapacityComparator.reversed());
        }

        vehiclesService.deleteVehicleDataById(vehicle1Id);
        vehiclesService.deleteVehicleDataById(vehicle2Id);

        Vehicles comparedVehicle1 = comparedData.get(0);
        Vehicles comparedVehicle2 = comparedData.get(1);

        Assert.assertTrue(comparedVehicle1.getCarryingCapacity() > comparedVehicle2.getCarryingCapacity());

    }

    @Test
    public void getComparedDataLeanAngle_happyPath() {

        Vehicles vehicle1 = getVehicle();
        Vehicles vehicle2 = getFifthVehicle();

        int vehicle1Id = vehiclesService.saveVehicle(vehicle1).getVehicleId();
        int vehicle2Id = vehiclesService.saveVehicle(vehicle2).getVehicleId();

        Map<String,String> queryParams = getQueryParams(String.valueOf(vehicle1Id), String.valueOf(vehicle2Id));

        String compareType = "leanAngle";

        List<Vehicles> comparedData = vehiclesService.getComparedData(compareType, queryParams);

        if (compareType.equalsIgnoreCase("leanAngle")) {
            Comparator<Vehicles> leanAngleComparator = Comparator.comparing(Vehicles::getLeanAngle);
            comparedData.sort(leanAngleComparator.reversed());
        }

        vehiclesService.deleteVehicleDataById(vehicle1Id);
        vehiclesService.deleteVehicleDataById(vehicle2Id);

        Vehicles comparedVehicle1 = comparedData.get(0);
        Vehicles comparedVehicle2 = comparedData.get(1);

        Assert.assertTrue(comparedVehicle1.getLeanAngle() > comparedVehicle2.getLeanAngle());

    }

    public Vehicles getVehicle() {

        Vehicles vehicle = new Vehicles();
        vehicle.setWheels(2);
        vehicle.setVehicleType("Bike");
        vehicle.setEngineSize(100);
        vehicle.setColour("Red");
        vehicle.setModelName("Duke");
        vehicle.setCarryingCapacity(0);
        vehicle.setLeanAngle((float) 22.55);
        vehicle.setDoors(0);

        return vehicle;

    }

    public Vehicles getSecondVehicle() {

        Vehicles vehicle = new Vehicles();
        vehicle.setWheels(4);
        vehicle.setVehicleType("Car");
        vehicle.setEngineSize(200);
        vehicle.setColour("Blue");
        vehicle.setModelName("Renault");
        vehicle.setCarryingCapacity(0);
        vehicle.setLeanAngle(0);
        vehicle.setDoors(4);

        return vehicle;

    }

    public Vehicles getThirdVehicle() {

        Vehicles vehicle = new Vehicles();
        vehicle.setWheels(8);
        vehicle.setVehicleType("Lorry");
        vehicle.setEngineSize(300);
        vehicle.setColour("Green");
        vehicle.setModelName("Mercedez");
        vehicle.setCarryingCapacity(2000);
        vehicle.setLeanAngle(0);
        vehicle.setDoors(2);

        return vehicle;

    }

    public Vehicles getFourthVehicle() {

        Vehicles vehicle = new Vehicles();
        vehicle.setWheels(8);
        vehicle.setVehicleType("Lorry");
        vehicle.setEngineSize(400);
        vehicle.setColour("Yellow");
        vehicle.setModelName("Tata");
        vehicle.setCarryingCapacity(3000);
        vehicle.setLeanAngle(0);
        vehicle.setDoors(2);

        return vehicle;

    }

    public Vehicles getFifthVehicle() {

        Vehicles vehicle = new Vehicles();
        vehicle.setWheels(2);
        vehicle.setVehicleType("Bike");
        vehicle.setEngineSize(100);
        vehicle.setColour("Black");
        vehicle.setModelName("Royal Enfield");
        vehicle.setCarryingCapacity(0);
        vehicle.setLeanAngle((float) 35.567);
        vehicle.setDoors(0);

        return vehicle;

    }

    public Map<String,String> getQueryParams(String vid1, String vid2) {
        Map<String,String> queryParams = new HashMap<>();

        queryParams.put("vid1", vid1);
        queryParams.put("vid2", vid2);

        return queryParams;
    }

}
