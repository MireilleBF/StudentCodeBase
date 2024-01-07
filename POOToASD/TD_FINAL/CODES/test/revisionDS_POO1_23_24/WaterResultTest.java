package revisionDS_POO1_23_24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterResultTest {
    WaterResult liquidWater = new WaterResult(11);
    WaterResult frozenWater = new WaterResult(0);
    WaterResult gasWater = new WaterResult(101);

    @BeforeEach
    void setUp() {
        liquidWater = new WaterResult(11);
        frozenWater = new WaterResult(0);
        gasWater = new WaterResult(101);
    }

    @Test
    void getValue() {
        assertEquals(11, liquidWater.getValue());
        assertEquals(0, frozenWater.getValue());
        assertEquals(101, gasWater.getValue());
    }

    @Test
    void getStatus() {
        assertEquals(WaterStatus.LIQUID, liquidWater.getStatus());
        assertEquals(WaterStatus.FROZEN, frozenWater.getStatus());
        assertEquals(WaterStatus.GAS, gasWater.getStatus());
    }

    @Test
    void getCode() {
        assertEquals(1, liquidWater.getCode());
        assertEquals(2, frozenWater.getCode());
        assertEquals(0, gasWater.getCode());
    }

    @Test
    void compareTo() {
        assertEquals(1, liquidWater.compareTo(frozenWater));
        assertEquals(-1, liquidWater.compareTo(gasWater));
        assertEquals(0, liquidWater.compareTo(liquidWater));
        assertEquals(-1, frozenWater.compareTo(liquidWater));
        assertEquals(-1, frozenWater.compareTo(gasWater));
        assertEquals(1, gasWater.compareTo(liquidWater));
        assertEquals(1, gasWater.compareTo(frozenWater));
    }

    @Test
    void testEquals() {
        WaterResult liquidWater2 = new WaterResult(11);
        WaterResult frozenWater2 = new WaterResult(0);
        WaterResult gasWater2 = new WaterResult(101);
        assertEquals(liquidWater, liquidWater2);
        assertEquals(frozenWater, frozenWater2);
        assertEquals(gasWater, gasWater2);
        assertNotEquals(liquidWater, frozenWater);
        assertNotEquals(liquidWater, gasWater);
        assertNotEquals(frozenWater, liquidWater);
        assertNotEquals(frozenWater, gasWater);
        assertNotEquals(gasWater, liquidWater);
        assertNotEquals(gasWater, frozenWater);
    }


    @Test
    void testToString() {
        assertEquals("T: 11 °C : LIQUID", liquidWater.toString());
        assertEquals("T: 0 °C : FROZEN", frozenWater.toString());
        assertEquals("T: 101 °C : GAS", gasWater.toString());
    }
}