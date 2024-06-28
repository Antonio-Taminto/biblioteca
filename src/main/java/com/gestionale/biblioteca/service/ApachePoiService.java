package com.gestionale.biblioteca.service;

import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

public interface ApachePoiService {
	
	void generateExcel() throws IOException;
	
	byte[] downloadExcel(String fileName,HttpServletResponse response) throws IOException;
}
