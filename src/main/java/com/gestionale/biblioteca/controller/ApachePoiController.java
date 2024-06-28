package com.gestionale.biblioteca.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gestionale.biblioteca.service.ApachePoiService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ApachePoiController {
	
	@Autowired
	private ApachePoiService apachePoiService;
	
	@PostMapping
	public ResponseEntity<Void> createExcel() throws IOException{
		apachePoiService.generateExcel();
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<byte[]> getExcel(@RequestParam String fileName,HttpServletResponse response) throws IOException{
		byte[] file = apachePoiService.downloadExcel(fileName,response);
		return ResponseEntity.ok(file);
	}
}
