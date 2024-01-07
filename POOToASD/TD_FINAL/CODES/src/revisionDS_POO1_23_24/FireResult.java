package revisionDS_POO1_23_24;

public class FireResult implements Result<String, FireStatus> {
    private final String value;
    private final FireStatus status;

    public FireResult(String value, FireStatus status) {
        this.value = value;
        this.status = status;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public FireStatus getStatus() {
        return status;
    }

    @Override
    public int getCode() {
        return status.getCode()+1;
    }

    @Override
    public int compareTo(Result<String, FireStatus> o) {
        return this.getCode() - o.getCode();
    }

    public String toString() {
        return "Value: " + getValue() + " Status: " + getStatus();
    }
}
