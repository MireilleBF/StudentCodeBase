package revisionDS_POO1_23_24;

public class FireElement extends Element {
    public FireElement(String fireElementName) {
        super(fireElementName);
    }

    @Override
    public Result<String,FireStatus> performAction(String actionName) {
        if (actionName.equals("burn")) {
            return new FireResult("burning", FireStatus.NORMAL);
        } else if (actionName.equals("extinguish")) {
            return new FireResult("extinguished", FireStatus.SMOKE);
        } else {
            throw new IllegalArgumentException("Action not supported");
        }
    }
}
