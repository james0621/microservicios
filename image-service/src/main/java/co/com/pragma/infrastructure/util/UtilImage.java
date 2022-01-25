package co.com.pragma.infrastructure.util;

import co.com.pragma.infrastructure.exception.ImageTypeIncompatibilityException;

public class UtilImage {
	
	//Valido el tipo de la imagen solo se permite .png y .jpeg
	public static void validateImageType(String type) {
		if(!type.equals("image/jpeg") && !type.equals("image/png") && !type.equals("image/jpg"))
			throw new ImageTypeIncompatibilityException();
		
	}

	public static String typeImage(String type){
		String subType = type.substring(type.indexOf(".") + 1, type.length());
		type = "image/" + subType;

		return type;
	}

}
