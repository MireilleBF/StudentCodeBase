package revisionDS_POO1_23_24;

public enum WaterStatus {
    LIQUID(1,"Liquid : 0°C < T < 100°C"),
    FROZEN(2,"Frozen : T <= 0°C"),
    GAS(0,"Gas : T > 100°C");

    private final String description;

    public int getCode() {
        return code;
    }

    private final int code;

    WaterStatus(int code, String description) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }
}
