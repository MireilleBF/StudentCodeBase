package v23_24.toFinish;

import java.util.HashSet;
import java.util.Set;


//Part II : Question 5 ------------------------
public class Harmo {

    private Set< Course> courses = new HashSet<>();
    private Set< Teacher> teachers;
    private Set< Student> students;

    public void addCourse( Course c) {
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
        for (final  Student student : students) {
            sum += student.ageIn(ConstantForHarmo.CURRENT_YEAR);
        }
        return ((double)sum) / students.size();
    }
    public Harmo() {
        students = new HashSet<>();
        teachers = new HashSet<>();
    }

    public void addStudent( Student s) {
        students.add(s);
    }
    public void addTeacher( Teacher t) {
        teachers.add(t);
    }



    /* ---- PARTIE III ---- */
    /**
     * Permet de retrouver un étudiant inscrit dans nos listes à partir de son email
     * @param email
     * @return l'étudiant dont l'émail correspond au paramétre, retourne null sinon.
     */
    public  Student findStudent(String email) {
        for ( Student student : students) {
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
    public  Student findStudent(String name, String firstName) {
        for ( Student student : students) {
            if (student.getName().equals(name) && student.getFirstName().equals(firstName)) {
                return student;
            }
        }
        return null;
    }
/* 1.	Comment définiriez-vous la méthode qui recherche
    a)	un étudiant à partir de son nom uniquement ?
    b)	plusieurs étudiants à partir d’un nom uniquement ?
    c)	plusieurs étudiants à partir d’un nom et d’une année de naissance ?

 */

    //You can't use the same method name with the same parameters
    public Student findStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    //You can't use the same method name with the same input parameters
    public Set<Student> findStudents(String name) {
        Set<Student> result = new HashSet<>();
        for (Student student : students) {
            if (student.getName().equals(name)) {
                result.add(student);
            }
        }
        return result;
    }

    public  Student findStudent(String name, int bitrhYear) {
        for ( Student student : students) {
            if (student.getName().equals(name) && student.getBirthYear() == bitrhYear) {
                return student;
            }
        }
        return null;
    }


    //Return the list of courses whose title contains the given string
    public Set<Course> findCourses(String title) {
        Set<Course> result = new HashSet<>();
        for (Course course : courses) {
            if (course.getTitle().contains(title)) {
                result.add(course);
            }
        }
        return result;
    }


    //Return the list of courses whose yje difficulty is greater or equals to the given difficulty
    public Set<Course> findCourses(int difficulty) {
        Set<Course> result = new HashSet<>();
        for (Course course : courses) {
            if (course.getDifficulty() >= difficulty) {
                result.add(course);
            }
        }
        return result;
    }
    public static void main(String[] args) {
         Student s1 = new  Student("Dupont", "Paul", 2000);
         Student s2 = new  Student("Durand", "Benedicte", 2001);
         Student s3 = new  Student("Smith", "John");

         Teacher p1 = new  Teacher("Lovelace", "Ada");
         Teacher p2 = new  Teacher("Turing", "Alan");
        p2.setOffice("B", 405);
         Teacher p3 = new  Teacher("Hopper", "Grace", "A 101");

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
