package com.java.annotation;

@Compont(identifier="123")
public class UpperCase {

	String toUpperCase(String str) {
		if(null != str) {
			return str.toUpperCase();
		}
		return null;
	}
}
