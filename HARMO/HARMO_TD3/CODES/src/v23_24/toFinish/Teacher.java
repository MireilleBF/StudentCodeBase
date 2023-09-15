package v23_24.toFinish;


import java.util.*;

/*
* Un enseignant est décrit par un nom, un prénom et une adresse mail où le contacter et la référence à son bureau par exemple A321
*/
/*
On souhaite pouvoir accéder en lecture au nom de l’enseignant, à son email et on souhaite pouvoir lui associer un nom de bureau sur la base suivante : nom du batiment et numéro de salle. Par exemple A321.
 */
public class Teacher extends PolytechMember {
    private String office;

    private static final String DEFAULT_OFFICE = "A 0";

    private List<Course> courses = new ArrayList<>();

    /***** Getters *****/

    public String getOffice() {
        return office;
    }

    public List<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    /***** Constructors *****/
    public Teacher(String name, String firstName, String office) {
        super(name, firstName);
        if (isOfficeInformationValid(office)) {
            this.office = office;
        }
        else {
            this.office = DEFAULT_OFFICE;
        }
    }
    @Override
    protected void buildEmail() {
        this.email = name + "." + firstName + "@univ-cotedazur";
    }

    /*
      un constructeur qui prend en paramètre le nom, le prénom et l’adresse mail et affecte par défaut le nom du bureau à A000.
     */
    public Teacher(String name, String firstName) {
        this(name, firstName, DEFAULT_OFFICE);
    }

    /***** Setters *****/

    /*
     On considère à présent que nom du bâtiment doit être une lettre comprise entre A et F.
     Ces contraintes pourront évoluer et on ne souhaite pas qu’il soit possible d’y accéder en dehors de la classe.
     Proposez une implémentation d’une méthode privée qui vérifie
     que les informations données sont correctes avant de modifier le nom du bureau.
     */
    public void setOffice(String building, int number) {
        if (isOfficeInformationValid(building, number)) {
            this.office = building + " " + number;
        }
    }


    /***** Methods *****/

    //17.	Ajouter à Enseignant une méthode qui vérifie s’il enseigne bien un cours donné.
    public boolean doYouTeach(Course c) {
        return courses.contains(c);
    }

    private boolean isOfficeInformationValid(String building, int number) {
        if ((building.length() != 1) || (building.charAt(0) < 'A') || (building.charAt(0) > 'F')) {
            return false;
        }
        return (number >= 0) && (number <= 500);
    }
    protected boolean isOfficeInformationValid(String officeName) {
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

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", office='" + office + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Teacher teacher = (Teacher) o;
        return Objects.equals(name, teacher.name) && Objects.equals(firstName, teacher.firstName) && Objects.equals(email, teacher.email) && Objects.equals(office, teacher.office) && Objects.equals(courses, teacher.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(office, courses);
    }

    //1.
   /* Faisons le choix suivant :
    un enseignant choisit à un cours c1 dont il prend la responsabilité, revient à appeler p1.teaches(c1) qui déclenche un appel à c1.setProfessor(p1).
    */
    public void teaches(Course c) {
        courses.add(c);
        c.setProfessor(this);
    }

    /**
     * 2.	Écrire la méthode qui calcule la moyenne du niveau de difficulté des cours donnés par un enseignant
     */
    public double averageDifficulty() {
        double sum = 0;
        for (Course c : courses) {
            sum += c.getDifficulty();
        }
        return sum / courses.size();
    }


    public static void main(String[] args) {
        Teacher teacher1 = new Teacher("Doe", "John");
        System.out.println(teacher1);
    }
}
