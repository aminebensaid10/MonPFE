package com.mypfeproject.pfe.dto;

import com.mypfeproject.pfe.entities.SituationFamiliale;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class DemandeSituationFamilialeDTO {
    private SituationFamiliale nouvelleSituation;

    private MultipartFile justificatifSituationFamiliale;
}
