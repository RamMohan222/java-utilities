import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyObjectExample {

    public static void main(String[] args) {

        IMath math = (IMath) Proxy.newProxyInstance(IMath.class.getClassLoader(), new Class[] {IMath.class},
                new MyProxyHandler(new Caliculator()));

        math.add(1, 2);
    }
}


interface IMath {
    int add(int a, int b);

    int sub(int a, int b);
}


class Caliculator implements IMath {

    @Override
    public int add(int a, int b) {
        System.out.println("add called");
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        System.out.println("sub called");
        return a - b;
    }

}


class MyProxyHandler implements InvocationHandler {

    private Object obj;

    public MyProxyHandler(Object o) {
        obj = o;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        Object result = null;
        try {
            System.out.println("Proxy Class is called before method invocation");
            result = m.invoke(obj, args);
        } catch (Exception e) {
            System.out.println("Invocation Target Exception: " + e);
        } finally {
            System.out.println("Proxy Class is called after method invocation");
        }
        return result;
    }
}
