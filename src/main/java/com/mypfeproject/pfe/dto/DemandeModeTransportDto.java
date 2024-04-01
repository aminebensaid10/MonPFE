package com.mypfeproject.pfe.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class DemandeModeTransportDto {
    private String typeTransport;

    private MultipartFile justificatifModeTransport;
}
