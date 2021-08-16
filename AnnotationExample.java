import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Apply {
    int value() default 0;

    String name() default "apply";
}


class Math {

    int add(int a, int b) {
        return a + b;
    }

    @Apply(value = 10, name = "sub")
    int sub(int a, int b) {
        return a - b;
    }
}

public class AnnotationExample {
    public static void main(String[] args) {
        Math m = new Math();
        for (var method : m.getClass().getDeclaredMethods()) {
            Apply a = method.getAnnotation(Apply.class);
            if (a != null) {
                System.out.println(a.name());
                System.out.println(a.value());
            }
        }
    }
}
