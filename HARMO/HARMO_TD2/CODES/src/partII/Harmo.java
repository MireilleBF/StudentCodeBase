package v23_24.partII;

import java.util.HashSet;
import java.util.Set;


//Part II : Question 5 ------------------------
public class Harmo {

    private Set<Course> courses = new HashSet<>();
    private Set<Teacher> teachers;
    private Set<Student> students;

    public void addCourse(Course c) {
        courses.add(c);
    }
    public Set<Course> getCourses() {
        return courses;
    }





    //Part II : Question 15 ------------------------
    // 3.	Écrire la méthode qui calcule la moyenne d’âge des étudiants de l’école
    public double averageAge() {
        int sum = 0;
        if (students.isEmpty()) {
            return sum;
        }
        for (final Student student : students) {
            sum += student.ageIn(ConstantForHarmo.CURRENT_YEAR);
        }
        return ((double)sum) / students.size();
    }
    public Harmo() {
        students = new HashSet<>();
        teachers = new HashSet<>();
    }

    public void addStudent(Student s) {
        students.add(s);
    }
    public void addTeacher(Teacher t) {
        teachers.add(t);
    }
    /**
     * Permet de retrouver un étudiant inscrit dans nos listes à partir de son email
     * @param email
     * @return l'étudiant dont l'émail correspond au paramétre, retourne null sinon.
     */
    public Student findStudent(String email) {
        for (Student student : students) {
            if (student.getEmail().equals(email)) {
                return student;
            }
        }
        return null;
    }


    /**
     * Permet de retrouver un étudiant inscrit dans nos listes à partir de son nom et son prenom
     * @param name
     * @param firstName
     * @return
     */
    public Student findStudent(String name, String firstName) {
        for (Student student : students) {
            if (student.getName().equals(name) && student.getFirstName().equals(firstName)) {
                return student;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        Student s1 = new Student("Dupont", "Paul", 2000);
        Student s2 = new Student("Durand", "Benedicte", 2001);
        Student s3 = new Student("Smith", "John");

        Teacher p1 = new Teacher("Lovelace", "Ada");
        Teacher p2 = new Teacher("Turing", "Alan");
        p2.setOffice("B", 405);
        Teacher p3 = new Teacher("Hopper", "Grace", "A 101");

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
    }

    public Set<Student> getStudents() {
        return new HashSet<>(students);
    }

    public Set<Teacher> getTeachers() {
        return new HashSet<>(teachers);
    }
}
