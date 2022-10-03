package com.streamwork.ch04.extra;

class Logger {
    public static synchronized void log(String message) {
        System.out.print(message);
    }
}
