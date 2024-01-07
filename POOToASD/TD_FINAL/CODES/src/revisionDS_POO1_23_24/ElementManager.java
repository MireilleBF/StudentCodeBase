package revisionDS_POO1_23_24;

import java.util.*;

public class ElementManager {

    private final String name;

    /**
     * The elements managed by this manager.
     */
    private final Map<String, Element> elements;
    /**
     * The trace of the experiments performed on the elements.
     */
    private final ExperimentTrace trace = new ExperimentTrace();

    /**
     * Returns the name of this manager.
     * @return the name of this manager.
     */
    public String getName() {
        return name;
    }

    /*************
     * CONSTRUCTOR
     *
     */

    /**
     * Creates a new manager with the given name and no elements.
     * @param name
     */
    public ElementManager(String name) {
        this(name, new HashMap<>());
    }

    /**
     * Creates a new manager with the given name and elements.
     * @param name
     * @param elements
     */
    public ElementManager(String name, Map<String, Element> elements) {
        this.name = name;
        this.elements = elements;
    }

    /**
     * Performs the given action on the given element and adds the result to the trace.
     * @param element
     * @param action the action to be performed on the element.
     * @throws ExperimentTraceException if the action cannot be performed on the element.
     */
    public <T, E extends Enum> void performAction(Element element, Action<T,E> action) throws ExperimentTraceException {
        if (element != null) {
            Result<T,E> result = action.executeOn(element);
            trace.addExperimentResult(result);
        }
    }

    public <T, E extends Enum>  void performActionListOn( String elementName, List<Action> action) throws ExperimentTraceException {
        Element element = elements.get(elementName);
        if (element != null) {
            for (Action<T,E> a : action) {
                Result<T,E> result = a.executeOn(element);
                trace.addExperimentResult(result);
            }
        }
    }


    public Optional<Element> getElement(String elementName) {
        return Optional.ofNullable(elements.get(elementName));
    }

    public Element addElement(Element elem) {
        if (elem != null) {
            elements.put(elem.getName(), elem);
        }
        return elem;
    }


    public static void main(String[] args) {
        ElementManager forEver = new ElementManager("ForEver");

        String waterElementName = "WaterElement";

        Element waterElement = new WaterElement(waterElementName);
        Element fireElement = new FireElement("FireElement");

        forEver.addElement(waterElement);
        forEver.addElement(fireElement);

        Optional<Element> retrievedWaterElement = forEver.getElement(waterElementName);
        if (retrievedWaterElement.isPresent()) {
            System.out.println("WaterElement found in ForEver.");
        } else {
            System.out.println("WaterElement not found in ForEver.");
        }

        List<Action> actionList = new ArrayList<>();
        actionList.add(new Action("burn"));
        actionList.add(new Action("extinguish"));
        System.out.println("Performing action list on FireElement");
        System.out.println("====================================");
        try {
            forEver.performActionListOn("FireElement", actionList);
        } catch (ExperimentTraceException e) {
            System.out.println("Exception ExperimentTraceException occurred: " + e );
        }

        System.out.println("results of the experiment: " + forEver.trace);
        System.out.println("====================================");
        System.out.println("Performing action list on WaterElement");
        System.out.println("====================================");
        actionList.clear();
        actionList.add(new Action("heat"));
        actionList.add(new Action("freeze"));
        actionList.add(new Action("boil"));
        actionList.add(new Action("condense"));
        try {
            forEver.performActionListOn("WaterElement", actionList);
        } catch (ExperimentTraceException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        System.out.println("results of the experiment: " + forEver.trace);
        System.out.println("====================================");

    }

    public List<Result> getTrace() {
        return trace.getResultCopy();
    }

    public void removeDuplicatedResults() {
        trace.removeDuplicatedResults();
    }
}


