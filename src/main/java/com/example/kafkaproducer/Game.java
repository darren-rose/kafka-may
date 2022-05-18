package com.example.kafkaproducer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String id;
    private String name;
    private Behaviour behaviour;
}
