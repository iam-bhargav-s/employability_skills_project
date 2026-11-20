package service;

import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.JobCard;
import model.SparePart;
import model.Vehicle;
import exceptions.JobCardNotFoundException;

public class JobCardService {
    private List<JobCard> jobCards = new ArrayList<>();
    private int nextJobId = 1001;

    public JobCard createJobCard(Customer customer, Vehicle vehicle, String complaint) {
        JobCard job = new JobCard(nextJobId++, customer, vehicle, complaint);
        jobCards.add(job);
        return job;
    }

    public JobCard findJobCard(int jobId) throws JobCardNotFoundException {
        for (JobCard job : jobCards) {
            if (job.getJobId() == jobId) return job;
        }
        throw new JobCardNotFoundException("Job card with ID " + jobId + " was not found.");
    }

    public void addSparePart(int jobId, SparePart part) throws JobCardNotFoundException {
        findJobCard(jobId).addSparePart(part);
    }

    public List<JobCard> getAllJobCards() {
        return jobCards;
    }
}
