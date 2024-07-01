package com.gestionale.biblioteca.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.UtenteRepository;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@ComponentScan("com.gestionale.biblioteca.excelGenerator")
public class BatchConfig {

	@Autowired
	private UtenteRepository utenteRepository;

	private String directoryPosition = "C:\\Users\\User\\Desktop\\datiLib";
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public Step step(JobRepository repository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("excel-step", repository).<Utente, Utente>chunk(10, transactionManager)
				.reader(itemReader())
				.processor(processor())
				.writer(itemWriter(directoryPosition))
				.build();
	}

	private ItemReader<Utente> itemReaderRepo() {
		RepositoryItemReader<Utente> itemReaderRepo = new RepositoryItemReader<Utente>();
		itemReaderRepo.setRepository(this.utenteRepository);
		itemReaderRepo.setMethodName("findAll");
		return itemReaderRepo;
	}
	
	private JpaCursorItemReader<Utente> itemReader() {
		return new JpaCursorItemReaderBuilder<Utente>()
				.name("utentiReader")
		        .entityManagerFactory(entityManagerFactory)
		        .queryString("SELECT u FROM Utente u")
		        .build();
	}
	private UtenteProcessor processor() {
		return new UtenteProcessor();
	}

	public ItemWriter<Utente> itemWriter(String directoryPosition) {
		ItemWriter<Utente> itemWriter = new UtenteWriter(directoryPosition);
		return itemWriter;
	}

	@Bean(name = "excel-job")
	public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("excel-job", jobRepository).flow(step(jobRepository, transactionManager)).end().build();
	}

}
