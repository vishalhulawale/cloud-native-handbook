import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class ListDemo {

    public static void main(String[] args) {

        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("Blue");
        colorList.add("Red");
        colorList.add("Green");
        colorList.add("Yellow");

        AtomicInteger count = new AtomicInteger(0);
        colorList.forEach(color->{
            System.out.println(color);
            count.getAndSet(count.get() + 1);
            // colorList.add("Purple"); // This would cause ConcurrentModificationException
            // colorList.remove("Red"); // This would also cause ConcurrentModificationException
        });

        Iterator<String> iterator = colorList.iterator();
        while (iterator.hasNext()) {
            String color = iterator.next();
            System.out.println(color);
            if (color.equals("Red")) {
                iterator.remove(); // Safe removal during iteration
            }
        }

        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>(colorList);
        cowList.parallelStream().forEach(color->{
            System.out.println(color);
            cowList.add("Purple"); // Safe to add during iteration
            cowList.remove("Green"); // Safe to remove during iteration
        });
    }
}
