package com.fundoonotes.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    // [Prajwal]:UC4:Standardized API Response wrapper
    private String message;
    private Object data;
}
