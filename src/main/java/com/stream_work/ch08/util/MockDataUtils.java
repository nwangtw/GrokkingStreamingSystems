package com.stream_work.ch08.util;

import com.stream_work.ch08.job.LatLongHolder;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;

public class MockDataUtils {

    private static final String[] ACCOUNTS = {
            "1234-5678-9111-1111",
            "1234-5678-9222-2222",
            "1234-5678-9333-3333",
            "1234-5678-9444-4444",
    };


    private static final List<LatLongHolder> LAT_LONG_LIST = List.of(
            new LatLongHolder(38.62727, -90.19789), // St. Louis, Missouri
            new LatLongHolder(37.773972,-122.431297), // San Francisco, California
            new LatLongHolder(40.730610, -73.935242),  // New York City, New York
            new LatLongHolder(45.512794, -122.679565) // Portland, Oregon
    );

    public static String getRandomUserAccount() {
        Random random = new Random();
        return ACCOUNTS[random.ints(0, ACCOUNTS.length).limit(1).findFirst().getAsInt()];
    }

    public static LatLongHolder getRandomLocation() {
        Random random = new Random();
        int index = random.ints(0, LAT_LONG_LIST.size()).limit(1).findFirst().getAsInt();
        return LAT_LONG_LIST.get(index);
    }

    public static LocalDate getRandomTransactionLocalDate() {
        Random random = new Random();
        int index = random.ints(0, 3).limit(1).findFirst().getAsInt();

        if (index == 0) {
            return LocalDate.of( 2019 , Month.MARCH , 11 );
        } else if (index == 1) {
            return LocalDate.now();
        } else {
            return LocalDate.of( 2018 , Month.JANUARY , 1 );
        }
    }
}
