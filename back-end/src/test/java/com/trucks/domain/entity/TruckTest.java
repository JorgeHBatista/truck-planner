package com.trucks.domain.entity;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import com.trucks.domain.valueobject.Plate;
import com.trucks.domain.valueobject.Brand;
import com.trucks.domain.valueobject.EntryPoint;
import com.trucks.domain.valueobject.ExitPoint;
import com.trucks.domain.valueobject.Driver;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

import com.trucks.domain.entity.Truck;
import com.trucks.TurnaroundTimeApplication;
import com.trucks.application.service.TruckService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TurnaroundTimeApplication.class)
@AutoConfigureMockMvc
public class TruckTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private TruckService service;

  // test the instance of a truck
  @Test
  void truckInstance() {
    Truck truck = new Truck();
    assertNotNull(truck);
  }

  //test the UUID of a truck
  @Test
  void truckUUID() {
    Truck truck = new Truck();
    for (int i = 0; i < 100; i++) {
      UUID uuid = UUID.randomUUID();
      truck.setId(uuid);
      assertEquals(uuid, truck.getId());
    }
  }

  // test the plate of a truck (using a constant and a random string)
  @Test
  void truckPlate() {
    Truck truck = new Truck();
    Plate plate = new Plate("1234ABC");
    truck.setPlate(plate);
    assertTrue(plate.equals(truck.getPlate()));
    for (int i = 0; i < 100; i++) {
      Plate randomPlate = Plate.random();
      truck.setPlate(randomPlate);
      assertTrue(randomPlate.equals(truck.getPlate()));  
    }
    assertThrows(IllegalArgumentException.class, () -> truck.setPlate(new Plate("Invalid Plate")));
  }

  // test the brand of a truck
  @Test
  void truckBrand() {
    Truck truck = new Truck();
    Brand brand = new Brand("Ford");
    truck.setBrand(brand);
    assertTrue(brand.equals(truck.getBrand()));
    for (int i = 0; i < 100; i++) {
      Brand randomBrand = Brand.random();
      truck.setBrand(randomBrand);
      assertTrue(randomBrand.equals(truck.getBrand()));  
    }
    assertThrows(IllegalArgumentException.class, () -> truck.setBrand(new Brand("")));}

  // test the unload status of a truck
  @Test
  void truckUnload() {
    Truck truck = new Truck();
    boolean isUnload = true;
    truck.setUnload(isUnload);
    assertEquals(isUnload, truck.isUnload());
  }

  // test the driver of a truck
  @Test
  void truckDriver() {
    Truck truck = new Truck();
    Driver driver = new Driver("Pepe Pérez");
    truck.setDriver(driver);
    assertTrue(driver.equals(truck.getDriver()));
    for (int i = 0; i < 100; i++) {
      Driver randomDriver = Driver.random();
      truck.setDriver(randomDriver);
      assertTrue(randomDriver.equals(truck.getDriver()));  
    }
    assertThrows(IllegalArgumentException.class, () -> truck.setDriver(new Driver("")));
  }
}