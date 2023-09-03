package com.trucks.domain.entity;

import com.trucks.domain.valueobject.EntryPoint;
import com.trucks.domain.valueobject.ExitPoint;
import com.trucks.domain.valueobject.IdentificationType;
import com.trucks.domain.entity.Access;
import com.trucks.domain.entity.Port;
import com.trucks.domain.entity.Truck;
import com.trucks.application.service.AccessService;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import com.trucks.TurnaroundTimeApplication;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TurnaroundTimeApplication.class)
@AutoConfigureMockMvc
public class AccessTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private AccessService service;

  // test the instance of a access
  @Test
  void accessInstance() {
    Access access = new Access();
    assertNotNull(access);
  }

  //test the id of an access
  @Test
  void accessId() {
    Access access = new Access();
    for (int i = 0; i < 100; i++) {
      UUID uuid = UUID.randomUUID();
      access.setId(uuid);
      assertTrue(uuid.equals(access.getId()));
    }
  }

  //test the entry point of a access
  @Test
  void accessEntryPoint() {
    Access access = new Access();
    for (int i = 0; i < 100; i++) {
      EntryPoint entryPoint = EntryPoint.random();
      access.setEntryPoint(entryPoint);
      assertTrue(entryPoint.equals(access.getEntryPoint()));
    }
  }

  //test the exit point of a access
  @Test
  void accessExitPoint() {
    Access access = new Access();
    for (int i = 0; i < 100; i++) {
      ExitPoint exitPoint = ExitPoint.random();
      access.setExitPoint(exitPoint);
      assertTrue(exitPoint.equals(access.getExitPoint()));
    }
  }

  //test the entry date of a access
  @Test
  void accessEntryDate() {
    Access access = new Access();
    for (int i = 0; i < 100; i++) {
      LocalDateTime entryDate = LocalDateTime.now();
      access.setEntryDate(entryDate);
      assertTrue(entryDate.equals(access.getEntryDate()));
    }
  }

  //test the exit date of a access
  @Test
  void accessExitDate() {
    Access access = new Access();
    for (int i = 0; i < 100; i++) {
      LocalDateTime exitDate = LocalDateTime.now();
      access.setExitDate(exitDate);
      assertTrue(exitDate.equals(access.getExitDate()));
    }
  }

  //test the identification type of a access
  @Test
  void accessIdentificationType() {
    Access access = new Access();
    for (int i = 0; i < 100; i++) {
      IdentificationType identificationType = IdentificationType.random();
      access.setIdentificationType(identificationType);
      assertTrue(identificationType.equals(access.getIdentificationType()));
    }
  }

}