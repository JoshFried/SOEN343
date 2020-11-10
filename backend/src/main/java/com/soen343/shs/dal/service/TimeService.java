package com.soen343.shs.dal.service;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class TimeService {
    private Clock clock;

    public TimeService() {
        clock = ClockSingleton.getClock();
    }

    /**
     * @return time in millisecond corresponding to the time elapse since 1 January 1970, 00:00 UTC
     */
    public long getCurrentDateInMilliseconds() {
        return clock.millis();
    }

    /**
     *
     * @param newTime new time time in millisecond corresponding to the time elapse since 1 January 1970, 00:00 UTC
     * @return time in millisecond corresponding to the time elapse since 1 January 1970, 00:00 UTC
     */
    public long setNewDate(final long newTime) {
        final long currentTime = getCurrentDateInMilliseconds();
        final long offSet = newTime - currentTime;
        clock = Clock.offset(clock, Duration.ofMillis(offSet));
        return clock.millis();
    }

    /**
     *
     * @return time in millisecond corresponding the number of milliseconds in a day, 0 ms correspond to midnight.
     */
    public long getCurrentTimeInMilliseconds() {
        final LocalDateTime localDateTime = LocalDateTime.now(clock);
        final long hoursInMilliseconds = localDateTime.getHour() * 60000;
        final long minuteInMilliSeconds = localDateTime.getMinute() * 60000;
        final long secondsInMilliSeconds = localDateTime.getSecond() * 60000;
        return hoursInMilliseconds + minuteInMilliSeconds + secondsInMilliSeconds;
    }

    public LocalTime getLocalTime() {
        return LocalDateTime.now(clock).toLocalTime();
    }
}
