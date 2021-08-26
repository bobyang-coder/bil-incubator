package com.bil.uid.api.util;

/**
 * twitter的snowflake算法 -- java实现
 * <p>
 * 原理:
 * SnowFlake算法产生的ID是一个64位的整型，结构如下（每一部分用“-”符号分隔）：
 * <p>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 * 二进制格式：(64位)
 * 1位标识部分+41位时间戳部分+5位数据中心标识部分+5位机器标识部分+12位序列号部分
 * <p>
 * 1位标识部分:在java中由于long的最高位是符号位，正数是0，负数是1，一般生成的ID为正数，所以为0；
 * 41位时间戳部分:这个是毫秒级的时间，一般实现上不会存储当前的时间戳，而是时间戳的差值（当前时间-固定的开始时间），
 * 这样可以使产生的ID从更小值开始；41位的时间戳可以使用69年，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年；
 * 10位节点部分:Twitter实现中使用前5位作为数据中心标识，后5位作为机器标识，可以部署1024个节点；
 * 12位序列号部分:支持同一毫秒内同一个节点可以生成4096个ID；
 * <p>
 * SnowFlake算法生成的ID大致上是按照时间递增的，用在分布式系统中时，需要注意数据中心标识和机器标识必须唯一，
 * 这样就能保证每个节点生成的ID都是唯一的。或许我们不一定都需要像上面那样使用5位作为数据中心标识，5位作为机器标识，
 * 可以根据我们业务的需要，灵活分配节点部分，
 * 如：若不需要数据中心，完全可以使用全部10位作为机器标识；若数据中心不多，也可以只使用3位作为数据中心，7位作为机器标识。
 * <p>
 * 注：long类型数据为8字节，一个字节8位，刚好64位
 * <p>
 *
 * @author bobyang
 * @version v1.0
 * @date 2017/09/02
 */
public class Snowflake {

    /**
     * 起始的时间戳
     */
    private static final long START_STMP = 1504364544760L;//2017年09月02日23:02:47

    /**
     * 每一部分占用的位数
     */
    private static final long DATA_CENTER_BIT = 5;//数据中心占用的位数
    private static final long MACHINE_BIT = 5;   //机器标识占用的位数
    private static final long SEQUENCE_BIT = 12; //序列号占用的位数

    /**
     * 每一部分的最大值
     */
    private static final long MAX_DATA_CENTER_NUM = ~(-1L << DATA_CENTER_BIT);
    private static final long MAX_MACHINE_NUM = ~(-1L << MACHINE_BIT);
    private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BIT);

    /**
     * 每一部分向左的位移
     */
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    private static final long DATACENTER_LEFT = SEQUENCE_BIT + MACHINE_BIT;
    private static final long TIMESTMP_LEFT = DATACENTER_LEFT + DATA_CENTER_BIT;

    private long datacenterId;  //数据中心
    private long machineId;     //机器标识
    private long sequence = 0L; //序列号
    private long lastStmp = -1L;//上一次时间戳

    public Snowflake(long dataCenterId, long machineId) {
        if (dataCenterId > MAX_DATA_CENTER_NUM || dataCenterId < 0) {
            throw new IllegalArgumentException("DataCenterId can't be greater than " + MAX_DATA_CENTER_NUM + "or less than 0");
        }
        if (machineId > MAX_MACHINE_NUM || machineId < 0) {
            throw new IllegalArgumentException("MachineId can't be greater than " + MAX_MACHINE_NUM + " or less than 0");
        }
        this.datacenterId = dataCenterId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long currStmp = getNewStmp();
        if (currStmp < lastStmp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
        }
        if (currStmp == lastStmp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                currStmp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }
        lastStmp = currStmp;
        return (currStmp - START_STMP) << TIMESTMP_LEFT //时间戳部分
                | datacenterId << DATACENTER_LEFT       //数据中心部分
                | machineId << MACHINE_LEFT             //机器标识部分
                | sequence;                             //序列号部分
    }

    /**
     * 获取下一个毫秒值
     *
     * @return
     */
    private long getNextMill() {
        long mill = getNewStmp();
        while (mill <= lastStmp) {
            mill = getNewStmp();
        }
        return mill;
    }

    /**
     * 获取新的毫秒值
     *
     * @return
     */
    private long getNewStmp() {
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
        //48130544695631940
        Snowflake snowFlake = new Snowflake(22, 30);
        for (int i = 0; i < (1 << 12); i++) {
            System.out.println(snowFlake.nextId());
        }
        System.out.println(~(-1L << DATA_CENTER_BIT));
        System.out.println(1 << 12);
    }
}
