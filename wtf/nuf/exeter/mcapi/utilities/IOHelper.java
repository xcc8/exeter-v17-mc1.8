package wtf.nuf.exeter.mcapi.utilities;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class IOHelper {
    public static void copyFile(File fileSource, File fileDestination) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(fileSource);
        FileOutputStream fileOutputStream = new FileOutputStream(fileDestination);
        byte[] buffer = new byte['?'];
        for (; ; ) {
            int read = fileInputStream.read(buffer);
            if (read < 0) {
                break;
            }
            fileOutputStream.write(buffer, 0, read);
        }
        fileOutputStream.flush();
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static String readString(String url) throws IOException {
        String result;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        result = bufferedReader.readLine();
        bufferedReader.close();
        return result;
    }

    public static List<String> readStrings(String url) throws IOException {
        List<String> results = new CopyOnWriteArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            results.add(line);
        }
        bufferedReader.close();
        return results;
    }
}
