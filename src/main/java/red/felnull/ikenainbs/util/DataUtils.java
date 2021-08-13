package red.felnull.ikenainbs.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataUtils {
    public static byte[] readAllByte(InputStream stream) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = stream.read(buffer);
            if (len < 0) {
                break;
            }
            bout.write(buffer, 0, len);
        }
        return bout.toByteArray();
    }
}
