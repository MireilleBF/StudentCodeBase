package v23_24.partII;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

     Student s1 = new  Student("Doe", "John", 1999);
     Student s2 = new Student("Doe", "Jane", 2000);

     Course c1 = new  Course("Java", 3, new  Teacher("Lovelace", "Ada"));
     Course c2 = new  Course("C++", 4, new  Teacher("Turing", "Alan"));
     Course c3 = new  Course("Python", 2, new  Teacher("Hopper", "Grace", "A 101"));
     Course c4 = new Course("C#", 5, new Teacher("Hopper", "Grace",  "A 101"));
    @org.junit.jupiter.api.Test
    void testSetName() {
        s1.setName("Haddock");
        assertEquals("Haddock", s1.getName());
        assertEquals("Haddock.John@etu.univ-cotedazur", s1.getEmail());

    }

    @Test
    void testInit() {
        assertNotNull(s1);
        assertNotNull(s2);
        Student s3 = new Student("Doe", "John");
        assertEquals(ConstantForHarmo.DEFAULT_BIRTH_YEAR, s3.getBirthYear());
        s3 = new Student("Doe", "John",1800);
        assertEquals(ConstantForHarmo.DEFAULT_BIRTH_YEAR, s3.getBirthYear());
        s3 = new Student("Doe", "John", 2023);
        assertEquals(ConstantForHarmo.DEFAULT_BIRTH_YEAR, s3.getBirthYear());

    }
    @org.junit.jupiter.api.Test
    void testSetFirstName() {
        s1.setFirstName("Captain");
        assertEquals("Captain", s1.getFirstName());
        assertEquals("Doe.Captain@etu.univ-cotedazur", s1.getEmail());
    }

    @org.junit.jupiter.api.Test
    void testGetName() {
        assertEquals("Doe", s1.getName());
    }

    @org.junit.jupiter.api.Test
    void testGetFirstName() {
        assertEquals("John", s1.getFirstName());
    }

    @org.junit.jupiter.api.Test
    void testAgeIn() {
        assertEquals(24, s1.ageIn(2023));
        assertEquals(23, s2.ageIn(2023));
    }

    @org.junit.jupiter.api.Test
    void testGetEmail() {
        assertEquals("Doe.John@etu.univ-cotedazur", s1.getEmail());
    }

    @org.junit.jupiter.api.Test
    void testSetBirthYear() {
        s1.setBirthYear(2000);
        assertEquals(2000, s1.getBirthYear());
    }

    @org.junit.jupiter.api.Test
    void testSetBirthYearFail() {
        assertEquals(1999, s1.getBirthYear());
        s1.setBirthYear(2023);
        assertEquals(1999, s1.getBirthYear());
    }

    @org.junit.jupiter.api.Test
    void testEnrollsIn() {
        assertFalse(c1.isEnrolled(s1));
        assertFalse(c2.isEnrolled(s1));
        assertTrue(s1.getCourses().isEmpty());
        s1.enrollsIn(c1);
        assertTrue(c1.isEnrolled(s1));
        assertTrue(s1.getCourses().contains(c1));
        s1.enrollsIn(c2);
        assertTrue(c2.isEnrolled(s1));
        assertTrue(s1.getCourses().contains(c2));
        assertTrue(s1.getCourses().contains(c1));
        s1.enrollsIn(c3);
        assertTrue(c3.isEnrolled(s1));
        assertTrue(s1.getCourses().contains(c3));
        assertTrue(s1.getCourses().contains(c2));
        assertTrue(s1.getCourses().contains(c1));

    }

    @org.junit.jupiter.api.Test
    void testIsEnrollsIn() {
        s1.enrollsIn(c1);
        assertTrue(s1.isEnrolled(c1));
        s1.enrollsIn(c2);
        assertTrue(s1.isEnrolled(c2));
        assertFalse(s1.isEnrolled(c3));

    }

    @org.junit.jupiter.api.Test
    void testAverageDifficulty() {
        assertTrue(s1.getCourses().isEmpty());
        assertEquals(0.0, s1.averageDifficulty());
        s1.enrollsIn(c4);
        assertEquals(5.0, c4.getDifficulty());
        assertEquals(5, s1.averageDifficulty());
        s1.enrollsIn(c3);
        assertEquals(2, c3.getDifficulty());
        assertEquals(3.5, s1.averageDifficulty());
        s1.enrollsIn(c2);
        assertEquals(4, c2.getDifficulty());
        assertEquals(3.66, s1.averageDifficulty(), 0.01);
    }

    @org.junit.jupiter.api.Test
    void testMain() {
        Student.main(new String[0]);
        //Only check that the main method does not throw an exception
    }

}