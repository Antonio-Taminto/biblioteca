package com.gestionale.biblioteca.batch;

import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.UtenteRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    private UtenteRepository utenteRepository;

    @Value("${directory}")
    private String directoryPosition;


    @Bean
    public Step step(JobRepository repository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("excel-step", repository).<Utente, Utente>chunk(10, transactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(itemWriter(directoryPosition)).build();
    }

    private ItemReader<Utente> itemReader() {
        return new UtenteReaderList(utenteRepository.findAll());
    }

    private UtenteProcessor processor() {
        return new UtenteProcessor();
    }

    public ItemWriter<Utente> itemWriter(String directoryPosition) {
        return new UtenteWriter(directoryPosition);
    }

    @Bean(name = "excel-job")
    public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("excel-job", jobRepository).flow(step(jobRepository, transactionManager)).end().build();
    }

}
