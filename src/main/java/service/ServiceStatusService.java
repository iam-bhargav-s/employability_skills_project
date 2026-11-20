package service;

import model.JobCard;
import exceptions.JobCardNotFoundException;
import exceptions.InvalidServiceStatusException;

public class ServiceStatusService {
    private JobCardService jobCardService;

    public ServiceStatusService(JobCardService jobCardService) {
        this.jobCardService = jobCardService;
    }

    public void updateStatus(int jobId, String status)
            throws JobCardNotFoundException, InvalidServiceStatusException {
        if (!status.equals("Pending") && !status.equals("In Progress")
                && !status.equals("Completed")) {
            throw new InvalidServiceStatusException("Invalid service status.");
        }

        JobCard job = jobCardService.findJobCard(jobId);
        job.setServiceStatus(status);
    }
}
