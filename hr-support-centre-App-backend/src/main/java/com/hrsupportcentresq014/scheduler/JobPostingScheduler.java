package com.hrsupportcentresq014.scheduler;


import com.hrsupportcentresq014.entities.Job;
import com.hrsupportcentresq014.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Component
public class JobPostingScheduler {
    private final JobRepository jobRepository;
    @Autowired
    public JobPostingScheduler(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }


    @Scheduled(fixedRate = 2 * 60 * 60 * 1000) // Run every 2 hours
    private void checkingJobClosingDate(){
        List<Job> jobs = jobRepository.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate currentDate = LocalDate.now();


        for (Job job : jobs){
            LocalDate closingDate = LocalDate.parse(job.getClosingDate(), formatter);
            if(closingDate.isBefore(currentDate)){
                job.setIsActive(false);
                jobRepository.save(job);
            }
        }
    }
}
