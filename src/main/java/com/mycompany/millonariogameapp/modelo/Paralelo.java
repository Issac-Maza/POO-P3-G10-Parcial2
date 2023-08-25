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
public class Paralelo implements Serializable{
    private Materia materia;
    private TerminoAcademico termino;
    private int numero;
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();
    
    public Paralelo(Materia materia, TerminoAcademico termino, int numero){
        this.materia = materia;
        this.termino = termino;
        this.numero = numero;
    }
    
    public void cargarEstudiante(Estudiante est){
        this.estudiantes = new ArrayList<Estudiante>();
        this.estudiantes.add(est);
    }

    public Materia getMateria() {
        return materia;
    }

    public TerminoAcademico getTermino() {
        return termino;
    }

    public int getNumero() {
        return numero;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public void setTermino(TerminoAcademico termino) {
        this.termino = termino;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    @Override
    public String toString() {
        return "Paralelo{" + "materia= " + materia.getNombre() +"\n" 
                + ", termino= " + termino.toString() +"\n" 
                + ", numero= " + numero +"\n" ;
                //  + ", estudiantes= " + estudiantes +'}' + "\n";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            Paralelo other = (Paralelo) obj;
            return this.numero == other.numero;
        }else{
            return false;
        }
    }
    
}
