package model;

public class Vehicle {
    private String vehicleNumber;
    private String model;

    public Vehicle(String vehicleNumber, String model) {
        this.vehicleNumber = vehicleNumber;
        this.model = model;
    }

    public String getVehicleNumber() { return vehicleNumber; }
    public String getModel() { return model; }

    @Override
    public String toString() {
        return vehicleNumber + " - " + model;
    }
}
