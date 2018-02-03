package com.statics.util;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

public class Cadenas {

    private HttpServletRequest _request;

    public Cadenas() {
    }

    public Cadenas(HttpServletRequest _request) {
        this._request = _request;
    }

    public void setRequest(HttpServletRequest _request) {
        this._request = _request;
    }

    public String getvariable(String nombre) throws UnsupportedEncodingException {
        String variable = "";
        if (!Compara(notNull(this._request.getParameter(nombre), ""), "")) {
            variable = new String(notEmpty(this._request.getParameter(nombre)).getBytes("UTF-8"), "UTF-8");
        }
        return variable;
    }

    public String armaCadena(String[] cadena) {
        String retorno = null;
        for (int a = 0; a < cadena.length; a++) {
            retorno = retorno + cadena[a] + ",";
        }
        retorno = retorno.substring(0, retorno.length() - 1);
        return retorno;
    }

    public String writter(String str) throws UnsupportedEncodingException {
        String stringbuffer = new String();
        if (str != null) {
            ProcesaCaracEspeciales cadenas = new ProcesaCaracEspeciales();
            stringbuffer = ProcesaCaracEspeciales.quitarEspeciales(str);
        } else {
            stringbuffer = "NULO";
        }
        return stringbuffer;
    }

    public String notEmpty(String str) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = "";
        } else {
            retorno = str;
        }
        return retorno;
    }

    public String getParrafo(String str) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = "";
        } else {
            retorno = str;
        }
        retorno = "<p>" + retorno + "</p>";
        return retorno;
    }

    public String notEmpty(String str, String cambio) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = cambio;
        } else {
            retorno = str;
        }
        return retorno;
    }

    public String notNull(String str) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = "";
        } else {
            retorno = str;
        }
        return writter(retorno);
    }

    public String notNull(String str, String cambio) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = cambio;
        } else {
            retorno = str;
        }
        return writter(retorno);
    }

    public String getNumber(Object number) {
        String str = String.valueOf(number);

        String numberFormat = "###,###,###,###";
        DecimalFormat formatter = new DecimalFormat(numberFormat);
        double value;
        try {
            value = Double.parseDouble(str);
        } catch (Throwable t) {
            return null;
        }
        return formatter.format(value);
    }

    public String getNumberDecimales(Object number) {
        String str = String.valueOf(number);

        String numberFormat = "###,###,###,###.##";
        DecimalFormat formatter = new DecimalFormat(numberFormat);
        double value;
        try {
            value = Double.parseDouble(str);
        } catch (Throwable t) {
            return null;
        }
        return formatter.format(value);
    }

    public String getDecimales(Object number) {
        String str = String.valueOf(number);

        String numberFormat = "###,###,###,###.##";
        DecimalFormat formatter = new DecimalFormat(numberFormat);
        double value;
        try {
            value = Double.parseDouble(str);
        } catch (Throwable t) {
            return null;
        }
        return formatter.format(value);
    }

    public String ConstruirCadena(String[] cadena) {
        String texto = "";
        for (int a = 0; a < cadena.length; a++) {
            texto = texto + cadena[a] + ",";
        }
        texto = texto + "0";
        return texto;
    }

    public boolean Compara(String a, String b) {
        boolean result = false;
        if (a.compareTo(b) == 0) {
            result = true;
        }
        return result;
    }

    public String ArmarCadena(String cadena, int longitud, String caracter) {
        String retorno = "";
        int i = longitud - cadena.length();
        int j = 0;
        while (j < i) {
            retorno = retorno + caracter;
            j++;
        }
        retorno = cadena + retorno;
        return retorno;
    }

    public String CaracterIzq(String cadena, int longitud, String caracter) {
        String retorno = "";
        int i = longitud - cadena.length();
        int j = 0;
        while (j < i) {
            retorno = retorno + caracter;
            j++;
        }
        retorno = retorno + cadena;
        return retorno;
    }

    public static String addSlashes(String str) {
        if (str == null) {
            return "";
        }
        StringBuffer s = new StringBuffer(str);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\'') {
                s.insert(i++, '\\');
            }
        }
        return s.toString();
    }

    public static String implode(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    public String html(String str, String cambio) throws UnsupportedEncodingException {
        String retorno = "";
        if (str == null) {
            retorno = cambio;
        } else {
            retorno = str;
        }
        return retorno;
    }
}
