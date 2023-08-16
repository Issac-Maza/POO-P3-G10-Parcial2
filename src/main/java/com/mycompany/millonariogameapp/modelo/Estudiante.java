/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.millonariogameapp.modelo;

import java.io.Serializable;

/**
 *
 * @author maza-
 */
public class Estudiante implements Serializable{
    private String nMatricula;
    private String nombre;
    private String correo;

    public Estudiante(String nMatricula, String nombre, String correo) {
        this.nMatricula = nMatricula;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getnMatricula() {
        return nMatricula;
    }

    public void setnMatricula(String nMatricula) {
        this.nMatricula = nMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    @Override
    public String toString(){
        return "Número de matricula: "+ nMatricula +"\n"+"Nombre: " + nombre +"\n"+"Correo: "+ correo;
    }
    
    
}
