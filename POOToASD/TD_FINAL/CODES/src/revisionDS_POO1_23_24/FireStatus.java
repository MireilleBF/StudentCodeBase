package revisionDS_POO1_23_24;

public enum FireStatus {
    NORMAL("Normal", 0),
    INFERNO("Inferno", 2),
    SMOKE("Smoke", 1);

    private final String description;
    private final int code;

    FireStatus(String description, int code) {
        this.description = description;
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }
}
