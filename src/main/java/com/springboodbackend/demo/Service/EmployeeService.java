package com.springboodbackend.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboodbackend.demo.exception.ResourceNotFoundException;
import com.springboodbackend.demo.model.Employee;
import com.springboodbackend.demo.model.Job;
import com.springboodbackend.demo.repository.EmployeeRepository;
import com.springboodbackend.demo.repository.JobRepository;


@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, JobRepository jobRepository) {
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
    }

    public Employee createEmployee(Employee employee) {
        String jobTitle = employee.getTitle();

        // Fetch the job details based on the provided job title
        Job job = jobRepository.findByTitle(jobTitle)
                .orElseThrow(() -> new ResourceNotFoundException("Job not exist with title: " + jobTitle));

        // Set the job for the employee
        employee.setJob(job);

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
    }

    public Employee updateEmployee(Long id, Employee employeeDetails) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        if (employeeDetails.getJob() != null) {
            long newJobId = employeeDetails.getJob().getJobId();
            Job newJob = jobRepository.findById(newJobId)
                    .orElseThrow(() -> new ResourceNotFoundException("Job not exist with id :" + newJobId));

            employee.setJob(newJob);
        }
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeeRepository.delete(employee);
    }
}
