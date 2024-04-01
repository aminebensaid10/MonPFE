package com.mypfeproject.pfe.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String ancienMdp;
    private String nouveauMdp;
    private String confirmMdp;

}
