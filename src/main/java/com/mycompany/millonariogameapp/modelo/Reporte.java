/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millonariogameapp.modelo;

import java.util.ArrayList;

/**
 *
 * @author maza-
 */
public class Reporte {
    private String fecha;
    private String estudianteParticipante;
    private int nivelMaximoAlcanzado;
    //private int tiempo;
    private String premio;

    public Reporte(String fecha , String participante, int level, String premio){
        this.fecha=fecha;
        this.nivelMaximoAlcanzado = level;
        this.estudianteParticipante =participante;
        this.premio=premio;
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
