package asia.vatek.project.entity;

import asia.vatek.project.entity.enums.FormTypeEnum;
import asia.vatek.project.entity.commons.CommonEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @Enumerated(value = EnumType.STRING)
    private FormTypeEnum formType;
}
