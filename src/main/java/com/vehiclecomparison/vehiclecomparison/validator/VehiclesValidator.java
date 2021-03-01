package com.vehiclecomparison.vehiclecomparison.validator;

import com.vehiclecomparison.vehiclecomparison.entity.Vehicles;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Configuration
public class VehiclesValidator {

    String numericPatternString = "((\\d+)((\\.\\d{1,2})?))$";

    Pattern numericPattern = Pattern.compile(numericPatternString);

    public Map<String, String> validate(Vehicles vehicles) {

        Map<String, String> errorsInData = new HashMap<>();

        if (vehicles.getWheels() == 0 | !(vehicles.getWheels()%2 == 0) || !(vehicles.getWheels() < 20)) {
            errorsInData.put("wheelCountError", "Wheels should be even and les than 20");
        }

        if (!numericPattern.matcher(String.valueOf(vehicles.getEngineSize())).matches()) {
            errorsInData.put("engineSizeError", "Engine size should be in numeric format");
        }

        return errorsInData;
    }

}
