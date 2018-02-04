package com.oracle.manager.pojo;

public class TestG {
	public static void main(String[] args) {
		int[] num = new int[] { 1, 4, 8, 9, 15, 18, 22, 45, 63 };
		int start = 0;
		int end = num.length;

		int find = 15;

		while (start <= end) {
			int mid = (start + end) / 2;
			if (num[mid] == find) {
				System.out.println("在索引为" + mid + "这个位置");
				break;
			} else if (num[mid] < find) {
				start = num[mid] + 1;
			} else {
				end = num[mid] - 1;
			}
		}

		// int[] bubbing = new int[] { 13, 74, 81, 29, 15, 18, 232, 45, 63 };
		int[] bubbing = new int[] { 299, 275, 246, 244, 115, 113, 98, 45, 41, 22 };

		for (int i = 0; i < bubbing.length - 1; i++) {

			for (int j = 0; j < bubbing.length -i- 1; j++) {
				int temp = bubbing[j];
				if (bubbing[j] > bubbing[j + 1]) {
					bubbing[j] = bubbing[j + 1];
					bubbing[j + 1] = temp;
				}
			}
		}
		for (int i = 0; i < bubbing.length; i++) {
			System.out.println(bubbing[i]);

		}
		System.out.println("===================");
		/*
		 * 冒泡排序 int[] num = {12,-5,36,78,90,20,15}
		 */
		int[] num1 = { 12, -5, 36, 78, 40, 20, 15 };
		for (int i = 0; i < num1.length - 1; i++) {
			for (int j = 0; j < num1.length - 1 - i; j++) {

				if (num1[j] > num1[j + 1]) {
					int temp = num1[j];
					num1[j] = num1[j + 1];
					num1[j + 1] = temp;
				}
			}
		}
		for (int i = 0; i < num1.length; i++) {
			System.out.print(num1[i] + " ");
		}

	}
}
