package com.gestionale.biblioteca.batch;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;

public class UtenteWriter implements ItemWriter<Utente> {

	private static final Logger log = LoggerFactory.getLogger(UtenteWriter.class);
	private String directoryPosition;

	private HSSFWorkbook workbook = new HSSFWorkbook();

	public UtenteWriter(String directoryPosition) {
		this.directoryPosition = directoryPosition;
	}

	public String getDirectoryPosition() {
		return directoryPosition;
	}

	public void setDirectoryPosition(String directoryPosition) {
		this.directoryPosition = directoryPosition;
	}

	@Override
	public void write(Chunk<? extends Utente> chunk) throws Exception {
		String name = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xls";
		File directory = new File(directoryPosition);
		if(!directory.isDirectory()) {
			throw new IOException("directory doesn't not exist");
		}
		File destinationFile = new File(directoryPosition + File.separator + name);
		for (Utente utente : chunk) {
			HSSFSheet sheet = this.workbook.createSheet(utente.getNome());
			for (Libro libro : utente.getLibriInPrestitoList()) {
				HSSFRow rowHeader = sheet.createRow(0);
				int cellPosition = 0;
				HSSFCell cellHeader = rowHeader.createCell(cellPosition++);
				cellHeader.setCellValue("id");
				cellHeader = rowHeader.createCell(cellPosition++);
				cellHeader.setCellValue("autore");
				cellHeader = rowHeader.createCell(cellPosition++);
				cellHeader.setCellValue("Anno Pubblicazione");
				int rowNumber = sheet.getPhysicalNumberOfRows();
				HSSFRow row = sheet.createRow(rowNumber++);
				// prendo la cella 0
				cellPosition = 0;
				HSSFCell cell = row.createCell(cellPosition++);
				// gli setto il valore
				cell.setCellValue(libro.getId());
				// prendo la cella 2
				cell = row.createCell(cellPosition++);
				// gli setto il valore
				cell.setCellValue(libro.getAutore());
				// prendo la cella 3
				cell = row.createCell(cellPosition++);
				// gli setto il valore
				cell.setCellValue(libro.getAnnoPubblicazione());
			}
		}

		
		if (chunk.isEnd()) {
			this.workbook.close();
			this.workbook.write(destinationFile);
			log.info("scritto file con nome" + name);
			this.workbook = new HSSFWorkbook();
		}
	}

}
