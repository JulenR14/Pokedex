package com.example.pokedex.ClasesComunes;

import java.util.ArrayList;

/*
    Esta clase es un contenedor de una lista de objetos de tipo DataSimple
    que se utiliza para obtener la lista de pokemons de la API
 */
public class DataList {

    public ArrayList<DataSimple> results;

    public ArrayList<DataSimple> getResults() { return results; }
}
