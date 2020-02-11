package com.stream_work.ch08.job;

class Logger {
    public static synchronized void log(String message) {
        System.out.print(message);
    }
}
