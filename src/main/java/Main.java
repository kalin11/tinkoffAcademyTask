import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //todo доделать логику у EnvSensor
        //todo timer
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
        StringBuilder requestBody = new StringBuilder(); // Request body parameters
        URL url;
        HttpURLConnection conn;
        DataOutputStream wr;
        int responseCode = 0;
        BufferedReader in;
        Hub hub = new Hub(ULEB128Util.decode(ULEB128Util.encode(Long.parseLong(args[1], 16)), 0));
        System.out.println("hello - " + hub.executeIAMHERE());
        requestBody = new StringBuilder(hub.executeWHOISHERE());
        System.out.println(requestBody);
        boolean first = true;
        try {
            url = new URL(args[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // Enable output and input streams for writing and reading data
            conn.setDoOutput(true);
            conn.setDoInput(true);
            wr = new DataOutputStream(conn.getOutputStream());
            wr.writeBytes(requestBody.toString());
            wr.flush();

            // Get the response code
            responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read the response from the server
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine.replaceAll("\\s++", ""));
            }

            PacketEncoder encoder = new PacketEncoder();
            if (encoder.base64IsCorrect(response.toString())) {
                encoder.setHub(hub);
                encoder.setStringData(response.toString());
                requestBody = new StringBuilder(encoder.buildStringForServer());
                System.out.println(requestBody);

                // Print the response
                System.out.println("Response: " + response.toString());
                System.out.println(Arrays.toString(response.toString().getBytes()));
            }

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
                    if (requestBody.length() != 0) {
                        wr.writeBytes(requestBody.toString());
                        wr.flush();
                    }

                    // Get the response code
                    responseCode = conn.getResponseCode();
                    System.out.println("Response Code: " + responseCode);

                    // Read the response from the server
                    in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine.replaceAll("\\s++", ""));
                    }

                    PacketEncoder encoder = new PacketEncoder();
                    if (encoder.base64IsCorrect(response.toString())) {
                        encoder.setStringData(response.toString());
                        encoder.setHub(hub);
//                    PacketEncoder encoder = new PacketEncoder();
//                    if (first) {
//                        first = false;
//                        encoder = new PacketEncoder("JALwHQQCAghTRU5TT1IwMQMCDGQGTEFNUDAyD7AJBkxBTVAwM3A",hub);
//                    }
//                    else {
//                        encoder = new PacketEncoder("DALwHQQCBAKlAdSOBos", hub);
//                    }
                        System.out.println("Response: " + response.toString());
                        requestBody = new StringBuilder(encoder.buildStringForServer());
//                    StringBuilder s = new StringBuilder(requestBody);
//                    if (requestBody.length() != 0) {
//                        Files.write(
//                                Paths.get("/home/kalin/IdeaProjects/TinkoffSmartHome/src/main/java/testing.txt"),
//                                s.append("\n").append(response).append("\n").toString().getBytes(),
//                                StandardOpenOption.APPEND
//                        );
//                    }
                        // Print the response
                        System.out.println(Arrays.toString(response.toString().getBytes()));
                    }
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

class Trigger {
    private int op;
    private long value;
    private String name;

    public Trigger(int op, long value, String name) {
        this.op = op;
        this.value = value;
        this.name = name;
    }

    public int getOp() {
        return op;
    }

