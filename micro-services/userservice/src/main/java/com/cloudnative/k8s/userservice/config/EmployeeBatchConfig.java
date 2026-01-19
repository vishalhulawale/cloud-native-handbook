package com.cloudnative.k8s.userservice.config;

import com.cloudnative.k8s.userservice.entity.Employee;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.batch.infrastructure.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class EmployeeBatchConfig {

    /* ===================== READER ===================== */

    @Bean
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeCsvReader")
                .resource(new ClassPathResource("employees.csv"))
                .linesToSkip(1)
                .delimited()
                .names("id", "name", "salary")
                .targetType(Employee.class)
                .build();
    }

    /* ===================== PROCESSOR ===================== */

    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return employee -> {
            System.out.println("Processor - Increasing salary for Employee ID: " + employee.getId());
            employee.setSalary(employee.getSalary() * 1.10);
            return employee;
        };
    }

    /* ===================== WRITER ===================== */

    @Bean
    public JpaItemWriter<Employee> writer(EntityManagerFactory emf) {
        return new JpaItemWriterBuilder<Employee>()
                .entityManagerFactory(emf)
                .build();
    }

    @Bean
    public FlatFileItemWriter<Employee> flatFileWriter() {
        return new FlatFileItemWriterBuilder<Employee>()
                .name("employeeCsvWriter")
                .resource(new FileSystemResource("updated_employees.csv"))
                .delimited()
                .delimiter(",")
                .names("id", "name", "salary")
                .build();
    }

    /* ===================== STEP ===================== */

    @Bean
    public Step employeeStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            EntityManagerFactory emf
    ) {
        return new StepBuilder("employeeStep", jobRepository)
                .<Employee, Employee>chunk(1000, transactionManager)
                .reader(reader())
                .processor(processor())
//                .writer(writer(emf))
                .writer(flatFileWriter())
                .build();

    }

    /* ===================== JOB ===================== */
    @Bean
    public Job employeeJob(
            JobRepository jobRepository,
            Step employeeStep
    ) {
        return new JobBuilder("employeeJob", jobRepository)
                .start(employeeStep)
                .build();
    }
}
