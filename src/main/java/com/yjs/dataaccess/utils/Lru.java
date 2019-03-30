package com.yjs.dataaccess.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * redis LRU
 * create by jiangsongy on 2019/3/18
 */
public class Lru {

	public static class LRU<k, v> extends LinkedHashMap<k, v> {

		private final int MAX_SIZE;

		public LRU(int capcity) {
			//排序模式accessOrder，该属性为boolean型变量，对于访问顺序，为true；对于插入顺序，则为false。
			super(16, 0.75f, true);
			this.MAX_SIZE = capcity;
		}

		@Override
		public boolean removeEldestEntry(Map.Entry<k, v> eldest) {
			if (size() > MAX_SIZE) {
				System.out.println("移除的元素为：" + eldest.getValue());
			}
			return size() > MAX_SIZE;
		}

		public static void main(String[] args) {
			Map<Integer, Integer> map = new LRU<>(5);
			for (int i = 1; i <= 11; i++) {
				map.put(i, i);
				System.out.println("cache的容量为：" + map.size());
				if (i == 4) {
					map.get(1);
				}
			}

			System.out.println("=-=-=-=-=-=-=-map元素:");
			map.entrySet().forEach(integerIntegerEntry -> System.out.println(integerIntegerEntry.getValue()));

		}

	}
}
