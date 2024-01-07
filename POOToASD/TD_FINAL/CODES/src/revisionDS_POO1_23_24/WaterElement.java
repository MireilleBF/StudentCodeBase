package revisionDS_POO1_23_24;

import java.util.HashMap;
import java.util.Map;


public class WaterElement extends Element {
    public static final String HEAT_ACTION = "heat";
    public static final String FREEZE_ACTION = "freeze";
    public static final String BOIL_ACTION = "boil";
    public static final String CONDENSE_ACTION = "condense";
    // ...

    private final Map<String, Result<Integer, WaterStatus>> actionsMap = initializeActionsMap();

    public WaterElement(String waterElementName) {
        super(waterElementName);
    }

    private Map<String, Result<Integer, WaterStatus>> initializeActionsMap() {
        Map<String, Result<Integer, WaterStatus>> map = new HashMap<>();
        map.put(HEAT_ACTION, new WaterResult(80));
        map.put(FREEZE_ACTION, new WaterResult(-4));
        map.put(BOIL_ACTION, new WaterResult(100));
        map.put(CONDENSE_ACTION, new WaterResult(40));
        return map;
    }

    @Override
    public Result<Integer, WaterStatus> performAction(String actionName) {
        Result<Integer, WaterStatus> result = actionsMap.get(actionName);
        if (result != null) {
            return result;
        } else {
            throw new IllegalArgumentException("Action not supported");
        }
    }
}