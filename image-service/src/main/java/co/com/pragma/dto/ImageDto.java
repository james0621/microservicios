package co.com.pragma.dto;

import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import co.com.pragma.util.UtilImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
	

	private String id;
	private String type;
	private String name;
	private Long size;
	private String base64;
	private Long docClient;
	
	public ImageDto(MultipartFile file, Long docClient) throws IOException {
		this.type = UtilImage.typeImage(file.getOriginalFilename());
		this.name = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		this.size = file.getSize();
		this.base64 = Base64.getEncoder().encodeToString(file.getBytes());
		this.docClient = docClient;
	}

}