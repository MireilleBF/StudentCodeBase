package v23_24.partII;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HarmoTest {


    Harmo harmo = new Harmo();

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
        harmo = new Harmo();
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

    @Test
    void testAddCourse() {
        assertTrue(harmo.getCourses().isEmpty());
        harmo.addCourse(coursJava);
        assertEquals(1, harmo.getCourses().size());
        assertTrue( harmo.getCourses().contains(coursJava));

        harmo.addCourse(coursC);
        assertEquals(2, harmo.getCourses().size());
        assertTrue( harmo.getCourses().contains(coursC));
    }

    @Test
    void testAddTeacher() {
        assertTrue(harmo.getTeachers().isEmpty());
        harmo.addTeacher(teaacherAda);
        assertEquals(1, harmo.getTeachers().size());
        assertTrue( harmo.getTeachers().contains(teaacherAda));

        harmo.addTeacher(teacherAlan);
        assertEquals(2, harmo.getTeachers().size());
        assertTrue( harmo.getTeachers().contains(teacherAlan));
    }


    @Test
    void testAverageAge() {
        assertEquals(0, harmo.averageAge());
        harmo.addStudent(studentDupont);
        assertEquals(23 , studentDupont.ageIn(ConstantForHarmo.CURRENT_YEAR));
        assertEquals(23, harmo.averageAge());
        harmo.addStudent(studentDurant);
        assertEquals(22, studentDurant.ageIn(ConstantForHarmo.CURRENT_YEAR));
        assertEquals(22.5, harmo.averageAge());
        harmo.addStudent(studentSmith);
        studentSmith.setBirthYear(2005);
        assertEquals(18, studentSmith.ageIn(ConstantForHarmo.CURRENT_YEAR));
        assertEquals(21, harmo.averageAge());
    }

    @Test
    void testAddStudent() {
        assertTrue(harmo.getStudents().isEmpty());
        harmo.addStudent(studentDupont);
        assertEquals(1, harmo.getStudents().size());
        assertTrue(harmo.getStudents().contains(studentDupont));

        harmo.addStudent(studentDurant);
        assertEquals(2, harmo.getStudents().size());
        assertTrue(harmo.getStudents().contains(studentDurant));
    }

    @Test
    void testFindStudentByName() {
        harmo.addStudent(studentDupont);
        harmo.addStudent(studentDurant);
        harmo.addStudent(studentSmith);
        assertEquals(studentDupont, harmo.findStudent("Dupont", "Paul"));
        assertEquals(studentDurant, harmo.findStudent("Durant", "Benedicte"));
        assertEquals(studentSmith, harmo.findStudent("Smith", "John"));
        assertEquals(null, harmo.findStudent("Smith", "Paul"));

    }

    @Test
    void testFindStudent() {
        harmo.addStudent(studentDupont);
        harmo.addStudent(studentDurant);
        assertEquals(studentDupont, harmo.findStudent(studentDupont.getEmail()));
        assertEquals(studentDurant, harmo.findStudent(studentDurant.getEmail()));

    }

    @Test
    void testMain() {
        Harmo.main(new String[0]);
        //Only check that the main method does not throw an exception

    }
}