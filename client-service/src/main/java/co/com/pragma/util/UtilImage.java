package co.com.pragma.util;

import co.com.pragma.exception.ImageTypeIncompatibilityException;

public class UtilImage {
	
	//Valido el tipo de la imagen solo se permite .png y .jpeg
	public static void validateImageType(String name) {
		String subName = name.substring(name.indexOf(".") + 1, name.length());
		name = "image/" + subName;
		if(!name.equals("image/jpeg") && !name.equals("image/png") && !name.equals("image/jpg"))
			throw new ImageTypeIncompatibilityException();		
		
	}

}
