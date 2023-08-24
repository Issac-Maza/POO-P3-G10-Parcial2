/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millonariogameapp.modelo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author maza-
 */
public class Materia implements Serializable{
    private String codigo;
    private String nombre;
    private int cantidadNiveles;
    private ArrayList<Paralelo> paralelos;
    private ArrayList<ArrayList<Pregunta>> lstOrdenadasxNivel;

    public Materia(String codigo, String nombre, int cantidadNiveles) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidadNiveles = cantidadNiveles;
        paralelos =  new ArrayList<>();
        lstOrdenadasxNivel = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public int getCantidadNiveles() {
        return cantidadNiveles;
    }

    public void setCantidadNiveles(int cantidadNiveles) {
        this.cantidadNiveles = cantidadNiveles;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Materia subject = (Materia) obj;
            return this.codigo.equals(subject.codigo) && this.nombre.equals(subject.nombre);
        } else {
            return false;
        }
    }
    
    public ArrayList<ArrayList<Pregunta>> getLstOrdenadasxNivel() {
        return lstOrdenadasxNivel;
    }

    public void setLstOrdenadasxNivel(ArrayList<ArrayList<Pregunta>> lstOrdenadasxNivel) {
        this.lstOrdenadasxNivel = lstOrdenadasxNivel;
    }
    
    public ArrayList<Paralelo> getParalelos() {
        return paralelos;
    }

    public void setParalelos(ArrayList<Paralelo> paralelos) {
        this.paralelos = paralelos;
    }
    
    public void agregarParalelo(Paralelo obj){
        this.paralelos.add(obj);
    }
   
    public void creacionDeNiveles(){
        for(int i = 0; i<cantidadNiveles; i++){
            lstOrdenadasxNivel.add(new ArrayList<>());
        }
    }
    
    public void addPreguntas(ArrayList<Pregunta> preguntas){
        lstOrdenadasxNivel.add(preguntas);
    }

    @Override
    public String toString() {
        return codigo +"_"+ nombre ;
    }
    
    
}
