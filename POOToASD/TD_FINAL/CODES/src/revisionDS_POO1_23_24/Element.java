package revisionDS_POO1_23_24;
/**
 * Element is the abstract class that represents the elements of the system.
 */
public abstract class Element {
    private String name;

    /**
     * Constructs an element with the given name.
     * @param name the name of the element
     */
    protected Element(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the element.
     * @return the name of the element
     */
    public String getName() {
        return name;
    }

    /**
     * Perform an action on the element.
     * @param actionName the name of the action to perform
     * @return the result of the action
     */
    public abstract Result performAction(String actionName);
}
