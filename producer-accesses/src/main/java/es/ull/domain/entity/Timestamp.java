package es.ull.domain.entity;

import java.util.Date;

public class Timestamp extends Date {

    public Timestamp(int year, int month, int date,
            int hour, int minute, int second) {
        super(year, month, date, hour, minute, second);
    }

    public Timestamp(long time) {
        super((time / 1000) * 1000);
    }

    public void setTime(long time) {
        super.setTime((time / 1000) * 1000);
    }

    public long getTime() {
        return super.getTime();
    }

    public Object clone () {
        Object newTimestamp = super.clone();
        return newTimestamp;
    }

    public boolean equals(Timestamp ts) {
        if (super.equals(ts)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Object ts) {
        if (ts instanceof Timestamp) {
            return this.equals((Timestamp) ts);
        } else {
            return false;
        }
    }

    public boolean before(Timestamp ts) {
        return compareTo(ts) < 0;
    }

    public boolean after(Timestamp ts) {
        return compareTo(ts) > 0;
    }

    public void addSeconds(long seconds) {
        long time = this.getTime() + (seconds * 1000);
        this.setTime(time);
    }

    public void addMiliseconds(long mlSeconds) {
        long time = this.getTime() + mlSeconds;
        this.setTime(time);
    }

    public int compareTo(Timestamp ts) {
        long thisTime = this.getTime();
        long anotherTime = ts.getTime();
        int i = (thisTime < anotherTime ? -1 : (thisTime == anotherTime ? 0 : 1));
        return i;
    }

    public int compareTo(Date o) {
        if (o instanceof Timestamp) {
            return compareTo((Timestamp) o);
        } else {
            Timestamp ts = new Timestamp(o.getTime());
            return this.compareTo(ts);
        }
    }
}