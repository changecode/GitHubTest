package com.java.annotation;

public class AnnotationMain {

	public static void main(String[] args) throws Exception{
		
		Class<?> clzss = Class.forName("com.java.annotation.UpperCase");
		boolean annotationBoolean = clzss.isAnnotationPresent(Compont.class);
		if(annotationBoolean) {
			Compont compont = (Compont)clzss.getAnnotation(Compont.class);
			String annotationResult = compont.identifier();
			System.out.println(String.format("compont annotaion return result: %s", annotationResult));
		}
	}
}
