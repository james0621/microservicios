package co.com.pragma.domain;

import co.com.pragma.infrastructure.rest.dto.ImageDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Client {

    private Long id;
    private String name;
    private String lastName;
    private String docType;
    private Long numDoc;
    private Integer age;
    private String cityBirth;
    private Date regisDate;
    private String idImage;
    private ImageDto image;

}
