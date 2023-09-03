package com.trucks.domain.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import com.trucks.domain.entity.Operation;

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
public class ScaleTest {

  // test the instance of a scale
  @Test
  void scaleInstance() {
    Scale scale = new Scale();
    assertNotNull(scale);
    for (int i = 0; i < 100; i++) {
      scale = Scale.random();
      assertNotNull(scale);
    }
  }

  // test the start time of a scale
  @Test
  void scaleStartTime() {
    Scale scale = new Scale();
    for (int i = 0; i < 100; i++) {
      LocalDateTime time = LocalDateTime.now();
      scale.setStartingTime(time);
      assertTrue(time.equals(scale.getStartingTime()));
    }
  }

  // test the end time of a scale
  @Test
  void scaleEndTime() {
    Scale scale = new Scale();
    for (int i = 0; i < 100; i++) {
      LocalDateTime time = LocalDateTime.now();
      scale.setFinishingTime(time);
      assertTrue(time.equals(scale.getFinishingTime()));
    }
  }

  // test the operations of a scale
  @Test
  void scaleOperations() {
    Scale scale = new Scale();
    for (int i = 0; i < 100; i++) {
      List<Operation> operations = new ArrayList<Operation>();
      for (int j = 0; j < i; j++) {
        Operation operation = Operation.random();
        operations.add(operation);
      }
      scale.setOperations(operations);
      assertTrue(operations == scale.getOperations());
    }
  }
}