package com.vatek.project.readable.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFormReq {
    private String fullName;
    private String email;
    private String phoneNumber;
    private Long typeId;
    private MultipartFile file;
}
