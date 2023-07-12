import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ULEB {
    private final static int BITS_LONG = 64;
    private final static int MASK_DATA = 0x7f;
    private final static int MASK_CONTINUE = 0x80;
    public static void main(String[] args) throws IOException {
        long value = 819;
        ArrayList<Byte> bytes = new ArrayList<>();
        do {
            byte b = (byte) (value & MASK_DATA);
            value >>= 7;
            if (value != 0) {
                b |= MASK_CONTINUE;
            }
            bytes.add(b);
        } while (value != 0);

        byte[] ret = new byte[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            ret[i] = bytes.get(i);
        }
        System.out.println(Arrays.toString(ret));
        System.out.println(decode(ret));

    }

    private static long decode(byte[] bytes) throws IOException {
        long value = 0;
        int i = 0;
        int shift = 0;
        while (true) {
            byte b = bytes[i++];
            value |= (long) (b & 0x7f) << shift;
            if ((b & 0x80) == 0) break;
            shift+=7;
        }
        return value;
    }
}
