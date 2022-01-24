package co.com.pragma.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document("images")
public class Image {
	
	@Id
	private String id;
	private String type;
	private String name;
	private Long size;
	private String base64;
	private Long docClient;
	

}
