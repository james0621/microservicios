package co.com.pragma.domain;


import co.com.pragma.infrastructure.util.UtilImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image {

    private String id;
    private String type;
    private String name;
    private Long size;
    private String base64;
    private Long docClient;

    public Image(MultipartFile file, Long docClient) throws IOException {
        this.type = UtilImage.typeImage(file.getOriginalFilename());
        this.name = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        this.size = file.getSize();
        this.base64 = Base64.getEncoder().encodeToString(file.getBytes());
        this.docClient = docClient;
    }

}
