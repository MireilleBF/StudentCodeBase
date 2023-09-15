package v23_24.toFinish;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeacherTest {

    Teacher t1 = new Teacher("Lovelace", "Ada");
    Teacher t2 = new Teacher("Turing", "Alan", "A 101");
    Teacher t3 = new Teacher("Hopper", "Grace", "A1");


    @Test
    void testInit() {
        assertEquals("Lovelace", t1.getName());
        assertEquals("Ada", t1.getFirstName());
        assertEquals("Lovelace.Ada@univ-cotedazur", t1.getEmail());
        assertEquals("Turing", t2.getName());
        assertEquals("Alan", t2.getFirstName());
    }

    @Test
    void testSetFirstName() {
        t1.setFirstName("Augusta");
        assertEquals("Augusta", t1.getFirstName());
        assertEquals("Lovelace.Augusta@univ-cotedazur", t1.getEmail());
    }

    @Test
    void testSetName() {
        t1.setName("Sanderson");
        assertEquals("Sanderson", t1.getName());
        assertEquals("Sanderson.Ada@univ-cotedazur", t1.getEmail());
    }

    @Test
    void testSetOffice() {
        t2.setOffice("B", 405);
        assertEquals("B 405", t2.getOffice());
        t2.setOffice("G", 101);
        assertEquals("B 405", t2.getOffice());
        t2.setOffice("A", 1010);
        assertEquals("B 405", t2.getOffice());
    }

    @Test
    void testIsOffice() {
        assertTrue(t2.isOfficeInformationValid("A 101"));
        assertFalse(t3.isOfficeInformationValid("A1"));
        assertFalse(t3.isOfficeInformationValid("A101"));
        assertTrue(t2.isOfficeInformationValid("F 101"));
        assertFalse(t2.isOfficeInformationValid("H 101"));
        assertTrue(t2.isOfficeInformationValid("A 1010"));
    }

    @org.junit.jupiter.api.Test
    void testMain() {
        Teacher.main(new String[0]);
        //Only check that the main method does not throw an exception
    }

    @Test
    void testEquals() {
        assertEquals(t1, t1);
        assertEquals(t1, new Teacher("Lovelace", "Ada"));
        assertNotEquals(t1, t2);
        assertNotEquals(null, t1);
        assertNotEquals(t1, new Object());
    }

    @Test
    void testTeaches() {
        Course c1 = new Course("Java", 3);
        Course c2 = new Course("C++", 3);
        Course c3 = new Course("C", 3, t3);
        t1.teaches(c1);
        assertEquals(1, t1.getCourses().size());
        assertEquals(c1, t1.getCourses().get(0));
        assertEquals(t1, c1.getProfessor());
        assertTrue(t1.doYouTeach(c1));
        assertFalse(t1.doYouTeach(c2));
        t1.teaches(c2);
        t1.teaches(c3);
        assertTrue(t1.doYouTeach(c1));
        assertTrue(t1.doYouTeach(c2));
        assertTrue(t1.doYouTeach(c3));
        assertEquals(3, t1.getCourses().size());
        assertEquals(c1, t1.getCourses().get(0));
        assertEquals(c2, t1.getCourses().get(1));
        assertEquals(c3, t1.getCourses().get(2));
        assertEquals(t1, c1.getProfessor());
        assertEquals(t1, c2.getProfessor());
        assertEquals(t1, c3.getProfessor());

    }

    @Test
    void testAverageDifficulty() {
        Course c1 = new Course("Java", 3);
        Course c2 = new Course("C++", 5);
        Course c3 = new Course("C", 1, t3);
        t1.teaches(c1);
        assertEquals(3, t1.averageDifficulty());
        t1.teaches(c2);
        assertEquals(4, t1.averageDifficulty());
        t1.teaches(c3);
        assertEquals(3, t1.averageDifficulty());
    }
}