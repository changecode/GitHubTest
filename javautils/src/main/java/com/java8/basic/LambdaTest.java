package com.java8.basic;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * Tim
 */
public class LambdaTest {

	static List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	static List<String> names = Arrays.asList("a","h","g","u");
	
	/**
	 * lambda expression to the forEach method
	 * 
	 * 方法引用有很多种，它们的语法如下：
	 *	静态方法引用：ClassName::methodName
	 *  实例上的实例方法引用：instanceReference::methodName
	 *	超类上的实例方法引用：super::methodName
	 *	类型上的实例方法引用：ClassName::methodName
	 *	构造方法引用：Class::new
	 *	数组构造方法引用：TypeName[]::new
	 * 
	 */
	public static void filter(){
		numbers.stream()
		  .filter(e -> e % 2 == 0)
//		  .forEach(e -> System.out.println(e));
		  .forEach(System.out::println);
	}
	
	/**
	 * Simple code for a simple task
	 */
	public static void range() {
		System.out.print("Get set...");
		  IntStream.range(1, 4)
		    .forEach(i -> System.out.print(i + "..."));
	}
	
	/**
	 * Using a lambda parameter in an inner class
	 */
	public static void executor() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
	    IntStream.range(0, 5)
	       .forEach(i -> 
	         executorService.submit(new Runnable() {
	           public void run() {
	             System.out.println("Running task " + i); 
	           }
	         }));
	     executorService.shutdown();
	}
	
	/**
	 * Iterating with limit
	 */
	public static void intStream() {
		IntStream.iterate(1, e -> e + 3)
		  .limit(34)
		  .sum();
	}
	
	/**
	 * A functional-style alternative
	 */
	public static void sum() {
		System.out.println(
				 numbers.stream()
				   .filter(e -> e  > 3)
				   .filter(e -> e % 2 == 0)
				   .filter(e -> e < 8)
				   .mapToInt(e -> e * 2)
				   .sum());
	}
	
	/**
	 * Function composition in Java 8
	 */
	public static void streammap() {
		List<String> names = Arrays.asList("Jack", "Jill", "Nate", "Kara", "Kim", "Jullie", "Paul", "Peter");
		System.out.println(
				 names.stream()
				      .filter(name -> name.startsWith("J"))
				      .filter(name -> name.length() > 3)
				      .map(name -> name.toUpperCase())
				      .collect(Collectors.joining(", ")));
	}
	
	public static void main(String[] args) {
		sum();
	}
}
