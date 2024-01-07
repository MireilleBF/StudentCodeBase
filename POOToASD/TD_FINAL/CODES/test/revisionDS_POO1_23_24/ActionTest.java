package revisionDS_POO1_23_24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActionTest {
    Action<Integer,WaterStatus> freeze = new Action<>(WaterElement.FREEZE_ACTION);
    Action<Integer,WaterStatus> boil = new Action<>(WaterElement.BOIL_ACTION);
    Action<Integer,WaterStatus> heat = new Action<>(WaterElement.HEAT_ACTION);

    WaterElement waterElement = new WaterElement("MontainWater");
   // WaterElement waterElement2 = new WaterElement("MoonWater");

    Action<String,FireStatus> actionOnFireElement = new Action<>("extinguish");
    Action<String,FireStatus> actionOnFireElement2 = new Action<>("burn");
    FireElement fireElement = new FireElement("FireInTheHole");
    FireElement fireElement2 = new FireElement("FireOnTheSea");

    @BeforeEach
    void setUp() {
    }

    @Test
    void executeOn() {
        Result<Integer,WaterStatus> result = freeze.executeOn(waterElement);
        assertEquals(-4, result.getValue());
        assertEquals(WaterStatus.FROZEN, result.getStatus());
        assertEquals(2, result.getCode());

        Result<Integer,WaterStatus> result2 = boil.executeOn(waterElement);
        assertEquals(100, result2.getValue());
        assertEquals(WaterStatus.LIQUID, result2.getStatus());
        assertEquals(1, result2.getCode());

        Result<Integer,WaterStatus> result3 = heat.executeOn(waterElement);
        assertEquals(80, result3.getValue());
        assertEquals(WaterStatus.LIQUID, result3.getStatus());
        assertEquals(1, result3.getCode());

        Result<String,FireStatus> result4 = actionOnFireElement.executeOn(fireElement);
        assertEquals("extinguished", result4.getValue());
        assertEquals(FireStatus.SMOKE, result4.getStatus());
        assertEquals(2, result4.getCode());

        Result<String,FireStatus> result5 = actionOnFireElement2.executeOn(fireElement);
        assertEquals("burning", result5.getValue());
        assertEquals(FireStatus.NORMAL, result5.getStatus());
        assertEquals(1, result5.getCode());
    }

    @Test
    void main() {
        Action.main(null);
        assert true;
    }
}