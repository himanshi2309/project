package com.springboodbackend.demo.Service;



import com.springboodbackend.demo.exception.ResourceNotFoundException;
import com.springboodbackend.demo.model.Job;
import com.springboodbackend.demo.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    public Job createJob(Job job) {
        return jobRepository.save(job);
    }

    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not exist with id :" + id));
    }

    public Job updateJob(Long id, Job jobDetails) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not exist with id :" + id));

        job.setTitle(jobDetails.getTitle());
        job.setType(jobDetails.getType());

        return jobRepository.save(job);
    }

    public void deleteJob(Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not exist with id :" + id));

        jobRepository.delete(job);
    }
    public  List<String> getAllJobTitles() {
        return jobRepository.findAll()
                .stream()
                .map(Job::getTitle)
                .collect(Collectors.toList());
    }
}
