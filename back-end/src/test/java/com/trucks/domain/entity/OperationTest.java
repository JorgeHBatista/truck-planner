package com.trucks.domain.entity;

import com.trucks.domain.valueobject.Cargo;
import com.trucks.domain.valueobject.OperationType;
import com.trucks.domain.valueobject.Quantity;

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
public class OperationTest {

    @Autowired
    private MockMvc mvc;

    // test the instance of a operation
    @Test
    void operationInstance() {
        Operation operation = new Operation();
        assertNotNull(operation);
    }

    // test the cargo of a operation
    @Test
    void operationCargo() {
        Operation operation = new Operation();
        for (int i = 0; i < 100; i++) {
            Cargo cargo = Cargo.random();
            operation.setCargo(cargo);
            assertTrue(cargo.equals(operation.getCargo()));
        }
    }

    // test the operation type of a operation
    @Test
    void operationOperationType() {
        Operation operation = new Operation();
        for (int i = 0; i < 100; i++) {
            OperationType operationType = OperationType.random();
            operation.setType(operationType);
            assertTrue(operationType.equals(operation.getType()));
        }
    }

    // test the quantity of a operation
    @Test
    void operationQuantity() {
        Operation operation = new Operation();
        for (int i = 0; i < 100; i++) {
            Quantity quantity = Quantity.random();
            operation.setQuantity(quantity);
            assertTrue(quantity.equals(operation.getQuantity()));
        }
    }
}