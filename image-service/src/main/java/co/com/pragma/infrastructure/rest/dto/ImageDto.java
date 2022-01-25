package co.com.pragma.infrastructure.rest.dto;

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

}
