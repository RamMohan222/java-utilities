package test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Log {
    String value() default "";
}


interface IBusinessDao {
    int caliculate1(int a, int b);

    int caliculate2(int a, int b);
}


class BusinessDaoImpl implements IBusinessDao {

    @Override
    @Log("prefix")
    public int caliculate1(int a, int b) {
        return a + b;
    }

    @Override
    public int caliculate2(int a, int b) {
        return a + b;
    }
}


class LogHandler implements InvocationHandler {

    private Object targetObject;

    public LogHandler(Object object) {
        this.targetObject = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;
        try {
            System.out.println("Proxy Class is called before method invocation");
            Method mainMethod = targetObject.getClass().getMethod(method.getName(), method.getParameterTypes());
            Log log = mainMethod.getAnnotation(Log.class);
            if (log != null) {
                System.out.println(log.value());
            }
            result = method.invoke(targetObject, args);
        } catch (Exception e) {
            System.out.println("Invocation Target Exception: " + e);
        } finally {
            System.out.println("Proxy Class is called after method invocation");
        }
        return result;
    }
}


class LogWrapper {
    public static Object proxy(Object obj, Class<?> type) {
        return Proxy.newProxyInstance(LogWrapper.class.getClassLoader(), new Class[] {type}, new LogHandler(obj));
    }
}


public class AnnotationHandlerWithProxy {

    public static void main(String[] args) {
        IBusinessDao dao = (IBusinessDao) LogWrapper.proxy(new BusinessDaoImpl(), IBusinessDao.class);
        int a = dao.caliculate1(1, 2);

        int b = dao.caliculate2(1, 2);
        System.out.println(a);
        System.out.println(b);
    }

}
