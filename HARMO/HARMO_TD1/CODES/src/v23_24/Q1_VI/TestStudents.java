package v23_24.Q1_VI;

public class TestStudents {
    //Part I : Question 29 ------------------------
    public static void main(String[] args) {

        Student s = new Student("Doe", "Jane", 2000);
        System.out.println(s);
        System.out.println(s.getName());
        s.setName("Haddock");
        System.out.println(s.getName());

        //Student s1 = new Student("Doe", "Jonh", 2000);
       // System.out.println(s1.name + " " + s1.firstName + " agée de " + s1.ageIn(2023));
       // if (s.isValidBirthYear(2023))
       //     s.setBirthYear(2023);
       // else System.out.println("too young!!");
    }


}
