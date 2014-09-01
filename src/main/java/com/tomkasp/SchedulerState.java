package com.tomkasp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

/**
 * Created by tomkasp on 9/1/14.
 */

@Entity
@Table(name = "QRTZ_SCHEDULER_STATE")
public class SchedulerState {

    @Id
    @Column(name = "sched_name")
    private String schedulerName;

    @Column(name = "instance_name")
    private String instanceName;

    @Column(name = "last_checkin_time")
    private BigInteger lastCheckInTime;

    @Column(name = "checkin_interval")
    private BigInteger checkinInterval;

    @Override
    public String toString() {
        return "SchedulerState{" +
                "schedulerName='" + schedulerName + '\'' +
                ", instanceName='" + instanceName + '\'' +
                ", lastCheckInTime=" + lastCheckInTime +
                ", checkinInterval=" + checkinInterval +
                '}';
    }
}