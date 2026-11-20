package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobCard {
    private int jobId;
    private Customer customer;
    private Vehicle vehicle;
    private String complaint;
    private String serviceStatus;
    private LocalDate serviceDate;
    private double labourCharge;
    private List<SparePart> spareParts;

    public JobCard(int jobId, Customer customer, Vehicle vehicle, String complaint) {
        this.jobId = jobId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.complaint = complaint;
        this.serviceStatus = "Pending";
        this.serviceDate = LocalDate.now();
        this.labourCharge = 0.0;
        this.spareParts = new ArrayList<>();
    }

    public int getJobId() { return jobId; }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
    public String getComplaint() { return complaint; }
    public String getServiceStatus() { return serviceStatus; }
    public LocalDate getServiceDate() { return serviceDate; }
    public double getLabourCharge() { return labourCharge; }
    public List<SparePart> getSpareParts() { return spareParts; }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public void setLabourCharge(double labourCharge) {
        this.labourCharge = labourCharge;
    }

    public void addSparePart(SparePart part) {
        spareParts.add(part);
    }

    public double calculatePartsCost() {
        double total = 0;
        for (SparePart part : spareParts) total += part.getTotalPrice();
        return total;
    }

    public double calculateTotal() {
        return calculatePartsCost() + labourCharge;
    }

    @Override
    public String toString() {
        return "Job ID: " + jobId
                + " | Customer: " + customer
                + " | Vehicle: " + vehicle
                + " | Complaint: " + complaint
                + " | Date: " + serviceDate
                + " | Status: " + serviceStatus;
    }
}
