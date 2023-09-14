package v23_24.partII;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {
    private Teacher teaacherAda;
    private Teacher teacherAlan;
    private Teacher teacherGrace;

    private Course coursJava;
    private Course coursC;

    private Course coursUI;

    private Student studentDupont;
    private Student studentDurant;
    private Student studentSmith;


    @BeforeEach
    void setUp() {
        initTeachers();

        initCourses();


        initStudents();
    }

    private void initStudents() {
        studentDupont = new Student("Dupont", "Paul", 2000);
        studentDurant= new Student("Durant", "Benedicte", 2001);
        studentSmith = new Student("Smith", "John");
    }

    private void initCourses() {
        coursJava = new Course("Java", 3);
        coursC = new Course("C++", 4);
        coursUI = new Course("UI", 2, teacherGrace);
    }

    private void initTeachers() {
        teaacherAda = new Teacher("Lovelace", "Ada");
        teacherAlan = new Teacher("Turing", "Alan");
        teacherGrace = new Teacher("Hopper", "Grace", "A 101");
    }

    private void init(){
        //Attention c'est l'étudiant qui s'inscrit au cours
        studentDupont.enrollsIn(coursJava);
        studentDurant.enrollsIn(coursJava);
        studentDurant.enrollsIn(coursC);
    }

    @Test
    void testInit(){
        init();
        assertTrue(coursJava.isEnrolled(studentDupont));
        assertTrue(coursJava.isEnrolled(studentDurant));
        assertTrue(coursC.isEnrolled(studentDurant));
        assertFalse(coursC.isEnrolled(studentSmith));

        assertTrue(studentDupont.isEnrolled(coursJava));
        assertTrue(studentDurant.isEnrolled(coursJava));
        assertTrue(studentDurant.isEnrolled(coursC));


    }

    //Should not be called outside
    @Test
    void testEnroll() {
        coursJava.enroll(studentDupont);
        assertTrue(coursJava.isEnrolled(studentDupont));
        coursJava.enroll(studentDurant);
        assertTrue(coursJava.isEnrolled(studentDurant));


    }

    @Test
    void testGetTitle() {
        assertEquals("Java", coursJava.getTitle());
    }

    @Test
    void testGetStudents() {
init();
        assertEquals(2, coursJava.getStudents().size());
        assertTrue(coursJava.getStudents().contains(studentDupont));
        assertTrue(coursJava.getStudents().contains(studentDurant));
        assertFalse(coursJava.getStudents().contains(studentSmith));
    }

    @Test
    void testGetDifficulty() {
        assertEquals(3, coursJava.getDifficulty());
        assertEquals(4, coursC.getDifficulty());
    }

    @Test
    void testGetSetProfessor() {
        assertNull(coursJava.getProfessor());
        assertNull(coursC.getProfessor());
        coursJava.setProfessor(teaacherAda);
        coursC.setProfessor(teacherAlan);
        assertEquals(teaacherAda, coursJava.getProfessor());
        assertEquals(teacherAlan, coursC.getProfessor());
        init();
        assertEquals(teacherGrace, coursUI.getProfessor());
    }

    @Test
    void testIsEnrolledByName() {
        init();
        assertTrue(coursJava.isEnrolled("Dupont", "Paul"));
        assertTrue(coursJava.isEnrolled("Durant", "Benedicte"));
        assertFalse(coursJava.isEnrolled("Smith", "John"));
        assertFalse(coursJava.isEnrolled("Smith", "Paul"));
        assertFalse(coursJava.isEnrolled("Dupont", "John"));
    }



    @Test
    void testSetTitle() {
        coursJava.setTitle("Java 2");
        assertEquals("Java 2", coursJava.getTitle());
    }

    @Test
    void testSetDifficulty2() {
        coursJava.setDifficulty(5);
        assertEquals(5, coursJava.getDifficulty());
        coursJava.setDifficulty(2);
        assertEquals(2, coursJava.getDifficulty());
        assertThrows(IllegalArgumentException.class, () -> coursJava.setDifficulty(6));

    }

    @Test
    void testGetLastStudentEnrolled() {
        assertNull(coursJava.getLastStudentEnrolled());
        coursJava.enroll(studentDupont);
        assertEquals(studentDupont, coursJava.getLastStudentEnrolled());
        coursJava.enroll(studentDurant);
        assertEquals(studentDurant, coursJava.getLastStudentEnrolled());

    }

    @Test
    void testPositionOf() {
        assertEquals(-1, coursJava.positionOf("Dupont"));
        init();
        assertEquals(0, coursJava.positionOf("Dupont"));
        assertEquals(1, coursJava.positionOf("Durant"));
        assertEquals(-1, coursJava.positionOf("Smith"));
    }


    @Test
    void testIsEnrolled() {
        init();
        assertTrue(coursJava.isEnrolled(studentDupont));
        assertTrue(coursJava.isEnrolled(studentDurant));
        assertFalse(coursJava.isEnrolled(studentSmith));
    }


    @Test
    void testAverageAge() {
        assertEquals(0, coursJava.averageAge());
        coursJava.enroll(studentDupont);
        assertEquals(23,studentDupont.ageIn(ConstantForHarmo.CURRENT_YEAR));
        assertEquals(23, coursJava.averageAge());

        coursJava.enroll(studentDurant);
        assertEquals(22,studentDurant.ageIn(ConstantForHarmo.CURRENT_YEAR));
        assertEquals(22.5, coursJava.averageAge());

        studentSmith.enrollsIn(coursJava);
        studentSmith.setBirthYear(2004);
        assertEquals(19,studentSmith.ageIn(ConstantForHarmo.CURRENT_YEAR));
        assertEquals(21.33, coursJava.averageAge(),0.01);
    }


        Teacher t1 = new     Teacher("Lovelace", "Ada");
        Teacher t2 = new     Teacher("Turing", "Alan", "A 101");
        Teacher t3 = new     Teacher("Hopper", "Grace", "A1");


    @Test
    void testSetFirstNameTest() {
        t1.setFirstName("Augusta");
        assertEquals("Augusta", t1.getFirstName());
        assertEquals("Lovelace.Augusta@univ-cotedazur", t1.getEmail());
    }

    @Test
    void testSetOfficeTest() {
        t2.setOffice("B", 405);
        assertEquals("B 405", t2.getOffice());
        t2.setOffice("G", 101);
        assertEquals("B 405", t2.getOffice());
        t2.setOffice("A", 1010);
        assertEquals("B 405", t2.getOffice());
    }
    @Test
    void testIsOfficeTest() {
        assertTrue(t2.isOfficeInformationValid("A 101"));
        assertFalse(t3.isOfficeInformationValid("A1"));
        assertTrue(t2.isOfficeInformationValid("F 101"));
        assertFalse(t2.isOfficeInformationValid("H 101"));
        assertTrue(t2.isOfficeInformationValid("A 1010"));
    }

    @org.junit.jupiter.api.Test
    void testMain() {
        Course.main(new String[0]);
        //Only check that the main method does not throw an exception
    }
}