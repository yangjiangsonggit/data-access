package com.yjs.dataaccess.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.util.Strings;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

/**
 * create by jiangsongy on 2019/1/17
 */
public class StreamTest {

	private static final String content = "Spring Boot is designed to get you up and running as quickly as possible,"
			+ " with minimal upfront configuration of Spring. Spring Boot takes an opinionated view of building"
			+ " production-ready applications.";
	private static final List<Integer> intList = Lists.newArrayList(1,4,5,3,6,4);
	private static final List<String> strList = Lists.newArrayList("aa","bb","CC","dDd");
	private static final Map<String,Double> doubleMap = Maps.newHashMap();
	static {
		doubleMap.put("s1",333.44);
		doubleMap.put("s2",33.54);
		doubleMap.put("s3",73.45);
		doubleMap.put("s4",824.5);
	}



	public static void main(String[] args) {

		Map<String, String> collect = strList.stream().map(String::toUpperCase)
				.collect(Collectors.toMap(String::toLowerCase, Function.identity()));
		System.out.println("1:" + collect);

		Student gongsi = new Student(1, 18, "gongsi");
		Student yingying = new Student(1, 20, "gongsi");
		Student xiaoer = new Student(2, 12, "xiaoer");
		Student xiaoertuo = new Student(2, 20, "xiaoer");
		ArrayList<Student> students = Lists.newArrayList(gongsi, yingying, xiaoer, xiaoertuo);
		Map<String, List<Student>> collect1 = students.stream().collect(groupingBy(Student::getName));
		System.out.println("2:" + collect1.toString());

		Map<Integer, Integer> collect2 = intList.stream().distinct().collect(Collectors.toMap(i -> i, i -> i + 10));
		System.out.println("3:" + collect2);


		System.out.println("4:");
		Map<String, Long> wordCount = Stream.of(content)
				.flatMap(line -> Arrays.stream(line.trim().split("\\s")))
				.map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
				.filter(word -> word.length() > 0)
				.map(word -> new AbstractMap.SimpleEntry<>(word, 1))
				.collect(groupingBy(AbstractMap.SimpleEntry::getKey, counting()));
		wordCount.forEach((k, v) -> System.out.println(String.format("%s ==>> %d", k, v)));


		Integer reduce = strList.stream().reduce(10, (sum, str) -> sum + str.length(), (a, b) -> a + b);
		System.out.println("5:" + reduce);
		String s = strList.stream().reduce((a, b) -> a.length() > b.length() ? a : b).orElse(Strings.EMPTY);
		System.out.println("6:" + s);

		Map<Boolean, List<Student>> collect3 = students.stream().collect(partitioningBy(e -> e.getAge() > 18));
		System.out.println("7:" + collect3.toString());


		String s1 = strList.stream().max(Comparator.comparing(String::length)).orElse(Strings.EMPTY);
		System.out.println("8:" + s1);

		boolean aa = strList.stream().anyMatch(e -> e.equalsIgnoreCase("aa"));
		System.out.println("9:" + aa);

		//		Optional.ofNullable(strList).map(e -> StringUtil)


	}

	static class Student {

		private int id;
		private int age;
		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Student(int id, int age, String name) {
			this.id = id;
			this.age = age;
			this.name = name;
		}

		@Override
		public String toString() {
			return "Student{" + "id=" + id + ", age=" + age + ", name='" + name + '\'' + '}';
		}
	}
}
