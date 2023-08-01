/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millonariogameapp.modelo;
import java.util.Scanner;

/**
 *
 * @author maza-
 */
public class TerminoAcademico {
    private int anio;
    private int numero;

    public TerminoAcademico(int anio, int numero) {
        this.anio = anio;
        this.numero = numero;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
    public String nombreTermino(){
        return anio+"-"+numero;
    }
    
    @Override
    public String toString(){
        return "Año: " + anio + ", Número: " + numero;
    }
    
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o!=null && getClass() == o.getClass()){
            TerminoAcademico termino = (TerminoAcademico)o;
            return this.numero == termino.getNumero() && this.anio == termino.getAnio();
        }else{
            return false;
        }
    }
}
