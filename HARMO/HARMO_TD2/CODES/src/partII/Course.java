package v23_24.partII;

import java.util.ArrayList;

import java.util.List;
import java.util.Objects;


/*
a course
Un cours est caractérisé́ par son intitulé(une chaîne de caractères), les étudiants inscrits, un niveau de difficulté noté entre 1 (facile) et 5 (difficile) et un enseignant responsable.
 */
public class Course {

    //Part II : Question 1 et 2------------------------
    private String title;
    private List<Student> students;
    private int difficulty;
    private Teacher professor;

    public Course(final String title, final int difficulty) {
        this.title = title;
        this.students = new ArrayList<>();
        this.setDifficultyLocal(difficulty);
    }
    public Course(String title, int difficulty, Teacher professor) {
        this(title, difficulty); // call the constructor above (Course(String title, int difficulty)
        this.professor = professor;
    }

    //Part II : Question 6------------------------
    protected void enroll(final Student student) {
        students.add(student);
    }

    public String getTitle() {
        return title;
    }

    public List<Student> getStudents() {
        return students;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Teacher getProfessor() {
        return professor;
    }

    //Part II : Question 4------------------------

    @Override
    public String toString() {
        return title + " (" + difficulty + ") " + professor;
    }

    //Part II : Question 10------------------------
    public void setProfessor(final Teacher professor) {
        this.professor = professor;
    }



    public void setTitle(final String title) {
        this.title = title;
    }

    //Part II : Question 3------------------------
    //3.	Modifiez votre code pour vérifier que le coefficient de difficulté est compris en 1 et 5.
    public void setDifficulty(int difficulty) {
        this.setDifficultyLocal(difficulty);
    }

    private void setDifficultyLocal(int difficulty) {
        if (difficulty < 1 || difficulty > 5) {
            throw new IllegalArgumentException("Difficulty must be between 1 and 5");
        }
        this.difficulty = difficulty;
    }

    //Part II : Question 7 ------------------------
    //last student enrolled in the course
    public Student getLastStudentEnrolled() {
        if (students.isEmpty()) {
            return null;
        }
        return students.get(students.size() - 1);
    }

    //Part II : Question 8 ------------------------
    //8.	Écrire une méthode qui renvoie la position d’un étudiant de nom donné dans un cours.
    public int positionOf(String name) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }


    public boolean isEnrolled(String  name, String firstName) {
        //Another solution when defined equals method in Student class
        // return students.contains(student);
        for (final Student s : students) {
            if (s.getName().equals(name) && s.getFirstName().equals(firstName)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnrolled(Student student) {
        return students.contains(student);
    }

    //Part II : Question 11 ------------------------
    /*
     * 	Écrire la méthode qui calcule la moyenne d’âge des étudiants inscrits à un cours.
     */
    public double averageAge() {
        double sum = 0;
        if (students.isEmpty()) {
            return sum;
        }
        for (final Student s : students) {
            sum += s.ageIn(ConstantForHarmo.CURRENT_YEAR);
        }
        return sum / students.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return difficulty == course.difficulty && Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, difficulty);
    }

    public static void main(String[] args){
        Course c1 = new Course("Java", 3, new Teacher("Lovelace", "Ada"));
        Course c2 = new Course("C++", 4, new Teacher("Turing", "Alan"));
        Student s1 = new Student("Dupont", "Paul", 2000);
        Student s2 = new Student("Durand", "Benedicte", 2001);
        Student s3 = new Student("Smith", "John");
        c1.enroll(s1);
        c1.enroll(s2);
        c2.enroll(s2);
        c2.enroll(s3);
        System.out.println(c1);
        System.out.println(c2);
        System.out.println("Students in java course : ------- \n" + c1.getStudents());
        System.out.println("Students in C++ course : ------- \n" + c2.getStudents());
    }

}
