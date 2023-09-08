package v23_24.QVII_IX;

import java.util.HashSet;
import java.util.Set;

public class Student {


    private String name;
    private String firstName;
    private int birthYear;

    // Part I : Question 33 ------------------------
    private String email;



// Part I : Question 46 ------------------------
    private static final String DEFAULT_EMAIL = "@etu.univ-cotedazur.fr";

    // Part I : Question 45 ------------------------
    private static final int DEFAULT_BIRTH_YEAR = 1901;


    /* ----------------------  */
    /* CONSTRUCTORS            */
    /* ----------------------  */
    public Student(String aName, String firstName) {
        this(aName, firstName, 1901);
    }
    // Part I : Question 34 ------------------------
    public Student(String aName, String firstName, int dateOfBirth) {
        name = aName;
        this.firstName = firstName;
        if (isValidBirthYear(dateOfBirth)) {
            this.birthYear = dateOfBirth;
        } else {
            this.birthYear = DEFAULT_BIRTH_YEAR;
        }
        email = name + "." + firstName + "@etu.univ-cotedazur.fr";
    }

    private boolean isValidBirthYear(int dateOfBirth) {
        return (dateOfBirth >= 1900) && (dateOfBirth <= 2021);
    }


    /* ----------------------  */
    /* ACCESSORS AND MUTATORS  */
    /* ----------------------  */
    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setBirthYear(int birthYear) {
        if (isValidBirthYear(birthYear))
            this.birthYear = birthYear;
    }

    public int ageIn(int year) {
        return year - birthYear;
    }



    // Part I : Question 39 ------------------------
    public String toString() {
        return "Student : \n\t" + name + "\t " + firstName + ", \n\t" + email + ", \n\t" + birthYear + "\n";
    }

    // Part I : Question 40 ------------------------
    public static void main(String[] args) {
        Student s = new Student("Doe", "Jane", 2000);
        System.out.println(s);
        System.out.println(s.getName());
        s.setName("Haddock");
        System.out.println(s.getName());

        System.out.println(s.getFirstName());
        int age = s.ageIn(2021);
        System.out.println(age);
        System.out.println(s.ageIn(2021));

        Student s1 = new Student("Doe", "Jonh", 2000);
        System.out.println(s1.name + " " + s1.firstName + " agée de " + s1.ageIn(2023));
        //que pour tester
        if (s.isValidBirthYear(2023)) {
            s.setBirthYear(2023);
        }
        else System.out.println("too young!!");
        Student s2 = new Student("Doe", "Jane", 2000);
        s1 = s2;
        System.out.println(s1.getFirstName());
        s1.setName("Schmitt");
        System.out.println(s2.getName());
        s1.setBirthYear(2010    );
        System.out.println(s1.getBirthYear() + " == 2010 ?" );
        s1.setBirthYear(2023);
        System.out.println(s1.getBirthYear()+ " == 2010 ?");
        s1.setBirthYear(2004);
        System.out.println(s1.getBirthYear()+ " == 2004 ?");
        s = new Student("Doe", "Jane", 2023);
        System.out.println(s.getBirthYear()+ " == 1901 ?");
    }

}