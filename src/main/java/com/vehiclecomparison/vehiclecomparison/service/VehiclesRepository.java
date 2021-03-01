package com.vehiclecomparison.vehiclecomparison.service;

import com.vehiclecomparison.vehiclecomparison.entity.Vehicles;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface VehiclesRepository extends CrudRepository<Vehicles, Integer> {

//    @Query
//    void updateColumnByVehicleId(String columnValue, int vehicleId);

}
