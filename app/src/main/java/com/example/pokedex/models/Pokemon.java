package com.example.pokedex.models;

public class Pokemon {

    public String name;
    public String url;

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() { return name; }
    public String getUrl() { return url; }

}
