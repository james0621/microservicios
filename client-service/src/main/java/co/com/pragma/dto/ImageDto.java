package co.com.pragma.dto;

import co.com.pragma.util.UtilImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

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
		UtilImage.validateImageType(file.getContentType());
		this.type = file.getContentType();
		this.name = file.getOriginalFilename();
		this.size = file.getSize();
		this.base64 = Base64.getEncoder().encodeToString(file.getBytes());
		this.docClient = docClient;
	}
	

}
