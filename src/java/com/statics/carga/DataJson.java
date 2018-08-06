/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statics.carga;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class DataJson {
    String min;
    String max;
    String nombreGraphic;
    String tipo;
    List<DataUnit> datos = new ArrayList();
    String concatX;

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
    
    public String getConcatX() {
        return concatX;
    }

    public void setConcatX(String concatX) {
        this.concatX = concatX;
    }
    

    public String getNombreGraphic() {
        return nombreGraphic;
    }

    public void setNombreGraphic(String nombreGraphic) {
        this.nombreGraphic = nombreGraphic;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<DataUnit> getDatos() {
        return datos;
    }

    public void setDatos(List<DataUnit> datos) {
        this.datos = datos;
    }
      
    
   
public class DataUnit{
    String colorBorde;
    String colorFondo;
    int porcentaje;
String unidadMedida;
String label;
String x;
String concatDatos;
int color;
double datoPromediado;

        public String getColorBorde() {
            return colorBorde;
        }

        public void setColorBorde(String colorBorde) {
            this.colorBorde = colorBorde;
        }

        public String getColorFondo() {
            return colorFondo;
        }

        public void setColorFondo(String colorFondo) {
            this.colorFondo = colorFondo;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public double getDatoPromediado() {
            return datoPromediado;
        }

        public void setDatoPromediado(double datoPromediado) {
            this.datoPromediado = datoPromediado;
        }

        public String getConcatDatos() {
            return String.join(",", datos);
        }

        public void setConcatDatos(String concatDatos) {
            this.concatDatos = concatDatos;
        }

    List<String> datos = new ArrayList();
    
    List<String> Fechas = new ArrayList();

        public int getPorcentaje() {
            return porcentaje;
        }

        public void setPorcentaje(int porcentaje) {
            this.porcentaje = porcentaje;
        }

        public String getUnidadMedida() {
            return unidadMedida;
        }

        public void setUnidadMedida(String unidadMedida) {
            this.unidadMedida = unidadMedida;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getX() {
            return x;
        }

        public void setX(String x) {
            this.x = x;
        }

        public List<String> getDatos() {
            return datos;
        }

        public void setDatos(List<String> datos) {
            this.datos = datos;
        }

        public List<String> getFechas() {
            return Fechas;
        }

        public void setFechas(List<String> Fechas) {
            this.Fechas = Fechas;
        }

    
}
    
    
}


