package com.gestionale.biblioteca.batch;

import java.io.File;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.gestionale.biblioteca.excelGenerator.UtenteExcel;
import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;

public class UtenteWriter implements ItemWriter<Utente> {
	private String directoryPosition;

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
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet sheet = hssfWorkbook.createSheet();
		HSSFRow rowHeader = sheet.createRow(0);
		HSSFCell cellHeader = rowHeader.createCell(0);
		cellHeader.setCellValue("nome");
		cellHeader = rowHeader.createCell(1);
		cellHeader.setCellValue("id");
		cellHeader = rowHeader.createCell(2);
		cellHeader.setCellValue("autore");
		cellHeader = rowHeader.createCell(3);
		cellHeader.setCellValue("Anno Pubblicazione");
		int rowNumber = 1;
		int cellPosition = 0;
		for (Utente utente : chunk) {

			for (Libro libro : utente.getLibriInPrestitoList()) {
				HSSFRow row = sheet.createRow(rowNumber++);
				// prendo la cella 0
				cellPosition = 0;
				HSSFCell cell = row.createCell(cellPosition++);
				// gli setto il valore
				cell.setCellValue(utente.getNome());
				// prendo la cella 1
				cell = row.createCell(cellPosition++);
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
		for (int i = 0; i < cellPosition; i++) {
			sheet.autoSizeColumn(i);
		}
		hssfWorkbook.close();
		hssfWorkbook.write(new File(directoryPosition + File.separator + "prova.xls"));
		
	}

}/*UtenteExcel utenteExcel = new UtenteExcel();
		@SuppressWarnings("unchecked")
		List<Utente> utenteList = (List<Utente>) chunk.getItems();
		HSSFWorkbook workbook = utenteExcel.generateExcelWorkbook(utenteList);
		workbook.close();
		workbook.write(new File(directoryPosition + File.separator + "prova.xls"));*/
