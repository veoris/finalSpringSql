package study;


import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.setPorsh(4);
        engine.setVolume(2);

        Car car = new Car("bmv", engine);
        System.out.println(car);
        System.out.println(engine);

        engine.setVolume(100);
        System.out.println(engine);
        System.out.println(car);

        car.getEngine().setVolume(500);
        System.out.println(car);
        System.out.println(engine);
        //float f = 1.0;
        //float f1 = 6345.56_f;
        System.out.println(car.getEngine());
        before();
        System.out.println();
    }
    public static void before() {
        Set set = new TreeSet();
        set.add("2");
        set.add(3);
        set.add("1");
        Iterator it = set.iterator();
        while (it.hasNext())
            System.out.print(it.next() + " ");
    }
}

class Engine {
    private int porsh;
    private int volume;

    public int getPorsh() {
        return porsh;
    }

    public void setPorsh(int porsh) {
        this.porsh = porsh;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Engine{" +
                "porsh=" + porsh +
                ", volume=" + volume +
                '}';
    }
}

final class Car {
    private final String name;
    private final Engine engine;

    public Car(String name, Engine engine) {
        this.name = name;
        Engine engine1 = new Engine();
        engine1.setVolume(engine.getVolume());
        engine1.setPorsh(engine.getPorsh());
        this.engine = engine1;

    }

    public String getName() {
        return name;
    }

    public Engine getEngine() {
        Engine engine2 = new Engine();
        engine2.setVolume(engine.getVolume());
        engine2.setPorsh(engine.getPorsh());
        return engine2;
    }

    @Override
    public String toString() {
        return "Car name " + name +
                " with engine " + engine;
    }
}
