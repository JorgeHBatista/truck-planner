package com.trucks.domain.entity;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

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
public class VesselTest {

    @Autowired
    private MockMvc mvc;

    // test the instance of a vessel
    @Test
    void vesselInstance() {
        Vessel vessel = new Vessel();
        assertNotNull(vessel);
        for (int i = 0; i < 100; i++) {
            vessel = Vessel.random();
            assertNotNull(vessel);
        }
    }

    // test the id of a vessel
    @Test
    void vesselId() {
        Vessel vessel = new Vessel();
        for (int i = 0; i < 100; i++) {
            UUID id = UUID.randomUUID();
            vessel.setId(id);
            assertTrue(id.equals(vessel.getId()));
        }
    }

    // test the scales of a vessel
    @Test
    void vesselScales() {
        Vessel vessel = new Vessel();
        for (int i = 0; i < 100; i++) {
            List<Scale> scales = new ArrayList<Scale>();
            Random rand = new Random();
            int amount = rand.nextInt(11);
            for (int j = 0; j < amount; j++) {
                scales.add(Scale.random());
            }
            vessel.setScales(scales);
            assertTrue(scales.equals(vessel.getScales()));
        }
    }
}