    public void setOp(byte op) {
        this.op = op;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

abstract class Device {
    private final String name;
    private final long src;
    private long serial;
    private final byte dev_type;
    private long lastDeviceAddress;
    private long timestamp;
    private int checkSum;

    private final int broadcastAddress = 16383;

    // EnvSensor
    private int sensors;
    private int op;
    private List<Trigger> list;
    private boolean hasTempDevice;
    private boolean hasWaterDevice;
    private boolean hasLuxDevice;
    private boolean hasAirDevice;

    private long tempBound;
    private long waterBound;
    private long luxBound;
    private long airBound;

    // Socket && Lamp && Switch
    private byte state;

    // Switch
    private List<String> devicesNames;

    Device(String name, long src, byte dev_type) {
        this.name = name;
        this.src = src;
        this.dev_type = dev_type;
    }

    public long getTempBound() {
        return tempBound;
    }

    public void setTempBound(long tempBound) {
        this.tempBound = tempBound;
    }

    public long getWaterBound() {
        return waterBound;
    }

    public void setWaterBound(long waterBound) {
        this.waterBound = waterBound;
    }

    public long getLuxBound() {
        return luxBound;
    }

    public void setLuxBound(long luxBound) {
        this.luxBound = luxBound;
    }

    public long getAirBound() {
        return airBound;
    }

    public void setAirBound(long airBound) {
        this.airBound = airBound;
    }

    public List<Trigger> getList() {
        return list;
    }

    public void setList(List<Trigger> list) {
        this.list = list;
    }

    public void setDevicesNames(List<String> devicesNames) {
        this.devicesNames = devicesNames;
    }

    public List<String> getDevicesNames() {
        if (this.devicesNames == null) {
            this.devicesNames = new ArrayList<>();
        }
        return this.devicesNames;
    }

    public boolean isHasTempDevice() {
        return hasTempDevice;
    }

    public void setHasTempDevice(boolean hasTempDevice) {
        this.hasTempDevice = hasTempDevice;
    }

    public boolean isHasWaterDevice() {
        return hasWaterDevice;
    }

    public void setHasWaterDevice(boolean hasWaterDevice) {
        this.hasWaterDevice = hasWaterDevice;
    }

    public boolean isHasLuxDevice() {
        return hasLuxDevice;
    }

    public void setHasLuxDevice(boolean hasLuxDevice) {
        this.hasLuxDevice = hasLuxDevice;
    }

    public boolean isHasAirDevice() {
        return hasAirDevice;
    }

    public void setHasAirDevice(boolean hasAirDevice) {
        this.hasAirDevice = hasAirDevice;
    }

    public int getBroadcastAddress() {
        return broadcastAddress;
    }

    public void add(String name) {
        this.devicesNames.add(name);
    }

    public void initList(List<String> list) {
        this.devicesNames = list;
    }

    public int getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(int checkSum) {
        this.checkSum = checkSum;
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

    public long getSrc() {
        return src;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getSensors() {
        return sensors;
    }

    public void setSensors(int sensors) {
        this.sensors = sensors;
    }

    public int getOp() {
        return op;
    }

    public void setOp(byte op) {
        this.op = op;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}

class Hub extends Device {
    private Map<Long, Device> srcDevice;
    private Map<String, Device> nameDevice;
    public Hub (long address) {
        super("HUB01", address, (byte) 0x01);
        super.setSerial(1);
        srcDevice = new HashMap<>();
        nameDevice = new HashMap<>();
    }

    public Map<String, Device> getNameDevice() {
        return this.nameDevice;
    }

    public Map<Long, Device> getSrcDevice() {
        return this.srcDevice;
    }

    public void putNewDeviceBySrc(Device device) {
        srcDevice.put(device.getSrc(), device);
    }

    public void putNewDeviceByName(Device device){
        nameDevice.put(device.getName(), device);
    }

    public Device getDevice(Long src) {
        return srcDevice.get(src);
    }

    public Device getDevice(String name) {
        return nameDevice.get(name);
    }

    public boolean contains(Long src) {
        return srcDevice.containsKey(src);
    }

    public boolean contains(String name) {
        return nameDevice.containsKey(name);
    }


    public String executeIAMHERE() {
        StringBuilder ans = new StringBuilder();
        String name = super.getName();
        List<Integer> list = new ArrayList<>();
        int[] src = ULEB128Util.encode(super.getSrc());
        for (int s : src) {
            list.add((int) s);
        }
        int[] dst = ULEB128Util.encode(super.getBroadcastAddress());
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
        int cmd = Commands.IAMHERE.getCommandNum();
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
            ans.append(new String(Base64.getUrlEncoder().withoutPadding().encode(x)));
        }
        return ans.toString();
    }

    public String executeWHOISHERE() {
        StringBuilder ans = new StringBuilder();
        String name = super.getName();
        List<Integer> list = new ArrayList<>();
        int[] src = ULEB128Util.encode(super.getSrc());
        for (int s : src) {
            list.add((int) s);
        }
        int[] dst = ULEB128Util.encode(super.getBroadcastAddress());
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
        int cmd = Commands.WHOISHERE.getCommandNum();
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
            ans.append(new String(Base64.getUrlEncoder().withoutPadding().encode(x)));
        }
        return ans.toString();
    }

    public String executeSETSTATUS(long dstValue, int state) {
        StringBuilder ans = new StringBuilder();
        String name = super.getName();
        List<Integer> list = new ArrayList<>();
        int[] src = ULEB128Util.encode(super.getSrc());
        for (int s : src) {
            list.add((int) s);
        }
        int[] dst = ULEB128Util.encode(dstValue);
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
        int cmd = Commands.SETSTATUS.getCommandNum();
        list.add(cmd);
        list.add(state);
//        byte[] cmd_body = new byte[1 + name.length()];
//        byte[] bytes = name.getBytes();
//        cmd_body[0] = (byte) name.length();
//        list.add(name.length());
//        for (int i = 1; i < cmd_body.length; i++) {
//            cmd_body[i] = bytes[i - 1];
//            System.out.print(Integer.toHexString(bytes[i - 1]) + " ");
//            list.add((int) bytes[i - 1]);
//        }
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
            ans.append(new String(Base64.getUrlEncoder().withoutPadding().encode(x)));
        }
        return ans.toString();
    }


}

class EnvSensor extends Device {
    public EnvSensor(String name, long address) {
        super(name, address, (byte) 0x02);
        if (super.getSerial() != 1) {
            super.setSerial(1);
        }
        super.setList(new ArrayList<>());
    }

    @Override
    public List<Trigger> getList() {
        return super.getList();
    }

    @Override
    public void setList(List<Trigger> list) {
        super.setList(list);
    }

    @Override
    public boolean isHasTempDevice() {
        return super.isHasTempDevice();
    }

    @Override
    public void setHasTempDevice(boolean hasTempDevice) {
        super.setHasTempDevice(hasTempDevice);
    }

    @Override
    public boolean isHasWaterDevice() {
        return super.isHasWaterDevice();
    }

    @Override
    public void setHasWaterDevice(boolean hasWaterDevice) {
        super.setHasWaterDevice(hasWaterDevice);
    }

    @Override
    public boolean isHasLuxDevice() {
        return super.isHasLuxDevice();
    }

    @Override
    public void setHasLuxDevice(boolean hasLuxDevice) {
        super.setHasLuxDevice(hasLuxDevice);
    }

    @Override
    public boolean isHasAirDevice() {
        return super.isHasAirDevice();
    }

    @Override
    public void setHasAirDevice(boolean hasAirDevice) {
        super.setHasAirDevice(hasAirDevice);
    }

    public int getSensors() {
        return super.getSensors();
    }

    public void setSensors(int sensors) {
        super.setSensors(sensors);
    }

    public int getOp() {
        return super.getOp();
    }

    public void setOp(byte op) {
        super.setOp(op);
    }

    public int getCheckSum() {
        return super.getCheckSum();
    }

    public void setCheckSum(int checkSum) {
        super.setCheckSum(checkSum);
    }

    @Override
    public long getLastDeviceAddress() {
        return super.getLastDeviceAddress();
    }

    @Override
    public void setLastDeviceAddress(long lastDeviceAddress) {
        super.setLastDeviceAddress(lastDeviceAddress);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public long getSrc() {
        return super.getSrc();
    }

    @Override
    public long getSerial() {
        return super.getSerial();
    }

    @Override
    public void setSerial(long serial) {
        super.setSerial(serial);
    }

    @Override
    public long getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public void setTimestamp(long timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public byte getDev_type() {
        return super.getDev_type();
    }
}

class Switch extends Device {
    public Switch (String name, long address) {
        super(name, address, (byte) 0x03);
    }

    @Override
    public List<String> getDevicesNames() {
        return super.getDevicesNames();
    }

    @Override
    public int getCheckSum() {
        return super.getCheckSum();
    }

    @Override
    public void setCheckSum(int checkSum) {
        super.setCheckSum(checkSum);
    }

    @Override
    public long getLastDeviceAddress() {
        return super.getLastDeviceAddress();
    }

    @Override
    public void setLastDeviceAddress(long lastDeviceAddress) {
        super.setLastDeviceAddress(lastDeviceAddress);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public long getSrc() {
        return super.getSrc();
    }

    @Override
    public long getSerial() {
        return super.getSerial();
    }

    @Override
    public void setSerial(long serial) {
        super.setSerial(serial);
    }

    @Override
    public byte getDev_type() {
        return super.getDev_type();
    }

    @Override
    public long getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public void setTimestamp(long timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public byte getState() {
        return super.getState();
    }

    @Override
    public void setState(byte state) {
        super.setState(state);
    }
}

class Lamp extends Device {
    public Lamp(String name, long address) {
        super(name, address, (byte) 0x04);
    }

    @Override
    public int getCheckSum() {
        return super.getCheckSum();
    }

    @Override
    public void setCheckSum(int checkSum) {
        super.setCheckSum(checkSum);
    }

    @Override
    public long getLastDeviceAddress() {
        return super.getLastDeviceAddress();
    }

    @Override
    public void setLastDeviceAddress(long lastDeviceAddress) {
        super.setLastDeviceAddress(lastDeviceAddress);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public long getSrc() {
        return super.getSrc();
    }

    @Override
    public long getSerial() {
        return super.getSerial();
    }

    @Override
    public void setSerial(long serial) {
        super.setSerial(serial);
    }

    @Override
    public byte getDev_type() {
        return super.getDev_type();
    }

    @Override
    public long getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public void setTimestamp(long timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public byte getState() {
        return super.getState();
    }

    @Override
    public void setState(byte state) {
        super.setState(state);
    }
}

class Socket extends Device {

    public Socket (String name, long address) {
        super(name, address, (byte) 0x05);
    }

    @Override
    public int getCheckSum() {
        return super.getCheckSum();
    }

    @Override
    public void setCheckSum(int checkSum) {
        super.setCheckSum(checkSum);
    }

    @Override
    public long getLastDeviceAddress() {
        return super.getLastDeviceAddress();
    }

    @Override
    public void setLastDeviceAddress(long lastDeviceAddress) {
        super.setLastDeviceAddress(lastDeviceAddress);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public long getSrc() {
        return super.getSrc();
    }

    @Override
    public long getSerial() {
        return super.getSerial();
    }

    @Override
    public void setSerial(long serial) {
        super.setSerial(serial);
    }

    @Override
    public byte getDev_type() {
        return super.getDev_type();
    }

    @Override
    public long getTimestamp() {
        return super.getTimestamp();
    }

    @Override
    public void setTimestamp(long timestamp) {
        super.setTimestamp(timestamp);
    }

    @Override
    public byte getState() {
        return super.getState();
    }

    @Override
    public void setState(byte state) {
        super.setState(state);
    }
}

enum Devices {
    SMARTHUB,
    ENVSENSOR,
    SWITCH,
    LAMP,
    SOCKET,
    CLOCK;

    public int getDeviceNum() {
        return ordinal() + 1;
    }
}

enum Commands {
    WHOISHERE,
    IAMHERE,
    GETSTATUS,
    STATUS,
    SETSTATUS,
    TICK;

    public int getCommandNum() {
        return ordinal() + 1;
    }
}

class PacketEncoder {
    private String stringData;
    private Integer[] bytes;

    private int length;
    private boolean isValidCheckSum;
    private int packetCRC8;

    private Hub hub;

    private int packetOffset = 1;

    //todo время

    public PacketEncoder() {};
    public String getStringData() {
        return stringData;
    }

    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(Hub hub) {
        this.hub = hub;
    }

    public boolean base64IsCorrect(String data) {
        boolean res = false;
        try {
            byte[] temp = Base64.getUrlDecoder().decode(data.getBytes());
            bytes = new Integer[temp.length];
            for (int i = 0; i < temp.length; i++) {
                bytes[i] = temp[i] & 0x00ff;
            }
            res = true;
        }
        catch (IllegalArgumentException e) {
            res = false;
        }
        return res;
    }

    public String manageReceivedCommand(int[] b) {
        StringBuilder ans = new StringBuilder();
        length = b[0];
        packetOffset = 1;
        ULEB128Util.bytesCounter = 0;
        long src = ULEB128Util.decode(b, packetOffset);
        packetOffset += ULEB128Util.bytesCounter;
        ULEB128Util.bytesCounter = 0;
        long dst = ULEB128Util.decode(b, packetOffset);
        packetOffset += ULEB128Util.bytesCounter;
        ULEB128Util.bytesCounter = 0;
        long serial = ULEB128Util.decode(b, packetOffset++);
        ULEB128Util.bytesCounter = 0;
        int dev_type = b[packetOffset++];
        System.out.println(dev_type);
        int cmd = b[packetOffset++];
        int checkSum = b[b.length - 1];
        if (dev_type == Devices.CLOCK.getDeviceNum() && cmd == Commands.TICK.getCommandNum()) {
            long timestamp = ULEB128Util.decode(b, packetOffset++);
            ULEB128Util.bytesCounter = 0;
            hub.setTimestamp(timestamp);
        }
        else if (cmd == Commands.WHOISHERE.getCommandNum()) {
            String name = getDeviceName(b);
            if (dev_type == Devices.ENVSENSOR.getDeviceNum()) {
                EnvSensor envSensor = new EnvSensor(name, src);
                int sensors = getSensors(b);
                envSensor.setSensors(sensors);
                envSensor.setHasTempDevice((sensors & (1 << 0)) != 0);
                envSensor.setHasWaterDevice((sensors & (1 << 1)) != 0);
                envSensor.setHasLuxDevice((sensors & (1 << 3)) != 0);
                envSensor.setHasAirDevice((sensors & (1 << 7)) != 0);
                envSensor.setList(getTriggers(b));
                envSensor.setSerial(serial);
                hub.putNewDeviceByName(envSensor);
                hub.putNewDeviceBySrc(envSensor);
            }
            else if (dev_type == Devices.SWITCH.getDeviceNum()) {
                Switch sw = new Switch(name, src);
                sw.initList(getSwitchListDevices(b));
                sw.setState((byte) 1);
                sw.setSerial(serial);
                hub.putNewDeviceBySrc(sw);
                hub.putNewDeviceByName(sw);
            }
            else if (dev_type == Devices.LAMP.getDeviceNum()) {
                Lamp lamp = new Lamp(name, src);
                lamp.setState((byte) 1);
                lamp.setSerial(serial);
                hub.putNewDeviceBySrc(lamp);
                hub.putNewDeviceByName(lamp);
            }
            else if (dev_type == Devices.SOCKET.getDeviceNum()) {
                Socket socket = new Socket(name, src);
                socket.setState((byte) 1);
                socket.setSerial(serial);
                hub.putNewDeviceBySrc(socket);
                hub.putNewDeviceByName(socket);
            }


            // отправить i am here хаба
            ans.append(hub.executeIAMHERE());
        }
        else if (cmd == Commands.IAMHERE.getCommandNum()) {
            // i am here
            // сначала мы должны получить имя устройства и создать объект этого устройства в зависимости от имени
            String name = getDeviceName(b);
            if (dev_type == Devices.SWITCH.getDeviceNum()) {
                if (!hub.contains(src)) {
                    Switch sw = new Switch(name, src);
                    sw.initList(getSwitchListDevices(b));
                    sw.setState((byte) 1);
                    sw.setSerial(serial);
                    System.out.println(sw.getDevicesNames());
                    hub.putNewDeviceBySrc(sw);
                    hub.putNewDeviceByName(sw);
                }
            }
            else if (dev_type == Devices.LAMP.getDeviceNum()) {
                if (!hub.contains(src)) {
                    Lamp lamp = new Lamp(name, src);
                    lamp.setState((byte) 1);
                    lamp.setSerial(serial);
                    hub.putNewDeviceBySrc(lamp);
                    hub.putNewDeviceByName(lamp);
                }
            }
            else if (dev_type == Devices.SOCKET.getDeviceNum()) {
                if (!hub.contains(src)) {
                    Socket socket = new Socket(name, src);
                    socket.setState((byte) 1);
                    hub.putNewDeviceBySrc(socket);
                    hub.putNewDeviceByName(socket);
                }
            }
            else if (dev_type == Devices.ENVSENSOR.getDeviceNum()) {
                if (!hub.contains(src)) {
                    EnvSensor envSensor = new EnvSensor(name, src);
                    int sensors = getSensors(b);
                    envSensor.setSensors(sensors);
                    envSensor.setHasTempDevice((sensors & (1 << 0)) != 0);
                    envSensor.setHasWaterDevice((sensors & (1 << 1)) != 0);
                    envSensor.setHasLuxDevice((sensors & (1 << 3)) != 0);
                    envSensor.setHasAirDevice((sensors & (1 << 7)) != 0);
                    envSensor.setList(getTriggers(b));
                    envSensor.setSerial(serial);
                    hub.putNewDeviceByName(envSensor);
                    hub.putNewDeviceBySrc(envSensor);
                }
            }
        }
        else if (cmd == Commands.STATUS.getCommandNum()) {
            if (dev_type == Devices.ENVSENSOR.getDeviceNum()) {
                int length = b[packetOffset++];
                ULEB128Util.bytesCounter = 0;
                if (hub.getSrcDevice().containsKey(src)) {
                    Device sensor = hub.getSrcDevice().get(src);
                    if (sensor.isHasTempDevice()) {
                        long tempBound = ULEB128Util.decode(b, packetOffset);
                        packetOffset += ULEB128Util.bytesCounter;
                        ULEB128Util.bytesCounter = 0;
                        sensor.setTempBound(tempBound);
                    }
                    if (sensor.isHasWaterDevice()) {
                        long waterBound = ULEB128Util.decode(b, packetOffset);
                        packetOffset += ULEB128Util.bytesCounter;
                        ULEB128Util.bytesCounter = 0;
                        sensor.setWaterBound(waterBound);
                    }
                    if (sensor.isHasLuxDevice()) {
                        long luxBound = ULEB128Util.decode(b, packetOffset);
                        packetOffset += ULEB128Util.bytesCounter;
                        ULEB128Util.bytesCounter = 0;
                        sensor.setLuxBound(luxBound);
                    }
                    if (sensor.isHasAirDevice()) {
                        long airBound = ULEB128Util.decode(b, packetOffset);
                        packetOffset += ULEB128Util.bytesCounter;
                        ULEB128Util.bytesCounter = 0;
                        sensor.setAirBound(airBound);
                    }

                    hub.getSrcDevice().put(src, sensor);
                    hub.getNameDevice().put(sensor.getName(), sensor);
                }


            }
            else if (dev_type == Devices.SWITCH.getDeviceNum()) {
                Device sw = hub.getDevice(src);
                int state = b[packetOffset++];
                sw.setState((byte) 1);
                List<String> list = sw.getDevicesNames();
                for (String l : list) {
                    Device lampOrSocket = hub.getDevice(l);
                    lampOrSocket.setState((byte) state);
                    hub.getSrcDevice().put(lampOrSocket.getSrc(), lampOrSocket);
                    hub.getNameDevice().put(lampOrSocket.getName(), lampOrSocket);
                    ans.append(hub.executeSETSTATUS(lampOrSocket.getSrc(), state));
                }
            }
            else if (dev_type == Devices.LAMP.getDeviceNum()) {
                Device lamp = hub.getDevice(src);
                int state = b[packetOffset++];
                lamp.setState((byte) state);
                hub.putNewDeviceBySrc(lamp);
                hub.putNewDeviceByName(lamp);

            }
            else if (dev_type == Devices.SOCKET.getDeviceNum()) {
                Device socket = hub.getDevice(src);
                int state = b[packetOffset++];
                socket.setState((byte) state);
                hub.putNewDeviceBySrc(socket);
                hub.putNewDeviceByName(socket);
            }
        }
//        System.out.println("src - " + src);
//        System.out.println("dst - " + dst);
//        System.out.println("serial - " + serial);
//        System.out.println("dev_type - " + dev_type);
//        System.out.println("cmd - " + cmd);
        return ans.isEmpty() ? "" : ans.toString();
    }

    public List<String> getSwitchListDevices(int[] b) {
        List<String> list = new ArrayList<>();
        int arrayLength = b[packetOffset++];
        int counter = 0;
        while (counter < arrayLength) {
            int stringLength = b[packetOffset++];
            byte[] string = new byte[stringLength];
            for (int i = 0; i < stringLength; i++) {
                string[i] = (byte) b[packetOffset++];
            }
            list.add(new String(string));
            counter++;
        }
        return list;
    }

    public int getSensors(int[] b) {
        return b[packetOffset++] & 0xff;
    }

    public List<Trigger> getTriggers(int[] b) {
        ULEB128Util.bytesCounter = 0;
        List<Trigger> list = new ArrayList<>();
        int length = b[packetOffset++];
        int counter = 0;
        while (counter < length) {
            int op = b[packetOffset++] & 0xff;
            long value = ULEB128Util.decode(b, packetOffset);
            packetOffset += ULEB128Util.bytesCounter;
            ULEB128Util.bytesCounter = 0;
            String name = getDeviceName(b);
            Trigger trigger = new Trigger(op, value, name);
            list.add(trigger);
            counter++;

        }
        return list;
    }

    public String getDeviceName(int[] b) {
        int length = b[packetOffset++];
        int copy = packetOffset;
        byte[] deviceName = new byte[length];
        for (int i = copy; i < copy + length; i++) {
            deviceName[i - copy] = (byte) (b[i]);
            packetOffset++;
        }
        return new String(deviceName);
    }

    public String buildStringForServer() {
        int counter = 0;
        StringBuilder ans = new StringBuilder();
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
                String string = manageReceivedCommand(b);
                if (string.length() != 0) {
                    ans.append(string);
                }
            }
        }
        return ans.toString();
    }
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
