package com.vatek.project.entity;

import com.vatek.project.entity.common.CommonEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.File;

@Entity(name = "information_form")
@Getter
@Setter
public class InformationFormEntity extends CommonEntity {
    @Column
    private String fullName;

    @Column
    private String email;

    @Column
    private String phoneNumber;

    @Column
    private Long typeId;

    @Column
    private byte[] file;

    @Column
    private Long formTypeId;
}
