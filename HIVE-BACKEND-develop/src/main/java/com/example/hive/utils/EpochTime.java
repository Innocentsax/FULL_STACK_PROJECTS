package com.example.hive.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class EpochTime {

    public static String getElapsedTime(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime zdtNow = ZonedDateTime.of(now, ZoneId.systemDefault());
        ZonedDateTime zdtCreatedAt = ZonedDateTime.of(time, ZoneId.systemDefault());

        long dateNow = zdtNow.toInstant().toEpochMilli();
        long dateCreatedAt = zdtCreatedAt.toInstant().toEpochMilli();
        long timeDiff = (dateNow - dateCreatedAt) / 1000;

        long interval = timeDiff / 31536000;
        if (interval >= 1) {
            return interval + (interval > 1 ? " years" : " year") + " ago";
        }

        interval = timeDiff / 2592000;
        if (interval >= 1) {
            return interval + (interval > 1 ? " months" : " month") + " ago";
        }

        interval = timeDiff / 604800;
        if (interval >= 1) {
            return interval + (interval > 1 ? " weeks" : " week") + " ago";
        }

        interval = timeDiff / 86400;
        if (interval > 1) {
            return interval + " days ago";
        }

        if (interval == 1) {
            return formatTimeOfDay("Yesterday", time);
        }

        return formatTimeOfDay("Today", time);
    }

    private static String formatTimeOfDay(String day, LocalDateTime time) {
        String[] today = time.toString().split("T");
        String[] timeOfDay = today[1].split(":");
        int first = Integer.parseInt(timeOfDay[0]);
        if (first < 12) {
            return day + ", " + first + ":" + timeOfDay[1] + "AM";
        }
        if (first == 12) {
            return day + ", " + first + ":" + timeOfDay[1] + "PM";
        }

        return day + ", " + (first - 12) + ":" + timeOfDay[1] + "PM";
    }
}
