package com.ucsal;

import java.time.Instant;

public class Main {
    static void main() {
        System.out.print(Instant.now());
    }

    public Instant getNow() {
        return Instant.now();
    }
}
