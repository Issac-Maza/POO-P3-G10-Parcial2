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
public class Reporte implements Serializable{
    private String fecha;
    private String estudianteParticipante;
    private String estudianteApoyo; 
    private int nivelMaximoAlcanzado;
    private String premio;
    private int puntaje;
    private ArrayList<String> comodinesUsados;
    private int contadorComodines;
    private String tiempoPasados;
    private int numPregAlcanzada;
    private int pregXNivel;
    
    public String getEstudianteApoyo() {
        return estudianteApoyo;
    }

    public void setEstudianteApoyo(String estudianteApoyo) {
        this.estudianteApoyo = estudianteApoyo;
    }

    public int getPregXNivel() {
        return pregXNivel;
    }

    public void setPregXNivel(int pregXNivel) {
        this.pregXNivel = pregXNivel;
    }

    public int getNumPregAlcanzada() {
        return numPregAlcanzada;
    }

    public void setNumPregAlcanzada(int numPregAlcanzada) {
        this.numPregAlcanzada = numPregAlcanzada;
    }

    public String getTiempoPasados() {
        return tiempoPasados;
    }

    public void setTiempoPasados(String tiempoPasados) {
        this.tiempoPasados = tiempoPasados;
    }

    public int getContadorComodines() {
        return contadorComodines;
    }

    public void setContadorComodines(int contadorComodines) {
        this.contadorComodines = contadorComodines;
    }

    public ArrayList<String> getComodinesUsados() {
        return comodinesUsados;
    }

    public void setComodinesUsados(ArrayList<String> comodinesUsados) {
        this.comodinesUsados = comodinesUsados;
    }

    public int getNivelMaximoAlcanzado() {
        return nivelMaximoAlcanzado;
    }

    public void setNivelMaximoAlcanzado(int nivelMaximoAlcanzado) {
        this.nivelMaximoAlcanzado = nivelMaximoAlcanzado;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public Reporte(String fecha , String participante, int level, String premio){
        this.fecha=fecha;
        this.nivelMaximoAlcanzado = level;
        this.estudianteParticipante =participante;
        this.premio=premio;
        this.comodinesUsados = new ArrayList<>();
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstudianteParticipante() {
        return estudianteParticipante;
    }

    public int getNivel() {
        return nivelMaximoAlcanzado;
    }

    /*public int getTiempo() {
        return tiempo;
    }*/

    public String getPremio() {
        return premio;
    }
    //avance del retorno
    @Override
    public String toString(){
        return"REPORTE:"+
              "\nFecha: "+fecha+
              "\nParticipante: "+estudianteParticipante+
              "\nNivel máximo alcanzado: "+nivelMaximoAlcanzado+
              "\nPremio: "+premio;
    }
}
