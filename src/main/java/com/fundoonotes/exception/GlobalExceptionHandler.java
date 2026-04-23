package com.fundoonotes.exception;

import com.fundoonotes.dto.response.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // UC4:Catching custom user exceptions
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseDTO> handleUserException(UserException exception) {
        ResponseDTO responseDTO = new ResponseDTO(exception.getMessage(), null);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    // UC8:Catching custom Note exceptions
    @ExceptionHandler(NoteException.class)
    public ResponseEntity<ResponseDTO> handleNoteException(NoteException exception) {
        ResponseDTO responseDTO = new ResponseDTO(exception.getMessage(), null);
        // Using NOT_FOUND if operations like delete/edit fail later
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND); 
    }

    // UC4:Catching validation exceptions (DTO constraints)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<ObjectError> errorList = exception.getBindingResult().getAllErrors();
        List<String> errMsg = errorList.stream()
                .map(objErr -> objErr.getDefaultMessage())
                .collect(Collectors.toList());
        ResponseDTO responseDTO = new ResponseDTO("Exception while processing REST Request", errMsg);
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }
}
