package com.yjs.dataaccess.utils;

import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cglib动态代理
 * create by jiangsongy on 2019/3/16
 */
public class CglibDynamicProxy {

	static class Test {
		void doTest1() {
			System.out.println("do test1");
		}

		void doTest2() {
			System.out.println("do test2");
		}
	}

	static class Filter implements CallbackFilter {

		@Override
		public int accept(Method method) {
			if ("doTest1".equalsIgnoreCase(method.getName())) {
				return 0;
			}
//			return 1;
			return 0;
		}
	}

	static class TestCglib implements MethodInterceptor{
		Object target;
		CallbackFilter callbackFilter;

		Object getProxyInstance(Object target,CallbackFilter callbackFilter) {
			this.target = target;
			this.callbackFilter = callbackFilter;
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(this.target.getClass());
			enhancer.setCallback(this);
			enhancer.setCallbackFilter(this.callbackFilter);
			return enhancer.create();
		}


		@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			System.out.println("原来的方法:" + method.getName());
			System.out.println("do 功能增强..");
			methodProxy.invokeSuper(o, objects);
			return null;
		}
	}

	public static void main(String[] args) {

		Test proxyInstance = (Test) new TestCglib().getProxyInstance(new Test(), new Filter());
		proxyInstance.doTest1();
		proxyInstance.doTest2();

	}


}
