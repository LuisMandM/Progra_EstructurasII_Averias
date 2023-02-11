package com.ikasgela;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tecnico {
    private String nombre;
    private int nivel;

    //Asociacion y Averias
    List<Averia> historial_Averias = new ArrayList<>();
    LinkedList<Averia> averias_Pendientes = new LinkedList<>();


    //Constructor
    public Tecnico(String nombre, int nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }

    //Getters and Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public List<Averia> getHistorial_Averias() {
        return historial_Averias;
    }

    public void add_Averia(Averia averia_current) {
        this.historial_Averias.add(averia_current);
        this.averias_Pendientes.add(averia_current);
    }

    public LinkedList<Averia> getAverias_Pendientes() {
        return averias_Pendientes;
    }

}
