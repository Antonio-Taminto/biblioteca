package com.gestionale.biblioteca.batch;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.gestionale.biblioteca.excelGenerator.UtenteExcel;
import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.UtenteRepository;

@Configuration
@ComponentScan("com.gestionale.biblioteca.excelGenerator")
public class BatchConfig {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private UtenteExcel utenteExcel;

	private String directoryPosition = "C:\\datiLib";

	@Bean
	public Step step(JobRepository repository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("excel-step", repository).<List<Utente>, HSSFWorkbook>chunk(10, transactionManager)
				.reader(itemReader()).processor(new UtenteProcessor()).writer(itemWriter(directoryPosition))
				.taskExecutor(taskExecutor()).build();
	}

	private ItemReader<List<Utente>> itemReader() {
		RepositoryItemReader<List<Utente>> itemReader = new RepositoryItemReader<List<Utente>>();
		itemReader.setRepository(this.utenteRepository);
		itemReader.setMethodName("findAll");

		return itemReader;
	}

	public MultiResourceItemWriter<HSSFWorkbook> itemWriter(String directoryPosition) {
		MultiResourceItemWriter<HSSFWorkbook> itemWriter = new MultiResourceItemWriter<HSSFWorkbook>();
		itemWriter.setDelegate();
		itemWriter.setResource(new FileSystemResource(directoryPosition + "/successReport"));
		return itemWriter;
	}

	private TaskExecutor taskExecutor() {
		SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
		asyncTaskExecutor.setConcurrencyLimit(10);
		return asyncTaskExecutor;
	}

	@Bean
	public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("excel-job", jobRepository).flow(step(jobRepository, transactionManager)).end().build();
	}

}
