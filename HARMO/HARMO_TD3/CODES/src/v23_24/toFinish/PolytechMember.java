package v23_24.toFinish;

import java.util.Objects;

public class PolytechMember {
    protected String name;
    protected String firstName;
    protected String email;


    public PolytechMember(String name, String firstName) {
        this.name = name;
        this.firstName = firstName;
        buildEmail();
    }

    protected void buildEmail() {
        this.email = name + "." + firstName + "@etu.univ-cotedazur";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setName(String name) {
        this.name = name;
        buildEmail();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        buildEmail();
    }

    @Override
    public String toString() {
        return "PNS " +
                "name='" + name + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PolytechMember that = (PolytechMember) o;
        return Objects.equals(name, that.name) && Objects.equals(firstName, that.firstName) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, firstName, email);
    }
}
