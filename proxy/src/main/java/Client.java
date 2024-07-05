import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName Client
 * @Description 使用动态代理增强Cat的功能
 * @Author zihao.ni
 * @Date 2024/7/5 17:28
 * @Version 1.0
 **/
public class Client {
    public static void main(String[] args) {
        Animal proxyCat = (Animal) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{Animal.class},
                new InvocationHandler() {
                    Cat cat = new Cat();

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object invoke = method.invoke(cat, args);
                        if ("eat".equals(method.getName()) && method.getParameterCount() == 0) {
                            System.out.println("proxy eat");
                        }

                        if ("eat".equals(method.getName()) && method.getParameterCount() == 1) {
                            String food = (String) args[0];
                            System.out.println("proxy eat " + food);
                        }

                        if ("sleep".equals(method.getName())) {
                            System.out.println("proxy sleep");
                        }

                        return invoke;
                    }
                });

        proxyCat.eat("bird");
        proxyCat.sleep();
    }
}