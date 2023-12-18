package com.example.pokedex.models;

import com.example.pokedex.ClasesComunes.DataSimple;

import java.util.List;

/*
    Esta clase se utiliza para obtener los detalles de cada pokemon
 */
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

        public DataSimple getStat() { return stat; }
        public int getBase_stat() { return base_stat; }
    }

    public static class Types {
        int slot;
        DataSimple type;

        public DataSimple getType() { return type; }
    }

}
