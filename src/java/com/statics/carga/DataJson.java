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
    String nombreGraphic;
    String tipo;
      List<DataUnit> datos = new ArrayList();

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
Double maxValue;
    String unidadMedida;
String label;
String x;
    List<String> datos = new ArrayList();
    List<String> Fechas = new ArrayList();

        public Double getMaxValue() {
            return maxValue;
        }

        public void setMaxValue(Double maxValue) {
            this.maxValue = maxValue;
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


