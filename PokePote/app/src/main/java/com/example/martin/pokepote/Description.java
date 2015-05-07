package com.example.martin.pokepote;

/**
 * Created by Martin on 06/05/2015.
 */
public class Description {
    public String generation;
    public String description;

    public Description(String generation, String description) {
        this.generation = generation;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Description{" +
                "generation='" + generation + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
