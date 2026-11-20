package service;

import java.util.ArrayList;
import java.util.List;
import model.JobCard;

public class ServiceHistoryService {
    private JobCardService jobCardService;

    public ServiceHistoryService(JobCardService jobCardService) {
        this.jobCardService = jobCardService;
    }

    public List<JobCard> getHistory(String vehicleNumber) {
        List<JobCard> history = new ArrayList<>();

        for (JobCard job : jobCardService.getAllJobCards()) {
            if (job.getVehicle().getVehicleNumber().equalsIgnoreCase(vehicleNumber)) {
                history.add(job);
            }
        }
        return history;
    }
}
