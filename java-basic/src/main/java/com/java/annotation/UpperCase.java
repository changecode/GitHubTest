package com.java.annotation;

@Compont(identifier="abc")
public class UpperCase {

	String toUpperCase(String str) {
		if(null != str) {
			return str.toUpperCase();
		}
		return null;
	}
}
