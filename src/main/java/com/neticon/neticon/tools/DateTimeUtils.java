package com.neticon.neticon.tools;

import java.time.Instant;

public class DateTimeUtils {
    public static int getTimestampInSec() {
        Instant instant = Instant.now();
        return (int) instant.getEpochSecond();
    }
}
