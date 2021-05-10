package com.example.teamproject;

/**
 * Created by 신무현
 */

// 싱글톤

public class Singleton {
    // Eager Initialization
    private static Singleton uniqueInstance = new Singleton();

    public String name;

    private Singleton() {}

    public static Singleton getInstance() {
        return uniqueInstance;
    }
}
