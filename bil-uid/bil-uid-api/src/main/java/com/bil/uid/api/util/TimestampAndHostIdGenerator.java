package com.bil.uid.api.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 根据时间和host生成的id
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @since 2020/08/23
 */
@Slf4j
public class TimestampAndHostIdGenerator {
    private static final int[] codex = {2, 3, 5, 6, 8, 9, 19, 11, 12, 14, 15, 17, 18};
    private static final AtomicInteger messageOrder = new AtomicInteger(0);

    private static final String localAddress = getLocalAddress();

    /**
     * 在生成message id的时候带上进程id，避免一台机器上部署多个服务都发同样的消息时出问题
     */
    private static final int PID = getPid();

    public static String getNext() {
        StringBuilder sb = new StringBuilder(40);
        long time = System.currentTimeMillis();
        String ts = new Timestamp(time).toString();

        for (int idx : codex) {
            sb.append(ts.charAt(idx));
        }
        sb.append('.').append(localAddress);
        sb.append('.').append(PID);
        sb.append('.').append(messageOrder.getAndIncrement()); //可能为负数.但是无所谓.
        return sb.toString();
    }

    public static int getPid() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            // format: "pid@hostname"
            String name = runtime.getName();
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Throwable e) {
            return 0;
        }
    }

    public static String getLocalAddress() {
        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            final ArrayList<String> ipv4Result = new ArrayList<>();
            final ArrayList<String> ipv6Result = new ArrayList<>();
            while (interfaces.hasMoreElements()) {
                final NetworkInterface networkInterface = interfaces.nextElement();
                if (!networkInterface.isUp()) {
                    continue;
                }
                if (networkInterface.isVirtual()) {
                    continue;
                }

                final Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    final InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress()) {
                        if (address instanceof Inet6Address) {
                            ipv6Result.add(address.getHostAddress());
                        } else {
                            ipv4Result.add(address.getHostAddress());
                        }
                    }
                }
            }

            if (!ipv4Result.isEmpty()) {
                for (String ip : ipv4Result) {
                    if (ip.startsWith("127.0")) {
                        continue;
                    }

                    return ip;
                }

                return ipv4Result.get(ipv4Result.size() - 1);
            } else if (!ipv6Result.isEmpty()) {
                return ipv6Result.get(0);
            }

            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            log.error("get local address failed", e);
        }

        return null;
    }


    public static void main(String[] args) {
        System.out.println(getNext());
        System.out.println(getNext());
    }
}
