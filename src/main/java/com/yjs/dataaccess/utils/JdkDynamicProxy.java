package com.yjs.dataaccess.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理
 * create by jiangsongy on 2019/3/16
 */
public class JdkDynamicProxy {

	static final String METHOD1 = "doSomething1";
	static final String METHOD2 = "doSomething2";
	static final String SEPARATOR = ",";


	interface DoSomething {
		void doSomething1();
		void doSomething2();
	}

	static class MyDoSomething implements DoSomething ,Test2{

		@Override
		public void doSomething1() {
			System.out.println("doing something1");
			System.out.println("finished something1");
		}

		@Override
		public void doSomething2() {
			System.out.println("doing something2");
			System.out.println("finished something2");
		}

		@Override
		public void test2() {
			System.out.println("test2");
		}
	}

	interface Test2 {
		void test2();
	}

	static class LoggerHelper {

		void logTimeStamp(String remark) {
			System.out.println(remark + " -> 当前时间:" + System.currentTimeMillis());
		}

		void logProxy(String proxyInfo) {
			System.out.println(proxyInfo);
		}
	}

	static class AddLogHandle implements InvocationHandler {

		Object target;
		LoggerHelper loggerHelper;

		AddLogHandle(Object target,LoggerHelper loggerHelper) {
			this.target = target;
			this.loggerHelper=loggerHelper;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Class<?> aClass = proxy.getClass();
			loggerHelper.logProxy(aClass.getName() + SEPARATOR + aClass.getSimpleName()
			+ SEPARATOR + aClass.getDeclaredMethods().toString());
			if (method.getName().equalsIgnoreCase(METHOD1)) {

				loggerHelper.logTimeStamp("方法1记录开始时间");
				method.invoke(target,args);
			} else if (method.getName().equalsIgnoreCase(METHOD2)) {

				method.invoke(target,args);
				loggerHelper.logTimeStamp("方法2记录结束时间");
			} else {
				handleDefault(method, args);
			}
			return null;
		}

		private void handleDefault(Method method, Object[] args)
				throws InvocationTargetException, IllegalAccessException {
			method.invoke(target,args);
			loggerHelper.logTimeStamp("默认实现");
		}
	}

	public static void main(String[] args) {
		LoggerHelper loggerHelper = new LoggerHelper();
		MyDoSomething myDoSomething = new MyDoSomething();
		AddLogHandle addLogHandle = new AddLogHandle(myDoSomething, loggerHelper);
		DoSomething proxy = (DoSomething)Proxy.newProxyInstance(myDoSomething.getClass().getClassLoader(),
				new Class[]{DoSomething.class,Test2.class},
				addLogHandle);
		proxy.doSomething1();
		proxy.doSomething2();

		System.out.println("---------------------");
		Test2 proxy1 = (Test2) proxy;
		proxy1.test2();


	}
}
