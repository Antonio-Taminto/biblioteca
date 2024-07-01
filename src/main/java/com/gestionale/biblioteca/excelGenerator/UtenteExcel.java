package com.gestionale.biblioteca.excelGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import com.gestionale.biblioteca.model.Libro;
import com.gestionale.biblioteca.model.Utente;

@Component
public class UtenteExcel {

	public HSSFWorkbook generateExcelWorkbook(List<Utente> utenteList) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("user with all books");
		utenteSheet(sheet, utenteList);
		workbook.close();
		return workbook;
	}

	public void generateExcelWorkbookAll(List<Utente> utenteList) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("user with all books");
		utenteSheet(sheet, utenteList);
		workbook.close();
		FileOutputStream outputStream = new FileOutputStream("C:\\datiLib" + File.separator + "prova.xls");
		workbook.write(outputStream);
	}

	private HSSFSheet utenteSheet(HSSFSheet sheet, List<Utente> utenteList) {
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
		for (Utente utente : utenteList) {
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
		return sheet;
	}

	public HSSFSheet utenteSheet(HSSFSheet sheet, Utente utente, int rowNumber) {
		int cellPosition = 0;
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
		for (int i = 0; i < cellPosition; i++) {
			sheet.autoSizeColumn(i);
		}
		return sheet;
	}
}
