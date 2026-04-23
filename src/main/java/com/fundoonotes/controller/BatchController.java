package com.fundoonotes.controller;

import com.fundoonotes.batch.ExcelReader;
import com.fundoonotes.dto.request.NoteExcelDTO;
import com.fundoonotes.service.impl.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/batch")
@RequiredArgsConstructor
public class BatchController {

    private final ExcelReader excelReader;
    private final BatchService batchService;

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              @RequestParam String email) {

        try {
            List<NoteExcelDTO> notes =
                    excelReader.readExcel(file.getInputStream());

            batchService.saveNotes(notes, email);

            return "Excel uploaded successfully";

        } catch (Exception e) {
            return "Failed to process file";
        }
    }
}