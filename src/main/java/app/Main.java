package app;

import java.util.List;
import java.util.Scanner;
import exceptions.InvalidServiceStatusException;
import exceptions.JobCardNotFoundException;
import model.Customer;
import model.JobCard;
import model.SparePart;
import model.Vehicle;
import service.BillingService;
import service.JobCardService;
import service.ServiceHistoryService;
import service.ServiceStatusService;
import util.InputUtil;

public class Main {
    public static void main(String[] args) {
        JobCardService jobCardService = new JobCardService();
        ServiceStatusService statusService = new ServiceStatusService(jobCardService);
        BillingService billingService = new BillingService(jobCardService);
        ServiceHistoryService historyService = new ServiceHistoryService(jobCardService);

        try (Scanner sc = new Scanner(System.in)) {
            int choice;
            do {
                System.out.println("\n===== VEHICLE SERVICE CENTER MANAGEMENT =====");
                System.out.println("1. Create Job Card");
                System.out.println("2. View All Job Cards");
                System.out.println("3. Update Service Status");
                System.out.println("4. Add Spare Parts");
                System.out.println("5. Add Labour Charge");
                System.out.println("6. Generate Service Bill");
                System.out.println("7. View Service History");
                System.out.println("8. View Job Card Details");
                System.out.println("0. Exit");

                choice = InputUtil.readInt(sc, "Enter your choice: ");

                switch (choice) {
                    case 1 -> createJobCard(sc, jobCardService);
                    case 2 -> displayAllJobs(jobCardService);
                    case 3 -> updateStatus(sc, statusService);
                    case 4 -> addSparePart(sc, jobCardService);
                    case 5 -> addLabourCharge(sc, billingService);
                    case 6 -> generateBill(sc, billingService);
                    case 7 -> viewHistory(sc, historyService);
                    case 8 -> viewJobCardDetails(sc, jobCardService);
                    case 0 -> System.out.println("Thank you for using Vehicle Service Center Management System.");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } while (choice != 0);
        }
    }

    private static void createJobCard(Scanner sc, JobCardService service) {
        System.out.println("\n--- CREATE JOB CARD ---");
        String customerName = InputUtil.readLine(sc, "Enter customer name: ");
        String phone = InputUtil.readLine(sc, "Enter phone number: ");
        String vehicleNumber = InputUtil.readLine(sc, "Enter vehicle number: ");
        String vehicleModel = InputUtil.readLine(sc, "Enter vehicle model: ");
        String complaint = InputUtil.readLine(sc, "Enter vehicle complaint: ");

        Customer customer = new Customer(customerName, phone);
        Vehicle vehicle = new Vehicle(vehicleNumber, vehicleModel);
        JobCard job = service.createJobCard(customer, vehicle, complaint);

        System.out.println("Job card created successfully.");
        System.out.println("Generated Job ID: " + job.getJobId());
    }

    private static void displayAllJobs(JobCardService service) {
        List<JobCard> jobs = service.getAllJobCards();
        if (jobs.isEmpty()) {
            System.out.println("No job cards found.");
            return;
        }
        System.out.println("\n--- ALL JOB CARDS ---");
        System.out.printf(
                "| %-7s | %-16s | %-12s | %-14s | %-12s | %-24s | %-12s | %-13s |%n",
                "Job ID", "Customer", "Phone", "Vehicle No.", "Model",
                "Complaint", "Date", "Status");
        System.out.println(
                "+---------+------------------+--------------+----------------+--------------+--------------------------+--------------+---------------+");
        for (JobCard job : jobs) {
            System.out.printf(
                    "| %-7s | %-16s | %-12s | %-14s | %-12s | %-24s | %-12s | %-13s |%n",
                    job.getJobId(),
                    fitColumn(job.getCustomer().getName(), 16),
                    fitColumn(job.getCustomer().getPhone(), 12),
                    fitColumn(job.getVehicle().getVehicleNumber(), 14),
                    fitColumn(job.getVehicle().getModel(), 12),
                    fitColumn(job.getComplaint(), 24),
                    job.getServiceDate(),
                    fitColumn(job.getServiceStatus(), 13));
        }
    }

    private static String fitColumn(String value, int width) {
        if (value.length() <= width) {
            return value;
        }
        return value.substring(0, width - 3) + "...";
    }

    private static void updateStatus(Scanner sc, ServiceStatusService service) {
        int id = InputUtil.readInt(sc, "Enter job ID: ");
        System.out.println("1. Pending");
        System.out.println("2. In Progress");
        System.out.println("3. Completed");
        int option = InputUtil.readInt(sc, "Select new status: ");

        String status;
        switch (option) {
            case 1 -> status = "Pending";
            case 2 -> status = "In Progress";
            case 3 -> status = "Completed";
            default -> {
                System.out.println("Invalid status choice.");
                return;
            }
        }

        try {
            service.updateStatus(id, status);
            System.out.println("Service status updated successfully.");
        } catch (JobCardNotFoundException | InvalidServiceStatusException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addSparePart(Scanner sc, JobCardService service) {
        int id = InputUtil.readInt(sc, "Enter job ID: ");
        String name = InputUtil.readLine(sc, "Enter spare part name: ");
        int quantity = InputUtil.readInt(sc, "Enter quantity: ");
        double price = InputUtil.readDouble(sc, "Enter price per unit: ");

        if (quantity <= 0 || price < 0) {
            System.out.println("Quantity must be positive and price cannot be negative.");
            return;
        }

        try {
            service.addSparePart(id, new SparePart(name, quantity, price));
            System.out.println("Spare part added successfully.");
        } catch (JobCardNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addLabourCharge(Scanner sc, BillingService service) {
        int id = InputUtil.readInt(sc, "Enter job ID: ");
        double charge = InputUtil.readDouble(sc, "Enter labour charge: Rs.");
        if (charge < 0) {
            System.out.println("Labour charge cannot be negative.");
            return;
        }
        try {
            service.setLabourCharge(id, charge);
            System.out.println("Labour charge saved successfully.");
        } catch (JobCardNotFoundException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void generateBill(Scanner sc, BillingService service) {
        int id = InputUtil.readInt(sc, "Enter job ID: ");
        try {
            JobCard job = service.getJobCard(id);
            service.printBill(job);
        } catch (JobCardNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewHistory(Scanner sc, ServiceHistoryService service) {
        String vehicleNumber = InputUtil.readLine(sc, "Enter vehicle number: ");
        List<JobCard> history = service.getHistory(vehicleNumber);

        if (history.isEmpty()) {
            System.out.println("No service history found for this vehicle.");
            return;
        }

        System.out.println("\n--- SERVICE HISTORY ---");
        for (JobCard job : history) {
            System.out.println(job);
            System.out.println("Total Bill: Rs." + job.calculateTotal());
            System.out.println("--------------------------------------");
        }
    }

    private static void viewJobCardDetails(Scanner sc, JobCardService service) {
        int id = InputUtil.readInt(sc, "Enter job ID: ");
        try {
            JobCard job = service.findJobCard(id);
            System.out.println("\n--- JOB CARD DETAILS ---");
            System.out.println("Job ID       : " + job.getJobId());
            System.out.println("Customer     : " + job.getCustomer().getName());
            System.out.println("Phone        : " + job.getCustomer().getPhone());
            System.out.println("Vehicle No.  : " + job.getVehicle().getVehicleNumber());
            System.out.println("Model        : " + job.getVehicle().getModel());
            System.out.println("Complaint    : " + job.getComplaint());
            System.out.println("Service Date : " + job.getServiceDate());
            System.out.println("Status       : " + job.getServiceStatus());
            System.out.println("Total Bill   : Rs." + job.calculateTotal());
        } catch (JobCardNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
