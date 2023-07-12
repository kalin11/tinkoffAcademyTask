import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Main {
    public static long byteCounter = 0;
    public static void main(String[] args) {
//        String a = "DbMG_38BBgaI0Kv6kzGK";
////        System.out.println(Arrays.toString(b));
//        PacketEncoder encoder = new PacketEncoder("DbMG_38BBgaI0Kv6kzGK");
//        System.out.println(encoder.getLength());
//        encoder.fillCRC();
//        System.out.println(Arrays.toString(ULEB128Util.encode(1)));
//        PacketEncoder encoder = new PacketEncoder("ELMG_38BAQEIU21hcnRIdWJr");
//        System.out.println("че хочет - ");
//        encoder.getBytes();
//        System.out.println(Arrays.toString("SmartHub".getBytes()));
//
//        SmartHub smartHub = new SmartHub();
//        smartHub.buildCmd();

//        PacketEncoder encoder = new PacketEncoder();
//        System.out.println(encoder.getByte("0d"));
//        System.out.println(Arrays.toString(ULEB128Util.encode(819)));
//        System.out.println(ULEB128Util.decode(ULEB128Util.encode(819)));
//        int[] bytes = new int[] {0xb3, 0x06, 0xff, 0x7f, 0x01, 0x06, 0x06, 0x88, 0xd0, 0xab,0xfa, 0x93, 0x31};
//        int[] bytes = new int[] {0xb3, 0x06, 0xff, 0x7f, 0x03, 0x06, 0x06, 0xa8, 0xd0, 0xab, 0x9c, 0x94,0x31};
//        int[] bytes = new int[] {0xb3, 0x06, 0xff, 0x7f, 0x04, 0x06, 0x06,  0x8c, 0xd1, 0xab, 0x9c, 0x94, 0x31};
//        int[] bytes = new int[] {0x0d, 0x06, 0xff, 0x7f, 0x05, 0x06, 0x06,  0xf0, 0xd1, 0xab, 0x9c, 0x94, 0x31, 0xa4};
//        System.out.println(bytes[0]);
//        int[] bytes = new int[] {0xb3};
//        System.out.println("1 - " + Integer.toHexString(CheckSumUtil.computeCheckSum(bytes)));
////        Integer.parseInt(Integer.toHexString(crc).substring(6, 8), 16)
//        int checkSum = CheckSumUtil.computeCheckSum(bytes);
//        System.out.println("11 - " + checkSum);
//        System.out.println(CheckSumUtil.validateCheckSum(bytes, checkSum));
//        System.out.println("validate check sum " + CheckSumUtil.validateCheckSum(bytes, CheckSumUtil.computeCheckSum(bytes)));

//        Hub hub = new Hub(819);
//        hub.putNewDevice(new Lamp("LAMP01", 3));

        // String url = args[0];  // URL of the server endpoint
        String requestBody = ""; // Request body parameters
        URL url;
        HttpURLConnection conn;
        DataOutputStream wr;
        int responseCode = 0;
        BufferedReader in;
        Hub hub = new Hub(ULEB128Util.decode(ULEB128Util.encode(Long.parseLong(args[1], 16)), 0));
        requestBody = hub.executeWHOISHERE();
        System.out.println(requestBody);
        try {
            url = new URL(args[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Enable output and input streams for writing and reading data
            conn.setDoOutput(true);
            conn.setDoInput(true);
            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(requestBody);
            wr.flush();

            // Get the response code
            responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response from the server
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            PacketEncoder encoder = new PacketEncoder(response.toString());
            encoder.parsePackets();


            // Print the response
            System.out.println("Response: " + response.toString());
            System.out.println(Arrays.toString(response.toString().getBytes()));

        }catch (IOException e) {
            System.exit(99);
        }
        if (responseCode != 200 && responseCode != 204) {
            System.exit(99);
        }
        else if (responseCode == 204) {
            System.exit(0);
        }
        else if (responseCode == 200) {
            while (responseCode != 204) {
                try {
                    url = new URL(args[0]);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    // Enable output and input streams for writing and reading data
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    wr = new DataOutputStream(conn.getOutputStream());
                    wr.writeBytes(requestBody);
                    wr.flush();

                    // Get the response code
                    responseCode = conn.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    // Read the response from the server
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    PacketEncoder encoder = new PacketEncoder(response.toString());
                    encoder.parsePackets();
                    // Print the response
                    Files.write(
                            Paths.get("/home/kalin/IdeaProjects/TinkoffSmartHome/src/main/java/wtf.txt"),
                            response.append("\n").toString().getBytes(),
                            StandardOpenOption.APPEND
                    );
                    System.out.println("Response: " + response.toString());
                    System.out.println(Arrays.toString(response.toString().getBytes()));
                }
                catch (IOException e) {
                    System.exit(99);
                }
            }
        }

//        try {
//            // Create URL object
//            URL obj = new URL(args[0]);
//
//            // Open connection
//            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//            // Set the request method to POST
//            con.setRequestMethod("POST");
//
//            // Set request headers
//            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//
//            // Enable output and input streams for writing and reading data
//            con.setDoOutput(true);
//            con.setDoInput(true);
//
//            // Write the request body to the connection
//            wr = new DataOutputStream(con.getOutputStream());
//            wr.writeBytes(requestBody);
//            wr.flush();
//
//            // Get the response code
//            responseCode = con.getResponseCode();
//            System.out.println("Response Code: " + responseCode);
//
//            // Read the response from the server
//            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//
//            // Print the response
//            System.out.println("Response: " + response.toString());
//            System.out.println(Arrays.toString(response.toString().getBytes()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

abstract class Device {
    private final String name;
    private final long address;
    private long serial;
    private final byte dev_type;
    private long lastDeviceAddress;

    Device(String name, long address, byte dev_type) {
        this.name = name;
        this.address = address;
        this.dev_type = dev_type;
    }

    public long getLastDeviceAddress() {
        return lastDeviceAddress;
    }

    public void setLastDeviceAddress(long lastDeviceAddress) {
        this.lastDeviceAddress = lastDeviceAddress;
    }

    public String getName() {
        return name;
    }

    public long getAddress() {
        return address;
    }


    public long getSerial() {
        return serial;
    }

    public void setSerial(long serial) {
        this.serial = serial;
    }

    public byte getDev_type() {
        return dev_type;
    }

}

class Hub extends Device {
    private Map<String, Device> nameDevice;
    public Hub (long address) {
        super("SmartHub", address, (byte) 0x01);
        super.setSerial(1);
        nameDevice = new HashMap<>();
    }

    public void putNewDevice(Device device) {
        nameDevice.put(device.getName(), device);
    }

    public String executeWHOISHERE() {
        String ans = "";
        String name = super.getName();
        List<Integer> list = new ArrayList<>();
        int[] src = ULEB128Util.encode(super.getAddress());
        for (int s : src) {
            list.add((int) s);
        }
        int[] dst = ULEB128Util.encode(16383);
        for (int s : dst) {
            list.add((int) s);
        }
        int[] serial = ULEB128Util.encode(super.getSerial());
        super.setSerial(super.getSerial() + 1);
        for (int s : serial) {
            list.add((int) s);
        }
        int dev_type = super.getDev_type();
        list.add((int) dev_type);
        int cmd = 1;
        list.add(cmd);
        byte[] cmd_body = new byte[1 + name.length()];
        byte[] bytes = name.getBytes();
        cmd_body[0] = (byte) name.length();
        list.add(name.length());
        for (int i = 1; i < cmd_body.length; i++) {
            cmd_body[i] = bytes[i - 1];
            System.out.print(Integer.toHexString(bytes[i - 1]) + " ");
            list.add((int) bytes[i - 1]);
        }
        int[] payload = list.stream().mapToInt(i -> i).toArray();
        int checkSum = CheckSumUtil.computeCheckSum(payload);
        if (CheckSumUtil.validateCheckSum(payload, checkSum)) {
            list.add(0, list.size());
            list.add(checkSum);
            System.out.println();
            System.out.println("checksum " + Integer.toHexString(checkSum));
            System.out.println();
            System.out.println(list);
            byte[] x = new byte[list.size()];
            for (int i = 0; i < list.size(); i++) {
                x[i] = (byte) (list.get(i) & 0x00ff);
            }
            ans = new String(Base64.getUrlEncoder().withoutPadding().encode(x));
        }
        return ans;
    }


}

class EnvSensor extends Device {
    private byte sensors;
    private byte op;

    public EnvSensor (String name,long address) {
        super(name, address, (byte) 0x02);
        super.setSerial(1);
    }
}

class Switch extends Device {
    private byte state;
    private List<String> devicesNames;
    public Switch (String name, long address) {
        super(name, address, (byte) 0x03);
        devicesNames = new ArrayList<>();
    }
}

class Lamp extends Device {
    private byte state;
    public Lamp(String name, long address) {
        super(name, address, (byte) 0x04);
    }

    public void setState(byte state) {
        this.state = state;
    }

    public byte getState() {
        return state;
    }


}

class Socket extends Device {
    private byte state;

    public Socket (String name, long address) {
        super(name, address, (byte) 0x05);
    }

    public void setState(byte state) {
        this.state = state;
    }

    public byte getState() {
        return state;
    }



}

//class SmartHub {
//    private final String name = "SmartHub";
//
//    public void buildCmd() {
//        List<Integer> list = new ArrayList<>();
//        int length = 2;
//        Integer[] src = ULEB128Util.encode(819);
//        for (int s : src) {
//            list.add((int) s);
//        }
//        length += src.length;
//        Integer[] dst = ULEB128Util.encode(16383);
//        for (int s : dst) {
//            list.add((int) s);
//        }
//        length += dst.length;
//        Integer[] serial = ULEB128Util.encode(1);
//        for (int s : serial) {
//            list.add((int) s);
//        }
//        length += serial.length;
//        int dev_type = 1;
//        list.add((int) dev_type);
//        length += 1;
//        int cmd = 1;
//        list.add((int) cmd);
//        length += 1;
//        byte[] cmd_body = new byte[1 + name.length()];
//        byte[] bytes = name.getBytes();
//        cmd_body[0] = (byte) name.length();
//        list.add(name.length());
//        for (int i = 1; i < cmd_body.length; i++) {
//            cmd_body[i] = bytes[i - 1];
//            System.out.print(Integer.toHexString(bytes[i - 1]) + " ");
//            list.add((int) bytes[i - 1]);
//        }
//        length += 1 + name.length();
//        Integer[] payload = list.toArray(new Integer[0]);
//        int checkSum = CheckSumUtil.computeCheckSum(payload);
//        if (CheckSumUtil.validateCheckSum(payload, checkSum)) {
//            list.add(0, list.size());
//            list.add(checkSum);
//            System.out.println();
//            System.out.println("checksum " + Integer.toHexString(checkSum));
//            System.out.println();
//            System.out.println(list);
//            byte[] x = new byte[list.size()];
//            for (int i = 0; i < list.size(); i++) {
//                x[i] = (byte) (list.get(i) & 0xff);
//            }
//            System.out.println(new String(Base64.getUrlEncoder().withoutPadding().encode(x)));
//        }
//
//    }
//
//}

class PacketEncoder {
    private String stringData;
    private Integer[] bytes;

    private int length;
    private boolean isValidCheckSum;
    private int packetCRC8;

    public PacketEncoder() {};
    public PacketEncoder (String data) {
        this.stringData = data;
        byte[] temp = Base64.getUrlDecoder().decode(stringData.getBytes());
        bytes = new Integer[temp.length];
        for (int i = 0; i < temp.length; i++) {
            bytes[i] = temp[i] & 0x00ff;
        }
    }

    public void getDeviceType(int[] b) {
        length = b[0];
        int packetOffset = 1;
        ULEB128Util.bytesCounter = 0;
        long src = ULEB128Util.decode(b, packetOffset);
        packetOffset += ULEB128Util.bytesCounter;
        ULEB128Util.bytesCounter = 0;
        long dst = ULEB128Util.decode(b, packetOffset);
        packetOffset += ULEB128Util.bytesCounter;
        ULEB128Util.bytesCounter = 0;
        long serial = ULEB128Util.decode(b, packetOffset++);
        int dev_type = b[packetOffset++];
        System.out.println(dev_type);
//        try {
//            if (dev_type == 3 || dev_type == 4) {
//                byte data[] = String.valueOf(dev_type).getBytes();
//                Path file = Paths.get("/home/kalin/IdeaProjects/TinkoffSmartHome/src/main/java/wtf.txt");
//                Files.write(
//                        Paths.get("/home/kalin/IdeaProjects/TinkoffSmartHome/src/main/java/wtf.txt"),
//                        String.valueOf(dev_type + "\n").getBytes(),
//                        StandardOpenOption.APPEND
//                );
//
//            }
//            else {
//                byte data[] = String.valueOf(dev_type).getBytes();
//                Path file = Paths.get("/home/kalin/IdeaProjects/TinkoffSmartHome/src/main/java/wtf.txt");
//                Files.write(
//                        Paths.get("/home/kalin/IdeaProjects/TinkoffSmartHome/src/main/java/wtf.txt"),
//                        "Timer\n".getBytes(),
//                        StandardOpenOption.APPEND
//                );
//            }
//        }catch (IOException e) {
//
//        }
        int cmd = b[packetOffset++];
//        System.out.println("src - " + src);
//        System.out.println("dst - " + dst);
//        System.out.println("serial - " + serial);
//        System.out.println("dev_type - " + dev_type);
//        System.out.println("cmd - " + cmd);
    }

    public void parsePackets() {
        int counter = 0;
        while (counter < bytes.length) {
            int length = bytes[counter];
            int[] b = new int[length + 2];
            int[] checkSum = new int[length];
            for (int i = 0; i < length + 2; i++) {
                b[i] = bytes[counter] & 0x00ff;
                if (i > 0 && i < length + 1) {
                    checkSum[i - 1] = bytes[counter];
                }
                counter++;
            }
            int expectedSum = b[b.length - 1];
            int crc8 = CheckSumUtil.computeCheckSum(checkSum);
            if (expectedSum == crc8) {
                System.out.println("ok");
                getDeviceType(b);
            }
        }
    }

    public void getBytes() {
        System.out.println(Arrays.toString(bytes));
    }

    public void fillCRC() {
        int[] payload = new int[bytes.length - 2];
        System.arraycopy(bytes, 1, payload, 0, bytes.length - 2);
        int packetCRC8 = bytes[bytes.length - 1];
        int expectedCRC8 = CheckSumUtil.computeCheckSum(payload);
        isValidCheckSum = (packetCRC8 == expectedCRC8);
    }

    public void fillPayload() {

    }

    public void setData(String data) {
        this.stringData = data;
    }

    public String getData() {
        return stringData;
    }

    public int getLength() {
        if (length == 0) {
            length = bytes[0];
        }
        return length;
    }

    public int getPacketCRC8() {
        return packetCRC8;
    }

    public boolean isValidCheckSum() {
        return isValidCheckSum;
    }
}
// пытаемся ответить на комманду
class ReceivedCmdHandler {
    private static final byte SMART_HUB = 0x01;
    private static final byte ENV_SENSOR = 0x02;
    private static final byte SWITCH = 0x03;
    private static final byte LAMP = 0x04;
    private static final byte SOCKET = 0x05;
    private static final byte CLOCK = 0x06;
    private static final byte WHO_IS_HERE = 0x01;
    private static final byte I_AM_HERE = 0x02;
    private static final byte GET_STATUS = 0x03;
    private static final byte STATUS = 0x04;
    private static final byte SET_STATUS = 0x05;
    private static final byte TICK = 0x06;

    private long serial = 1L;
    public static void getCmdBody(byte devType, byte cmd) {
        switch (devType) {
            case ENV_SENSOR -> {
                switch (cmd) {
                    case WHO_IS_HERE -> {
                        // length - получим
                        // src - аргумент командной строки
                        // dst - 16383 aka 0x3FFF
                        // serial = serial++;
                        // dev_type - с константы взять
                        // cmd - I AM HERE
                        // cmd_body - string hub name, dev_props empty
                        // crc8 контрольная сумма пакета
                        break;
                    }
                    case I_AM_HERE -> {
                        // как-то запомнить соседей
                        // информация о устройстве в dev_props
                        break;
                    }
                    case STATUS -> {
                        // как-то запомнить статусы устройств
                        break;
                    }
                }
                break;
            }
            case SWITCH -> {
                break;
            }
            case LAMP -> {
                break;
            }
            case SOCKET -> {
                break;
            }
            case CLOCK -> {
                break;
            }

        }
    }
}

class CmdSender {

}

class ULEB128Util {
    private final static int BITS_LONG = 64;
    private final static int MASK_DATA = 0x7f;
    private final static int MASK_CONTINUE = 0x80;

    public static long bytesCounter = 0;

    public static int[] encode(long value) {
        List<Byte> bytes = new ArrayList<>();
        do {
            byte b = (byte) (value & MASK_DATA);
            value >>= 7;
            if (value != 0) {
                b |= MASK_CONTINUE;
            }
            bytes.add(b);
        } while (value != 0);

        int[] ret = new int[bytes.size()];
        for (int i = 0; i < bytes.size(); i++) {
            ret[i] = bytes.get(i) & 0x00ff;
        }
        return ret;
    }
    public static long decode(int[] bytes, int pos) {
        long value = 0;
        int i = pos;
        int shift = 0;
        while (true) {
            int b = bytes[i++];
            bytesCounter += 1;
            value |= (long) (b & 0x7f) << shift;
            if ((b & 0x80) == 0) break;
            shift+=7;
        }
        return value;
    }
}

class CheckSumUtil {
    private static final byte generator = 0x1D;
    public static int computeCheckSum(int[] bytes) {
//        System.out.println("start point " + Arrays.toString(bytes));
        byte crc = 0;
        for (int b : bytes) {
            crc ^= b;
            for (int i = 0; i < 8; i++) {
                if ((crc & 0x80) != 0) {
                    crc = (byte) ((crc << 1) ^ generator);
                }
                else {
                    crc <<= 1;
                }
            }
        }
        return crc & 0x00ff;
    }

    public static boolean validateCheckSum(int[] bytes, int crc) {
        int[] newBytes = new int[bytes.length + 1];
        System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
        newBytes[bytes.length] = crc;
        return computeCheckSum(newBytes) == 0;
     }
}
