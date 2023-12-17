package com.example.pokedex.models;

import com.example.pokedex.ClasesComunes.DataSimple;

import java.util.List;

public class PokeDetails {

     int id;
     String name;
     double height;
     double weight;
     List<Stats> stats;
     List<Types> types;
     int base_experience;

    public int getId() { return id; }
    public String getName() { return name; }
    public double getHeight() { return height; }
    public double getWeight() { return weight; }
    public List<Stats> getStats() { return stats; }
    public List<Types> getTypes() { return types; }
    public int getBase_experience() { return base_experience; }


    public static class Stats {
        int base_stat;
        DataSimple stat;
    }

    public static class Types {
        int slot;
        DataSimple type;
    }

}
