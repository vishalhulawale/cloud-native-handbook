import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileInputStreamDemo {

    public static void main(String[] args) {

        String filePath = "example.txt";
        String data = "My name is John Doe.";

        // Write to a file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            byte[] bytes = data.getBytes();
            fos.write(bytes);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        // Read from a file
        try (FileInputStream fis = new FileInputStream(filePath)){
            int byteData;

            while ((byteData = fis.read()) != -1) {
                System.out.print((char) byteData);
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }

}
