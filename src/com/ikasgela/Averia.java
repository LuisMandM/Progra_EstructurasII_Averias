package com.ikasgela;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Averia {
    private String titulo;
    private String descripcion;
    private LocalDateTime fecha_Apertura;
    private int nivel;

    //Asociations
    private Tecnico tecnico_Asignado;

    public Averia(String titulo, String descripcion, LocalDateTime fecha_Apertura, int nivel) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_Apertura = fecha_Apertura;
        this.nivel = nivel;
    }

    //Getters and Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha_Apertura() {
        return fecha_Apertura;
    }

    public void setFecha_Apertura(LocalDateTime fecha_Apertura) {
        this.fecha_Apertura = fecha_Apertura;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Tecnico getTecnico_Asignado() {
        return tecnico_Asignado;
    }

    public void setTecnico_Asignado(Tecnico tecnico_Asignado) {
        this.tecnico_Asignado = tecnico_Asignado;
    }

    @Override
    public String toString() {
        return String.format("Titulo: %-20s\nDescrip: %-5s\nApertura:%td %tb %tY \nNivel: %-2d",
                titulo, descripcion, fecha_Apertura, fecha_Apertura, fecha_Apertura, nivel);
    }
}
