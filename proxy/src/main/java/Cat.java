/**
 * @ClassName Cat
 * @Description
 * @Author zihao.ni
 * @Date 2024/7/5 17:27
 * @Version 1.0
 **/
public class Cat implements Animal {
    @Override
    public void eat() {
        System.out.println("cat eat fish");
    }

    @Override
    public void eat(String food) {
        System.out.println("cat eat " + food);
    }


    @Override
    public void sleep() {
        System.out.println("cat sleep all day");
    }
}