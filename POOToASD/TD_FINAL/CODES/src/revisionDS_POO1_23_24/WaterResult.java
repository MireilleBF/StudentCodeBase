package revisionDS_POO1_23_24;

import java.util.Objects;

public class WaterResult implements Result<Integer, WaterStatus> {
    private final int temperature;
    private final WaterStatus status;

    public WaterResult(int temperature) {
        this.temperature = temperature;
        this.status = analyzeWater(temperature);
    }

    @Override
    public Integer getValue() {
        return temperature;
    }

    @Override
    public WaterStatus getStatus() {
        return status;
    }

    @Override
    public int getCode() {
        return status.getCode();
    }

    private WaterStatus analyzeWater(int temperature) {
        if (temperature <= 0) {
            return WaterStatus.FROZEN;
        } else if (temperature > 100) {
            return WaterStatus.GAS;
        } else {
            return WaterStatus.LIQUID;
        }
    }

    @Override
    public int compareTo(Result<Integer, WaterStatus> o) {
        return this.getValue().compareTo(o.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterResult that = (WaterResult) o;
        return Objects.equals(temperature, that.temperature) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, status);
    }

    public String toString() {
        return "T: " + getValue() + " °C : " + getStatus();
    }
}
