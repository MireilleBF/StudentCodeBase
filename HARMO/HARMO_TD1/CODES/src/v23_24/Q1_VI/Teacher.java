package v23_24.Q1_VI;

public class Teacher {


    //Part I : Question 4 ------------------------
    private String name;
    private String firstName;
    private String office;

    //Part I : Question 11 ------------------------
    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getOffice() {
        return office;
    }

    //Part I : Question 7 ------------------------

    public Teacher(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
    }
    public Teacher(String name, String firstName, String office) {
        this.name = name;
        this.firstName = firstName;
        this.office = office;
    }

    //Part I : Question 12 et  27 ------------------------
    public void buildOfficeName(String building, int number) {
        if (isOfficeInformationValid(building, number)) {
            this.office = building + " " + number;
        }
    }

    //Part I : Question 26 et  28 ------------------------
    private boolean isOfficeInformationValid(String building, int number) {
        if ((building.length() != 1) || (building.charAt(0) < 'A') || (building.charAt(0) > 'F')) {
            return false;
        }
        //Seule cette ligne est demandée dans la question
        return (number >= 0) && (number <= 500);
    }


    //Part I : Questions 22 à 24 ------------------------
    public static void main(String[] args) {
        Teacher teacher1 = new Teacher("Doe", "John");
        System.out.println(teacher1);
        System.out.println(teacher1.firstName);
        System.out.println(teacher1.getName());
        System.out.println(teacher1.getOffice());
        teacher1.buildOfficeName("A", 0);
        System.out.println(teacher1.getOffice());
        teacher1.buildOfficeName("A", 401);
        System.out.println(teacher1.getOffice());

    }
}
