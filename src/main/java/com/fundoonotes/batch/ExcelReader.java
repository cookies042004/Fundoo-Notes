package com.fundoonotes.batch;


import com.fundoonotes.dto.request.NoteExcelDTO;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExcelReader {

    public List<NoteExcelDTO> readExcel(InputStream inputStream) {

        List<NoteExcelDTO> notes = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // skip header

                NoteExcelDTO dto = new NoteExcelDTO();

                dto.setTitle(row.getCell(0).getStringCellValue());
                dto.setDescription(row.getCell(1).getStringCellValue());

                notes.add(dto);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error reading Excel file");
        }

        return notes;
    }
}