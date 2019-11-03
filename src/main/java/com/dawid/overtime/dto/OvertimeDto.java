package com.dawid.overtime.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OvertimeDto {
    private Long id;
    private String amount;
    private String overtimeDate;
    private String pickUpDate;
    private String remarks;
}
