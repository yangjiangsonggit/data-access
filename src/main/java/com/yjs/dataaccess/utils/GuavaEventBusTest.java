package com.yjs.dataaccess.utils;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.util.Strings;

import java.time.Clock;
import java.time.Instant;
import java.util.HashMap;
import java.util.function.Function;

/**
 * create by jiangsongy on 2019/1/19
 */
public class GuavaEventBusTest {

	public static void main(String[] args) throws Exception{
		HashMap<Integer,Integer> data = new HashMap<>();
		data.put(1,2);
		data.put(2,2);
		data.put(3,2);
		data.put(4,1);
		data.put(5,5);
		System.out.println("1:" + MapUtils.invertMap(data));

		Function<Integer,Integer> addOne = n -> n + 1;

		System.out.println("2:" + addOne.apply(1));

		System.out.println("3:" + data.computeIfAbsent(10,s -> 6));

		System.out.println("instant now with utc:{}" + Instant.now(Clock.systemUTC()));
		System.out.println("instant now default zone:{}" + Instant.now(Clock.systemDefaultZone()));
		System.out.println("instant now from currentTime Millis:{}" + Instant.ofEpochMilli(System.currentTimeMillis()));
		long date = System.currentTimeMillis();
		long date2 = date / 1000;
		System.out.println("instant now from currentTime Millis:{}" + Instant.ofEpochMilli(date));
		System.out.println("instant now from currentTime Millis to second:{}" + Instant.ofEpochSecond(date2));

		System.out.println("###");
		System.out.println("###");
		/**
		 * guava eventBus
		 */
		EventBus eventBus = new EventBus("test");
		EventListener listener1 = new EventListener();
		EventListener listener2 = new EventListener();
		EventListenerAnother listenerAnother = new EventListenerAnother();

		eventBus.register(listener1);
		eventBus.register(listener2);
		eventBus.register(listenerAnother);

		eventBus.post(new TestEvent("200"));
		eventBus.post(new TestEvent("300"));
		eventBus.post(new TestEvent("400"));

		System.out.println("LastMessage:"+listener1.getLastMessage());
		System.out.println("LastMessage:"+listener2.getLastMessage());

	}



	static class TestEvent {
		private final String message;

		public TestEvent(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}


	static class EventListener {

		private String lastMessage = Strings.EMPTY;

		@Subscribe
		@AllowConcurrentEvents
		public void listen(TestEvent event) {
			lastMessage = event.getMessage();
			System.out.println("listen Message:"+lastMessage);
		}

		public String getLastMessage() {
			return lastMessage;
		}
	}

	static class EventListenerAnother {

		private String lastMessage = Strings.EMPTY;

		@Subscribe
		@AllowConcurrentEvents
		public void listen(TestEvent event) {
			lastMessage = event.getMessage();
			System.out.println("listen Another Message:"+lastMessage);
		}

		public String getLastMessage() {
			return lastMessage;
		}
	}

}
