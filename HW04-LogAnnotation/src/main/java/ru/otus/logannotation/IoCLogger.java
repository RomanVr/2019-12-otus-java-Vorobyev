package ru.otus.logannotation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class IoCLogger {
    static ILogger createLog() {
        InvocationHandler handler = new LoggerHandler(new TestLogging());
        return (ILogger) Proxy.newProxyInstance(IoCLogger.class.getClassLoader(), new Class<?>[]{ILogger.class}, handler);
    }

    private static class LoggerHandler implements InvocationHandler {
        private final TestLogging logger;

        public LoggerHandler(TestLogging logger) {
            this.logger = logger;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            /*System.out.println("method = " + method + ", args = " + Arrays.deepToString(args));
            System.out.println("Annotation present - " + method.isAnnotationPresent(Log.class));
            System.out.println("logger = " + this.logger.getClass() + "; method - " + method.getName());
            */
            for(Method m: logger.getClass().getDeclaredMethods()) {
                /*System.out.println("m = " + m.getName());*/
                if(method.getName() == m.getName() && m.isAnnotationPresent(Log.class)) {
                    String parameters = "";
                    for(Object arg: args) {
                        parameters += arg.toString() + " ";
                    }
                    System.out.println("executed method: " + m.getName() + ", param: " + parameters);
                }
            }
            return method.invoke(logger, args);
        }
    }
}
