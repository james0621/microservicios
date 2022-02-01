package co.com.pragma.infrastructure.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class ClientDto {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String lastName;

    @NotBlank
    private String docType;

    @NotNull
    private Long numDoc;

    @Min(value = 18, message = "El cliente debe ser mayor de edad")
    private Integer age;
    private String cityBirth;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date regisDate;

    //Relacion imagen mongo
    private String idImage;

    private ImageDto image;
}