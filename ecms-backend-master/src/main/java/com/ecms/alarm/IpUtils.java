package com.ecms.alarm;

import java.net.*;
import java.util.Enumeration;

public class IpUtils {

    public static final String IP_UNKNOW = "unknow ip";

    public static String getLocalIp() {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            return getLocalIpFromWindows();
        } else {
            return getLocalIpFromLinux();
        }
    }

    public static String getMacAddr() {
        String MacAddr = "";
        String str = "";
        try {
            NetworkInterface NIC = NetworkInterface.getByName("eth0");
            byte[] buf = NIC.getHardwareAddress();
            for (int i = 0; i < buf.length; i++) {
                str = str + byteHEX(buf[i]);
            }
            MacAddr = str.toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return MacAddr;
    }

    public static String getLocalIpFromLinux() {
        String ip = IP_UNKNOW;
        try {
            Enumeration<?> e1 = NetworkInterface.getNetworkInterfaces();
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                if (!ni.getName().equals("eth0")) {
                    continue;
                } else {
                    Enumeration<?> e2 = ni.getInetAddresses();
                    while (e2.hasMoreElements()) {
                        InetAddress ia = (InetAddress) e2.nextElement();
                        if (ia instanceof Inet6Address)
                            continue;
                        ip = ia.getHostAddress();
                    }
                    break;
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (IP_UNKNOW.equals(ip)) {
            ip = getLocalIpFromWindows();
        }
        return ip;
    }

    public static String byteHEX(byte ib) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }

    public static String getLocalIpFromWindows() {
        String localIp = "";
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            localIp = IP_UNKNOW;
        }
        return localIp;
    }

    public static String getHostName() {
        String hostName = "";
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            hostName = "hostname";
        }
        return hostName;
    }

}
