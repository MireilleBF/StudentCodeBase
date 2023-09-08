package v23_24.QVII_IX;

public class Teacher {

/*
 //On considere que on doit passer par build office Name

 public void setOffice(String officeName) {
        if (isOfficeInformationValid(officeName)) {
            this.office = officeName;
        }
        else {
            this.office = DEFAULT_OFFICE;
        }
    }

 */


    public String getEmail() {
        return email;
    }

    private String name;
    private String firstName;
    private String office;

    //Part I : Question 36 ------------------------
    private String email;


     static final String DEFAULT_EMAIL = "@univ-cotedazur";

    //Part I : Question 37 & 44 ------------------------
     static final String DEFAULT_OFFICE = "A 0";

    //Part I : Question 37 ------------------------
    public Teacher(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
        this.office = DEFAULT_OFFICE;
        buildEmail();
    }

    //Part I : Question 38 ------------------------
    public Teacher(String name, String firstName, String office) {
        this(name, firstName);
        if (isOfficeInformationValid(office)) {
            this.office = office;
        }
    }

    private void buildEmail() {
        this.email = name + "." + firstName + "@univ-cotedazur";
    }


    public void buildOfficeName(String building, int number) {
        if (isOfficeInformationValid(building, number)) {
            this.office = building + " " + number;
        }
    }

    private boolean isOfficeInformationValid(String building, int number) {
        if ((building.length() != 1) || (building.charAt(0) < 'A') || (building.charAt(0) > 'F')) {
            return false;
        }
        //Seule cette ligne est demandée dans la question
        return (number >= 0) && (number <= 500);
    }

    //Part I : Question 48 ------------------------
    boolean isOfficeInformationValid(String officeName) {
        if (officeName.length() <3) {
            return false;
        }
        if ((officeName.charAt(0) < 'A') || (officeName.charAt(0) > 'F')) {
            return false;
        }
        if (officeName.charAt(1) != ' ') {
            return false;
        }

        int number = 0;
        String numberPart = officeName.substring(2,officeName.length() - 1);
        try {
            number = Integer.parseInt(numberPart);
        }
        catch (NumberFormatException e) {
            return false;
        }
        return (number >= 0) || (number <= 500);
    }


    /* ------------------------ */
    /*  Getters and setters     */
    /* ------------------------ */
    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getOffice() {
        return office;
    }

    public void setName(String name) {
        this.name = name;
        buildEmail();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        buildEmail();
    }

    //Part I : Question 42 ------------------------
    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", office='" + office + '\'' +
                '}';
    }

    //Part I : Question 43 ------------------------
    public static void main(String[] args) {
        Teacher teacher1 = new Teacher("Ada", "Lovelace");
        System.out.println(teacher1);
        System.out.println(teacher1.getFirstName());
        System.out.println(teacher1.getName());
        System.out.println(teacher1.getOffice());
        teacher1.buildOfficeName("A", 0);
        System.out.println(teacher1.getOffice());
        teacher1.buildOfficeName("A", 401);
        System.out.println(teacher1.getOffice());
        //the email must be updated
        teacher1.setFirstName("Charles");
        System.out.println(teacher1);
        teacher1.setName("Babbage");
        System.out.println(teacher1);
        //The office name is the good one
        Teacher teacher2 = new Teacher("Doe", "John", "A 101");
        System.out.println(teacher2.getOffice() + " == " + "A 101");
        //The office name is the default one
        Teacher teacher3 = new Teacher("Doe", "John", "A 501");
        System.out.println(teacher3.getOffice() + " == " + DEFAULT_OFFICE);
        //The office name is the default one
        Teacher teacher4 = new Teacher("Doe", "John", "G 50");
        System.out.println(teacher4.getOffice() + " == " + DEFAULT_OFFICE);
        Teacher teacher5 = new Teacher("Doe", "John", "A Temple");
        System.out.println(teacher5.getOffice() + " == " + DEFAULT_OFFICE);
        Teacher teacher6 = new Teacher("Doe", "John", "A50");


    }

}
