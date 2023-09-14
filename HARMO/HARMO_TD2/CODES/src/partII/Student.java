package v23_24.partII;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Student {
    private String name;
    private String firstName;
    private String email;

    private int birthYear;

    /**
     * Courses auxquels un étudiant s'est inscrit
     */
    private Set<Course> courses = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public int getBirthYear() {
        return birthYear;
    }


    public Set<Course> getCourses() {
        return courses;
    }


    public void setName(String name) {
        this.name = name;
        buildEmail();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        buildEmail();
    }

    private void buildEmail() {
        this.email = name + "." + firstName + "@etu.univ-cotedazur";
    }


    /**
     * Définir une méthode publique qui permet de modifier la date de naissance.
     * La modification n'est effectuée que si la date de naissance est valide.
     * @param newBirthYear
     */
    public void setBirthYear(int newBirthYear) {
        if (isValidBirthYear(newBirthYear)) {
            this.birthYear = newBirthYear;
        }
    }


    //Définir un constructeur qui prend en paramètre le nom, le prénom et affecte par défaut la date de naissance à 1901.
    public Student(String herName, String firstName) {
        this(herName, firstName, ConstantForHarmo.DEFAULT_BIRTH_YEAR);
    }

    //Définir un constructeur qui prend en paramètre le nom, le prénom et la date de naissance.
    //En l'absence d'email, l'email par défaut est construit à partir du nom et du prénom et de l'adresse de l'université.
    public Student(String herName, String firstName, int dateOfBirth) {
        this.name = herName;
        this.firstName = firstName;
        buildEmail();
        if (isValidBirthYear(dateOfBirth)) {
            this.birthYear = dateOfBirth;
        }
        else {
            this.birthYear = ConstantForHarmo.DEFAULT_BIRTH_YEAR;
        }
    }
    private boolean isValidBirthYear(int dateOfBirth) {
        return (dateOfBirth >= ConstantForHarmo.DEFAULT_BIRTH_YEAR) && (dateOfBirth <= 2021);
    }


    /**
     * @param year
     * @return l'âge de l'étudiant en fonction de l'année passée en paramètre
     */
    public int ageIn(int year) {
        return year - birthYear;
    }




    // Part II : Question 6 ------------------------
    /*
     * 2.	Ecrire la méthode qui permet à un étudiant de s’inscrire à un cours.
     * un étudiant s1 choisit un cours c1 revient à appeler s1.enrollsIn(c1) qui déclenche un appel à c1.enroll(s1). On peut donc considérer que l’étudiant choisit un cours et que le cours enregistre l’étudiant.
     */
    public void enrollsIn(Course c) {
        courses.add(c);
        c.enroll(this);
    }

    //verifier si un étudiant suit un cours donné
    public boolean isEnrolled(Course c) {
        return courses.contains(c);
    }

    // Part II : Question 13------------------------
    /**
     * 1.	Ecrire la méthode qui calcule la moyenne du niveau de difficulté des cours auxquels un étudiant est inscrit
     *
     */
    public double averageDifficulty() {
        double sum = 0;
        if (courses.isEmpty()) {
            return sum;
        }
        for (Course c : courses) {
            sum += c.getDifficulty();
        }
        return sum / courses.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Student student = (Student) o;
        return birthYear == student.birthYear && Objects.equals(name, student.name) && Objects.equals(firstName, student.firstName) && Objects.equals(email, student.email) && Objects.equals(courses, student.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, firstName, email, birthYear, courses);
    }

    @Override
    public String toString() {
        return "Student : \n\t" + name + "\t " + firstName + ", \n\t" + email + ", \n\t" + birthYear + "\n";
    }
    public static void main(String[] args) {
          Student s = new   Student("Doe", "Jane", 2000);
        System.out.println(s);
        System.out.println(s.getName());
        s.setName("Haddock");
        System.out.println(s.getName());

        System.out.println(s.getFirstName());
        int age = s.ageIn(2021);
        System.out.println(age);
        System.out.println(s.ageIn(2021));


        System.out.println(s.getEmail());
          Student s2 = new   Student("Doe", "John", 2001);
        System.out.println(s2.getName());
        System.out.println(s2.getFirstName());
        System.out.println(s2.ageIn(2021));


        System.out.println(s.getEmail());
          Student s3 = new   Student("Doe", "John", 2024);
        System.out.println(s3.ageIn(2021));

          Student s4 = new   Student("Doe", "John");
        System.out.println(s4.ageIn(2021));

    }




}
