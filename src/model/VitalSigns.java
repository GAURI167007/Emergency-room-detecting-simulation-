package model;

public class VitalSigns {

    private int heartRate;
    private int systolicBP;
    private int oxygenSaturation;
    private double temperature;
    private int respiratoryRate;
// these are all the info that will be collected to ensure correct execution
public VitalSigns(
            int heartRate,
            int systolicBP,
            int oxygenSaturation,
            double temperature,
            int respiratoryRate) {

        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.oxygenSaturation = oxygenSaturation;
        this.temperature = temperature;
        this.respiratoryRate = respiratoryRate;
    } public int getHeartRate() {
        return heartRate;
    }public int getSystolicBP() {
        return systolicBP;
    }public int getOxygenSaturation() {
        return oxygenSaturation;
    }
    public double getTemperature() {
        return temperature;
    }public int getRespiratoryRate() {
        return respiratoryRate;
    }public void update(
            int heartRate,
            int systolicBP,
            int oxygenSaturation,
            double temperature,
            int respiratoryRate) {

        this.heartRate = heartRate;
        this.systolicBP = systolicBP;
        this.oxygenSaturation = oxygenSaturation;
        this.temperature = temperature;
        this.respiratoryRate = respiratoryRate;
    }public boolean indicatesHighRisk() {
        return heartRate > 120
                || heartRate < 50
                || systolicBP < 90
                || oxygenSaturation < 92
                || respiratoryRate > 30;
    }


    @Override
public String toString() {

        return "HR=" + heartRate
                + ", BP=" + systolicBP
                + ", SpO2=" + oxygenSaturation + "%"
                + ", Temp=" + temperature
                + ", RR=" + respiratoryRate;
    }
}
