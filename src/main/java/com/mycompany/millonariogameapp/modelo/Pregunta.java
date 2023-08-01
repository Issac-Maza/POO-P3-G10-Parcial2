/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millonariogameapp.modelo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author maza-
 */
public class Pregunta {
    private String enunciado;
    private int nivel;
    private String respuestaCorrecta;
    private String opcion1;
    private String opcion2;
    private String opcion3;
    private String opcion4;
    private ArrayList<String> posiblesRespuestas;

    public Pregunta(String enunciado, int nivel, String opcion1,String opcion2,String opcion3,String opcion4) {
        this.enunciado = enunciado;
        this.nivel = nivel;
        this.respuestaCorrecta = opcion1;
        this.opcion1= opcion1;
        this.opcion2 = opcion2;
        this.opcion3 = opcion3;
        this.opcion4 = opcion4;
        this.posiblesRespuestas = cambiarOrden();
    }
    
    public Pregunta(String enunciado, int niv, ArrayList<String> respuestas) {
        this(enunciado, niv, respuestas.get(0), respuestas.get(1), respuestas.get(2), respuestas.get(3));
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }
    
    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String repuestaCorrecta) {
        this.respuestaCorrecta = repuestaCorrecta;
    }

    public ArrayList<String> getPosiblesRespuestas() {
        return posiblesRespuestas;
    }

    public void setPosiblesRespuestas(ArrayList<String> posiblesRespuestas) {
        this.posiblesRespuestas = posiblesRespuestas;
    }
    
    @Override
    public String toString(){
        String result = "Enunciado: " + enunciado + "\n";
        result += "Nivel: " + nivel + "\n";
        result += "[1] " + posiblesRespuestas.get(0) + "\n";
        result += "[2] " + posiblesRespuestas.get(1) + "\n";
        result += "[3] " + posiblesRespuestas.get(2) + "\n";
        result += "[4] " + posiblesRespuestas.get(3) + "\n";
        return result; 
    }
    
    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(o!=null && getClass() == o.getClass()){
            Pregunta question = (Pregunta)o;
            return this.enunciado == question.enunciado;
        }else{
            return false;
        }
    }
    
    public ArrayList<String> cambiarOrden(){
        ArrayList<String> resp = new ArrayList<>();
        resp.add(opcion1);
        resp.add(opcion2);
        resp.add(opcion3);
        resp.add(opcion4);
        Random random = new Random();
        for (int i = resp.size() - 1; i >= 1; i--)
        {
            // obtener un índice aleatorio `j` tal que `0 <= j <= i`
            int j = random.nextInt(i + 1);
 
            // intercambiar el elemento en la i-ésima posición en la lista con el elemento en
            // índice generado aleatoriamente `j`
            String obj = resp.get(i);
            resp.set(i, resp.get(j));
            resp.set(j, obj);
        }
        return resp;
    }
    
}
