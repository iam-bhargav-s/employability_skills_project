package service;

import model.JobCard;
import model.SparePart;
import exceptions.JobCardNotFoundException;

public class BillingService {
    private JobCardService jobCardService;

    public BillingService(JobCardService jobCardService) {
        this.jobCardService = jobCardService;
    }

    public void setLabourCharge(int jobId, double charge)
            throws JobCardNotFoundException {
        if (charge < 0) throw new IllegalArgumentException("Charge cannot be negative.");
        jobCardService.findJobCard(jobId).setLabourCharge(charge);
    }

    public JobCard getJobCard(int jobId) throws JobCardNotFoundException {
        return jobCardService.findJobCard(jobId);
    }

    public void printBill(JobCard job) {
        System.out.println("\n========== SERVICE BILL ==========");
        System.out.println("Job ID       : " + job.getJobId());
        System.out.println("Customer     : " + job.getCustomer().getName());
        System.out.println("Vehicle      : " + job.getVehicle());
        System.out.println("Service Date : " + job.getServiceDate());
        System.out.println("\nSpare Parts:");
        if (job.getSpareParts().isEmpty()) {
            System.out.println("No spare parts added.");
        } else {
            for (SparePart part : job.getSpareParts()) System.out.println(part);
        }
        System.out.println("----------------------------------");
        System.out.println("Spare Parts Cost : Rs." + job.calculatePartsCost());
        System.out.println("Labour Charge    : Rs." + job.getLabourCharge());
        System.out.println("TOTAL BILL       : Rs." + job.calculateTotal());
        System.out.println("==================================");
    }
}
