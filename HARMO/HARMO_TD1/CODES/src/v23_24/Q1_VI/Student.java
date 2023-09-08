package v23_24.Q1_VI;

public class Student {


    //Part I : Questions 1 à 3 ------------------------
    private String name;
    private String firstName;
    private int birthYear;

    //Part I : Questions 5 et 6------------------------
    //Part I : Question 24 ------------------------
    //Part I : Question 25 ------------------------
    public Student(String aName, String firstName, int dateOfBirth) {
        name = aName;
        this.firstName = firstName;
        if (isValidBirthYear(dateOfBirth)) {
            this.birthYear = dateOfBirth;
        }
        else {
            this.birthYear = 1901;
        }
    }

    //Part I : Question 24 ------------------------
    private boolean isValidBirthYear(int dateOfBirth) {
        return (dateOfBirth >= 1900) && (dateOfBirth <= 2010);
    }


    //Part I : Questions 8  et 9 ------------------------
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

    //Part I : Question 31 ------------------------
    public void setBirthYear(int birthYear) {
        if (isValidBirthYear(birthYear))
            this.birthYear = birthYear;
    }

    //Part I : Question 10 ------------------------
    public int ageIn(int year) {
        return year - birthYear;
    }



    //Part I : Question 14 ------------------------
    public static void main(String[] args) {
        Student s = new Student("Doe", "Jane", 2000);
        System.out.println(s);

        System.out.print(s.getName() + " becomes ");
        s.setName("Haddock");
        System.out.println(s.getName());

        System.out.print(s.getFirstName() + " becomes ");
        s.setFirstName("Archibald");
        System.out.println(s.getFirstName());

        s.setBirthYear(2021);
        System.out.println(s.getBirthYear() + " different de " + 2021);
        s.setBirthYear(2004);
        System.out.println(s.getBirthYear() + " is " + 2004);
        System.out.println(s.getFirstName());
        int age = s.ageIn(2021);
        System.out.println(age);
        System.out.println(s.ageIn(2021));

        Student s1 = new Student("Doe", "Jonh", 2000);
        System.out.println(s1.name + " " + s1.firstName + " agée de " + s1.ageIn(2023));

        //Don't do that !!
        if (s.isValidBirthYear(2023))
            s.setBirthYear(2023);
        else System.out.println("too young!!");
        Student s2 = new Student("Doe", "Jane", 2000);
        s1 = s2;
        System.out.println(s1.getFirstName());
        s1.setName("Schmitt");
        System.out.println(s2.getName());
    }

}