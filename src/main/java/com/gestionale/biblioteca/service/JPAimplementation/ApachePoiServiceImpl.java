package com.gestionale.biblioteca.service.JPAimplementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.gestionale.biblioteca.excelGenerator.UtenteExcel;
import com.gestionale.biblioteca.model.Utente;
import com.gestionale.biblioteca.repository.UtenteRepository;
import com.gestionale.biblioteca.service.ApachePoiService;

import jakarta.servlet.http.HttpServletResponse;

@Service
@ComponentScan("com.gestionale.biblioteca.excelGenerator")
public class ApachePoiServiceImpl implements ApachePoiService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private UtenteExcel utenteExcel;

	private String directoryPosition = "C:\\datiLib";

	@Override
	public void generateExcel() throws IOException {
		List<Utente> utenteList = utenteRepository.findAll();
		HSSFWorkbook workbook = utenteExcel.generateExcelWorkbook(utenteList);
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH.mm.ss"));
		String originalNameEncode = URLEncoder.encode(date + ".xls", "UTF-8");
		File directory = new File(directoryPosition);
		if (!directory.isDirectory()) {
			workbook.close();
			throw new IOException(directory.toString() + " non è una directiory");
		}
		File directorySavedFile = new File(directory + File.separator + originalNameEncode);
		FileOutputStream outputStream = new FileOutputStream(directorySavedFile);
		workbook.write(outputStream);
	}

	@Override
	public byte[] downloadExcel(String fileName, HttpServletResponse response) throws IOException {
		return getExcel(fileName, response);
	}

	private byte[] getExcel(String fileName, HttpServletResponse response) throws IOException {
		// cre un file con il path della directory dov'è presente il file
		File directory = new File(directoryPosition);
		// controllo che sia una directory
		if (!directory.isDirectory()) {
			throw new IOException(directory.toString() + " non è una directiory");
		}
		// imposto il contentType con formato excel
		response.setContentType("application/vnd.ms-excel");
		// imposto gli heander per il download automatico da browser
		response.setHeader("Content-Disposition", "attachment; filename = \"" + fileName + "\"");
		// creo un file con il path della directory e il fileName
		File file = new File(directory + File.separator + fileName);
		// se non esiste un file con quel nome lancio una IOException
		if (!file.exists()) {
			throw new IOException("file non trovato");
		}
		// ritorno il file convertito in un array di byte
		return IOUtils.toByteArray(new FileInputStream(file));
	}

}
