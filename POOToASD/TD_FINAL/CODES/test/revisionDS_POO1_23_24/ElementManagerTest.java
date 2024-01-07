package revisionDS_POO1_23_24;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ElementManagerTest {

    ElementManager elementManager = new ElementManager("myManager");

    @BeforeEach
    void setUp() {
        elementManager = new ElementManager("test");
    }

    @Test
    void testGetName() {
        assertEquals("test", elementManager.getName());
    }

    @Test
    void testPerformActionOnWaterElement() {
        Element element = new WaterElement("Water");
        Action<Integer, WaterStatus> action = new Action<>(WaterElement.FREEZE_ACTION);
        try {
            elementManager.performAction(element, action);
            List<Result> res = elementManager.getTrace();
            assertEquals(1, res.size());
            Result<Integer, WaterStatus> result = res.get(0);
            assertEquals(-4, result.getValue());
            assertEquals(WaterStatus.FROZEN, result.getStatus());
        } catch (ExperimentTraceException e) {
            fail("Unexpected exception");
        }
        assertEquals(1, elementManager.getTrace().size());
    }

    @Test
    void testPerformActionOnFireElement() {
        Element element = new FireElement("Fire");
        Action<String, FireStatus> action = new Action<>("extinguish");
        try {
            elementManager.performAction(element, action);
            List<Result> res = elementManager.getTrace();
            assertEquals(1, res.size());
            Result<String, FireStatus> result = res.get(0);
            assertEquals("extinguished", result.getValue());
            assertEquals(FireStatus.SMOKE, result.getStatus());
        } catch (ExperimentTraceException e) {
            fail("Unexpected exception");
        }
        assertEquals(1, elementManager.getTrace().size());

    }

    @Test
    void testPerformActionListOnWaterElement() {
        Element element = new WaterElement("Water");
        Action<Integer, WaterStatus> freeze = new Action<>(WaterElement.FREEZE_ACTION);
        Action<Integer, WaterStatus> boil = new Action<>(WaterElement.BOIL_ACTION);
        Action<Integer, WaterStatus> heat = new Action<>(WaterElement.HEAT_ACTION);
        try {
            elementManager.addElement(element);
            elementManager.performActionListOn(element.getName(), List.of(freeze, boil, heat));
            List<Result> res = elementManager.getTrace();
            assertEquals(3, res.size());

            Result<Integer, WaterStatus> result = res.get(2);
            assertEquals(-4, result.getValue());
            assertEquals(WaterStatus.FROZEN, result.getStatus());
            result = res.get(1);
            assertEquals(100, result.getValue());
            assertEquals(WaterStatus.LIQUID, result.getStatus());
            result = res.get(0);
            assertEquals(80, result.getValue());
            assertEquals(WaterStatus.LIQUID, result.getStatus());
        } catch (ExperimentTraceException  e) {
            fail("Unexpected exception");
        }
        assertEquals(3, elementManager.getTrace().size());
    }

    @Test
    void testGetAndAddElement() {
        Element element = new WaterElement("Water");
        elementManager.addElement(element);
        Optional<Element> opt = elementManager.getElement("Water");
        assertTrue(opt.isPresent());
        assertEquals(element, opt.get());
    }


    @Test
    void testGetAndAddElements() {
        Element water = new WaterElement("Water");
        Element fire = new FireElement("Fire");

        elementManager.addElement(water);
        elementManager.addElement(fire);
        Optional<Element> opt = elementManager.getElement("Water");
        assertTrue(opt.isPresent());
        assertEquals(water, opt.get());
        opt = elementManager.getElement("Fire");
        assertTrue(opt.isPresent());
        assertEquals(fire, opt.get());
    }


    @Test
    void testMain() {
        System.out.println("running main : no exception expected");
        ElementManager.main(new String[]{"test"});
    }


    void initElementManager() {
        Element water = new WaterElement("Water");
        Element fire = new FireElement("Fire");
        elementManager.addElement(water);
        elementManager.addElement(fire);

        Action<Integer, WaterStatus> freeze = new Action<>(WaterElement.FREEZE_ACTION);
        Action<Integer, WaterStatus> boil = new Action<>(WaterElement.BOIL_ACTION);
        Action<Integer, WaterStatus> heat = new Action<>(WaterElement.HEAT_ACTION);
        Action<String, FireStatus> extinguish = new Action<>("extinguish");
        Action<String, FireStatus> burn = new Action<>("burn");

        try {
            elementManager.performActionListOn("Water", List.of(freeze, boil, heat,freeze, boil, heat));
            elementManager.performActionListOn("Fire", List.of(extinguish, burn, burn, extinguish, burn, burn));

        } catch (ExperimentTraceException  e) {
            fail("Unexpected exception");
        }
    }

    @Test
    void testRemoveDuplicatedInTrace(){
        initElementManager();
        assertEquals(12, elementManager.getTrace().size());
        elementManager.removeDuplicatedResults();
        //equals is not redefined on FireResult
        assertEquals(9, elementManager.getTrace().size());
    }
}