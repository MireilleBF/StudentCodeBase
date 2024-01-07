package revisionDS_POO1_23_24;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WaterStatusTest {

    @Test
    void testGetCode() {
        WaterStatus waterStatus = WaterStatus.LIQUID;
        assertEquals(1, waterStatus.getCode());
        assertEquals(2, WaterStatus.FROZEN.getCode());
        assertEquals(0, WaterStatus.GAS.getCode());
        assertEquals(1, WaterStatus.valueOf("LIQUID").getCode());
    }

    @Test
    void getDescription() {
        WaterStatus waterStatus = WaterStatus.LIQUID;
        assertEquals("Liquid : 0°C < T < 100°C", waterStatus.getDescription());
        assertEquals("Frozen : T <= 0°C", WaterStatus.FROZEN.getDescription());
        assertEquals("Gas : T > 100°C", WaterStatus.GAS.getDescription());
        assertEquals("Liquid : 0°C < T < 100°C", WaterStatus.valueOf("LIQUID").getDescription());
    }

    @Test
    void values() {
        assertEquals(3, WaterStatus.values().length);
        assertEquals(WaterStatus.LIQUID, WaterStatus.values()[0]);
        assertEquals(WaterStatus.FROZEN, WaterStatus.values()[1]);
        assertEquals(WaterStatus.GAS, WaterStatus.values()[2]);

    }

    @Test
    void valueOf() {
        assertEquals(WaterStatus.LIQUID, WaterStatus.valueOf("LIQUID"));
        assertEquals(WaterStatus.FROZEN, WaterStatus.valueOf("FROZEN"));
        assertEquals(WaterStatus.GAS, WaterStatus.valueOf("GAS"));
    }
}