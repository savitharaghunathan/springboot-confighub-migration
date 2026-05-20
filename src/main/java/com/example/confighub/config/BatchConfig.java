package com.example.confighub.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Profile("batch")
public class BatchConfig {

    // Pattern 37: Uses spring-boot-starter-batch which relies on JDBC metadata
    // In 4.0, spring-boot-starter-batch-jdbc is needed explicitly
    @Bean
    public Job configImportJob(JobRepository jobRepository, Step importStep) {
        return new JobBuilder("configImportJob", jobRepository)
                .start(importStep)
                .build();
    }

    @Bean
    public Step importStep(JobRepository jobRepository,
                            PlatformTransactionManager transactionManager) {
        return new StepBuilder("importStep", jobRepository)
                .<String, String>chunk(100, transactionManager)
                .reader(configItemReader())
                .processor(configItemProcessor())
                .writer(configItemWriter())
                .build();
    }

    @Bean
    public ItemReader<String> configItemReader() {
        return () -> null;
    }

    @Bean
    public ItemProcessor<String, String> configItemProcessor() {
        return item -> item.strip();
    }

    @Bean
    public ItemWriter<String> configItemWriter() {
        return items -> {};
    }
}
