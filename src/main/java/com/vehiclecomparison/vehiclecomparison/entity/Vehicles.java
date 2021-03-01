package com.vehiclecomparison.vehiclecomparison.entity;

import com.google.common.base.Strings;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vehicles")
public class Vehicles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private int vehicleId;
    @NotNull
    @Column(name = "wheels")
    private int wheels;
    @NotNull
    @Column(name = "engine_size")
    private int engineSize;
    @NotBlank
    @Column(name = "colour")
    private String colour;
    @NotBlank
    @Column(name = "model_name")
    private String modelName;

    @Column(name = "carrying_capacity")
    private int carryingCapacity;
    @Column(name = "lean_angle")
    private float leanAngle;
    @Column(name = "doors")
    private int doors;
    @NotNull
    @Column(name = "vehicle_type")
    private String vehicleType;

    public Vehicles() {

    }

    public Vehicles(int wheels, int engineSize, String colour, String modelName, int carryingCapacity,
                    float leanAngle, int doors, String vehicleType) {

        super();
        this.vehicleId = vehicleId;
        this.wheels = wheels;
        this.engineSize = engineSize;
        this.colour = colour;
        this.modelName = modelName;
        this.carryingCapacity = carryingCapacity;
        this.leanAngle = leanAngle;
        this.doors = doors;
        this.vehicleType = vehicleType;

    }


    public int getWheels() {
        return wheels;
    }

    public void setWheels(int wheels) {
        this.wheels = wheels;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public void setEngineSize(int engineSize) {
        this.engineSize = engineSize;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public int getCarryingCapacity() {
        return carryingCapacity;
    }

    public void setCarryingCapacity(int carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public float getLeanAngle() {
        return leanAngle;
    }

    public void setLeanAngle(float leanAngle) {
        this.leanAngle = leanAngle;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "{" + "\n" + "Vehicles" + "\n[\n"
                + "Vehicle Id: " + vehicleId + "\n"
                + "Wheels: " + wheels + "\n"
                + "Engine Size: " + engineSize + "\n"
                + "Colour: " + Strings.nullToEmpty(colour) + "\n"
                + "Model Name: " + Strings.nullToEmpty(modelName) + "\n"
                + "Carrying Capacity: " + carryingCapacity + "\n"
                + "Lean Angle: " + leanAngle + "\n"
                + "Doors: " + doors + "\n"
                + "Vehicle Type: " + vehicleType + "\n"
                + "]\n" + "}";
    }
}
