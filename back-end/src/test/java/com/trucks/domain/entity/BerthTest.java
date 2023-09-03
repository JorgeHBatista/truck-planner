package com.trucks.domain.entity;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import es.ull.utils.string.UllString;

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
public class BerthTest {

    @Autowired
    private MockMvc mvc;

    // test the instance of a berth
    @Test
    void berthInstance() {
        Berth berth = new Berth();
        assertNotNull(berth);
    }
}