package com.gestionale.biblioteca.batch;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.batch.item.ItemProcessor;

import com.gestionale.biblioteca.excelGenerator.UtenteExcel;
import com.gestionale.biblioteca.model.Utente;

public class UtenteProcessor implements ItemProcessor<List<Utente>, HSSFWorkbook>{

	@Override
	public HSSFWorkbook process(List<Utente> item) throws Exception {
		UtenteExcel utenteExcel = new UtenteExcel();
		HSSFWorkbook workbook = utenteExcel.generateExcelWorkbook(item);
		return workbook;
	}

	
}
