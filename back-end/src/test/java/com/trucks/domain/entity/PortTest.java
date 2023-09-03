package com.trucks.domain.entity;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import java.util.Random;
import com.trucks.domain.valueobject.Coordinate;
import com.trucks.domain.valueobject.PortName;

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

import com.trucks.domain.entity.Port;
import com.trucks.TurnaroundTimeApplication;
import com.trucks.application.service.PortService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TurnaroundTimeApplication.class)
@AutoConfigureMockMvc
public class PortTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private PortService service;
  private static final Logger logger = LoggerFactory.getLogger(PortTest.class);

  // test the instance of a port
  @Test
  void portInstance() {
    Port port = new Port();
    assertNotNull(port);
  }

  //test the UUID of a port
  @Test
  void portUUID() {
    Port port = new Port();
    for (int i = 0; i < 100; i++) {
      UUID uuid = UUID.randomUUID();
      port.setId(uuid);
      assertTrue(uuid.equals(port.getId()));
    }
  }

  //test the name of a port
  @Test
  void portName() {
    Port port = new Port();
    for (int i = 0; i < 100; i++) {
      PortName name = PortName.random();
      port.setName(name);
      assertTrue(name.equals(port.getName()));
    }
  }

  //test the coordinate of a port
  @Test
  void portCoordinate() {
    Port port = new Port();
    for (int i = 0; i < 100; i++) {
      Coordinate coordinate = Coordinate.random();
      port.setCoordinate(coordinate);
      assertTrue(coordinate.equals(port.getCoordinate()));
    }
  }
}