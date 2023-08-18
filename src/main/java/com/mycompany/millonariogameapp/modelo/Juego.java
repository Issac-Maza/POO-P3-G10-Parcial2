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
public class Juego implements Serializable{
    
    private TerminoAcademico termino;
    private Materia materia;
    private Paralelo paralelo;
    private int preguntasPorNivel;
    private Estudiante participante;
    private Estudiante acompañante;
    private int puntaje;
    private ArrayList<Pregunta> preguntasSeleccionadas;
    private static boolean boolean50_50 = true;
    private static boolean booleanConsultarCompañero = true;
    private static boolean booleanConsultarSalon = true;

    public Juego(TerminoAcademico termino,Paralelo paralelo ,Materia materia, int preguntasPorNivel, Estudiante participante, Estudiante acompañante) {
        this.termino = termino;
        this.paralelo = paralelo;
        this.materia = materia;
        this.preguntasPorNivel = preguntasPorNivel;
        this.participante = participante;
        this.acompañante = acompañante;
        preguntasSeleccionadas = new ArrayList();
    }
    
    public void iniciarJuego(){
        
    }
    
    public void usarComodin50_50(){
        if(boolean50_50==true){
            
            
            boolean50_50 = false;
        }
        
    }
    
    public void usarComodinConsultarCompañero(){
        if(booleanConsultarCompañero == true){
            
            
            booleanConsultarCompañero = false;
        }
    }
    
    public void usarComodinConsultarSalon(){
        if(booleanConsultarSalon == true){
            
            
            booleanConsultarSalon = false;
        }
        
    }

    public TerminoAcademico getTermino() {
        return termino;
    }

    public Materia getMateria() {
        return materia;
    }

    public Paralelo getParalelo() {
        return paralelo;
    }

    public int getPreguntasPorNivel() {
        return preguntasPorNivel;
    }

    public Estudiante getParticipante() {
        return participante;
    }

    public Estudiante getAcompañante() {
        return acompañante;
    }

    public ArrayList<Pregunta> getPreguntasSeleccionadas() {
        return preguntasSeleccionadas;
    }

    public void setTermino(TerminoAcademico termino) {
        this.termino = termino;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }

    public void setPreguntasPorNivel(int preguntasPorNivel) {
        this.preguntasPorNivel = preguntasPorNivel;
    }

    public void setParticipante(Estudiante participante) {
        this.participante = participante;
    }

    public void setAcompañante(Estudiante acompañante) {
        this.acompañante = acompañante;
    }

    public void setPreguntasSeleccionadas(ArrayList<Pregunta> preguntasSeleccionadas) {
        this.preguntasSeleccionadas = preguntasSeleccionadas;
    }
    
    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
    
    public void usoDeAlgunComodin(int i){
        System.out.println("COmodines disponibles: ");
        if(boolean50_50) System.out.println("1. Comodín 50/50");
        if(booleanConsultarCompañero) System.out.println("2. Comodín consulta al compañero");
        if(booleanConsultarSalon) System.out.println("3. Comodín conulsta al salón");
        if(!(boolean50_50 || booleanConsultarCompañero || booleanConsultarSalon)){
            System.out.println("No tiene comodines para usar");
        }else{
            switch(i){
                case 1:
                    usarComodin50_50();
                    System.out.println("Se usó el comodín 50/50");
                    break;
                case 2:
                    usarComodinConsultarCompañero();
                    System.out.println("Se usó del comodín Consulta al compañero");
                    break;
                case 3:
                    usarComodinConsultarSalon();
                    System.out.println("Se usó el comodín Consulta al curso:");
                    break;
            }
        }
    }
}
