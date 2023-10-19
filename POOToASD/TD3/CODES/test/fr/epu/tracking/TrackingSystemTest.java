package fr.epu.tracking;

import fr.epu.vehicles.Drone;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrackingSystemTest {

    TrackingSystem trackingSystem;
    Drone drone1 ;
    Drone drone2 ;
    Drone drone3 ;
    @BeforeEach
    void setUp() {
        trackingSystem = new TrackingSystem();
        drone1 = new Drone(100, 10);
        drone2 = new Drone(40, 2);
        drone3 = new Drone(50, 5);
    }

    void init() {
        trackingSystem.addTrackableObject(drone1);
        trackingSystem.addTrackableObject(drone2);
        trackingSystem.addTrackableObject(drone3);
    }
    @Test
    void testAddTrackableObject() {
        assertEquals(0,trackingSystem.getNumberOfTrackedObjects());
        trackingSystem.addTrackableObject(drone1);
        assertEquals(1,trackingSystem.getNumberOfTrackedObjects());
        trackingSystem.addTrackableObject(drone2);
        assertEquals(2,trackingSystem.getNumberOfTrackedObjects());
    }

    //We can't test this method because getPosition() uses Math.random()
    @Test
    void testGetTrackableObjectPosition() {
        init();
        assertTrue(trackingSystem.getTrackableObjectPosition(0).isPresent());
        System.out.println(trackingSystem.getTrackableObjectPosition(0).get());
        assertTrue(trackingSystem.getTrackableObjectPosition(1).isPresent());
        System.out.println(trackingSystem.getTrackableObjectPosition(1).get());
        assertTrue(trackingSystem.getTrackableObjectPosition(2).isPresent());
        System.out.println(trackingSystem.getTrackableObjectPosition(2).get());
        assertFalse(trackingSystem.getTrackableObjectPosition(3).isPresent());
    }

    @Test
    void testGetAllTrackablePositions() {
        init();
        assertEquals(3,trackingSystem.getAllTrackablePositions().size());
        System.out.println(trackingSystem.getAllTrackablePositions());
    }
}