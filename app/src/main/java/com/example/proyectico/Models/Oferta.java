package com.example.proyectico.Models;

public class Oferta {
    private String IdOferta;
    private String Titulo;
    private String NombreEmpresa;
    private String Salario;
    private String Descrpcion;
    private String Requisitos;
    private String Otros;
    private String fecharegistro;
    private long timestamp;

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
    }

    public String getIdOferta() {
        return IdOferta;
    }

    public void setIdOferta(String idOferta) {
        this.IdOferta = idOferta;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        this.Titulo = titulo;
    }

    public String getNombreEmpresa() {
        return NombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.NombreEmpresa = nombreEmpresa;
    }

    public String getSalario() {
        return Salario;
    }

    public void setSalario(String salario) {
        this.Salario = salario;
    }

    public String getDescrpcion() {
        return Descrpcion;
    }

    public void setDescrpcion(String descrpcion) {
        this.Descrpcion = descrpcion;
    }

    public String getRequisitos() {
        return Requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.Requisitos = requisitos;
    }

    public String getOtros() {
        return Otros;
    }

    public void setOtros(String otros) {
        this.Otros = otros;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
