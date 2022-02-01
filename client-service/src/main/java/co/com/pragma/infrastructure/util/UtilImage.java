package co.com.pragma.infrastructure.util;

import co.com.pragma.infrastructure.exception.ImageTypeIncompatibilityException;

public class UtilImage {

	private UtilImage(){}
	
	//Valido el tipo de la imagen solo se permite .png y .jpeg
	public static void validateImageType(String name) {
		name = typeImage(name);
		if(!name.equals("image/jpeg") && !name.equals("image/png") && !name.equals("image/jpg"))
			throw new ImageTypeIncompatibilityException();		
		
	}

	public static String typeImage(String type){
		String subType = type.substring(type.indexOf(".") + 1, type.length());
		type = "image/" + subType;

		return type;
	}

}
