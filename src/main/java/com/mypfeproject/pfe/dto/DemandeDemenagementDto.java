package com.mypfeproject.pfe.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class DemandeDemenagementDto {
    private String nouvelleAdresse;

    private MultipartFile justificatifAdressePrincipale;
}
