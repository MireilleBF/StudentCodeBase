package revisionDS_POO1_23_24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FireElementTest {

    FireElement fireElement = new FireElement("Fire");
    @BeforeEach
    void setUp() {
        fireElement = new FireElement("Fire");
    }

    @Test
    void testPerformActionBurn() {
        Result<String, FireStatus> result = fireElement.performAction("burn");
        assertEquals("burning", result.getValue());
        assertEquals(FireStatus.NORMAL, result.getStatus());
        assertEquals(1, result.getCode());
    }

    @Test
    void testPerformActionExtinguish() {
        Result<String, FireStatus> result = fireElement.performAction("extinguish");
        assertEquals("extinguished", result.getValue());
        assertEquals(FireStatus.SMOKE, result.getStatus());
        assertEquals(2, result.getCode());
    }
}