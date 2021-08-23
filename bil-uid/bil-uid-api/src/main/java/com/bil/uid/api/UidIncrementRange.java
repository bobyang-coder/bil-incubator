package com.bil.uid.api;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Uid 增量范围
 *
 * @author haibo.yang   <bobyang_coder@163.com>
 * @version v1.0.0
 * @since 2020/3/29
 */
public class UidIncrementRange implements Serializable {

    private static final long serialVersionUID = 1352421842038168499L;

    private final AtomicBoolean over = new AtomicBoolean(false);

    private final LinkedBlockingQueue<Long> ids;

    private final int type;

    public UidIncrementRange(LinkedBlockingQueue<Long> ids, int type) {
        this.ids = ids;
        this.type = type;
    }

    public long removeAndGet() {
        Long id = ids.poll();
        if (id == null) {
            over.compareAndSet(false, true);
            return -1;
        }
        return id;
    }

    public boolean discard(boolean ignoreEmpty) {
        if (type == 0 || type == 1) {
            LocalDate currentDate = LocalDate.now();
            Long value = ids.peek();
            if (value == null) {
                return !ignoreEmpty;
            }
            Uid uid = Uid.of(value);
            if (currentDate.isAfter(uid.getTimeDate())) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean expire() {
        return discard(true);
    }

    public int getType() {
        return type;
    }

    public List<Long> toList() {
        List<Long> target = Lists.newArrayListWithCapacity(ids.size());
        ids.drainTo(target);
        return target;
    }

    public boolean isOver() {
        return over.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UidIncrementRange)) {
            return false;
        }
        UidIncrementRange that = (UidIncrementRange) o;
        if (type != that.type) {
            return false;
        }
        if (!over.equals(that.over)) {
            return false;
        }
        return Objects.equals(ids, that.ids);
    }

    @Override
    public int hashCode() {
        int result = over.hashCode();
        result = 31 * result + (ids != null ? ids.hashCode() : 0);
        result = 31 * result + type;
        return result;
    }

    @Override
    public String toString() {
        return "IncrementRange{" +
                "over=" + over +
                ", ids=" + ids +
                ", type=" + type +
                '}';
    }
}
