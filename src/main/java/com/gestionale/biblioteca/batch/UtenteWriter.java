package com.gestionale.biblioteca.batch;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UtenteWriter implements ItemWriter<HSSFWorkbook> {
    private String directoryPosition;

    public UtenteWriter(String directoryPosition){
        this.directoryPosition = directoryPosition;
    }

    public String getDirectoryPosition() {
        return directoryPosition;
    }

    public void setDirectoryPosition(String directoryPosition) {
        this.directoryPosition = directoryPosition;
    }

    @Override
    public void write(Chunk<? extends HSSFWorkbook> chunk) throws Exception {
        for (HSSFWorkbook workbook : chunk) {
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
    }
}
