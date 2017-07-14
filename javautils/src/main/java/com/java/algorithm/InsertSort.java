package com.java.algorithm;

/**
 * insert sort
 * @author Tim
 *
 */
public class InsertSort {

	public static void sort(int[] numbers) {
		for (int i = 1; i < numbers.length; i++) {
			int currentNum = numbers[i];
			int j = i - 1;
			while(j >= 0 && numbers[j] > currentNum) {
				numbers[j+1] = numbers[j];
				j--;
			}
			numbers[j + 1] = currentNum; 
		}
	}
	
}
