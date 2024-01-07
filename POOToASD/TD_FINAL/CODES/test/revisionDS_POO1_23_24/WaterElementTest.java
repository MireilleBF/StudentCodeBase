package revisionDS_POO1_23_24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class WaterElementTest {

    WaterElement waterElement;
    @BeforeEach
    void setUp() {
        waterElement = new WaterElement("Water");
    }

    @ParameterizedTest
    @CsvSource({"heat, 80, LIQUID, 1", "freeze, -4, FROZEN, 2", "boil, 100, LIQUID, 1", "condense, 40, LIQUID, 1"})
    void testPerformAction(String action, int temperature, WaterStatus status, int code) {
        Result<Integer, WaterStatus> result = waterElement.performAction(action);
        assertEquals(temperature, result.getValue());
        assertEquals(status, result.getStatus());
        assertEquals(code, result.getCode());
    }

    /*
    @Test
    void testPerformActionHeat() {
        Result<Integer, WaterStatus> result = waterElement.performAction("heat");
        assertEquals(80, result.getValue());
        assertEquals(WaterStatus.LIQUID, result.getStatus());
        assertEquals(1, result.getCode());
    }
    @Test
    void testPerformActionFreeze() {
        Result<Integer, WaterStatus> result = waterElement.performAction("freeze");
        assertEquals(-4, result.getValue());
        assertEquals(WaterStatus.FROZEN, result.getStatus());
        assertEquals(2, result.getCode());
    }
    @Test
    void testPerformActionBoil() {
        Result<Integer, WaterStatus> result = waterElement.performAction("boil");
        assertEquals(100, result.getValue());
        assertEquals(WaterStatus.LIQUID, result.getStatus());
        assertEquals(1, result.getCode());
    }

    @Test
    void testPerformActionCondense() {
        Result<Integer, WaterStatus> result = waterElement.performAction("condense");
        assertEquals(40, result.getValue());
        assertEquals(WaterStatus.LIQUID, result.getStatus());
        assertEquals(1, result.getCode());
    }

     */
}