package com.statics.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Fechas {

    public  static Date getFechaHora() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }

    public static Calendar retornaCalendar(String cadena) {
        Calendar cal = null;

        try {
            DateFormat formatter = new SimpleDateFormat();
            Date date = formatter.parse(cadena);
            cal = Calendar.getInstance();
            cal.setTime(date);
        } catch (java.text.ParseException e) {
        }
        return cal;
    }

    
    
    
    
    
    public static Date retornaDate(String cadena) {
        Date date = null;

        try {
            DateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            date = formatter.parse(cadena);
        } catch (java.text.ParseException e) {
        }
        return date;
    }
    public static Date retornaDate(String cadena, String format) {
        final long start = System.currentTimeMillis();
        Date date = null;
        try {
        if(cadena.split(" ").length==1 && format.equals("dd/MM/yyyy HH:mm:ss")){
            cadena=cadena.concat(" 00:00:00");
        }
            DateFormat formatter = new SimpleDateFormat(format);
            date=formatter.parse(cadena);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("-->## Elapsed retornaDate " + (System.currentTimeMillis() - start) + " ms");
        return date;
    }

    public static Timestamp retornaTimeStamp(String cadena) {
        Calendar cal = retornaCalendar(cadena);
        long date = cal.getTime().getTime();
        Timestamp timeStamp = new Timestamp(date);
        return timeStamp;
    }

    public static Timestamp getFechaHoraTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        long date = calendar.getTime().getTime();
        Timestamp timeStamp = new Timestamp(date);
        return timeStamp;
    }
    static long x; 
    public static Timestamp getFechaHoraTimeStampRamdom() {
        x= x +60000;
        System.out.println(x);
        Calendar calendar = Calendar.getInstance();
        long date = calendar.getTime().getTime()+x;
        Timestamp timeStamp = new Timestamp(date);
        return timeStamp;
    }

    public static String DevuelveFormato(Timestamp t) {
        String s = "";

        if (t != null) {
            s = new SimpleDateFormat("dd-MM-yyyy").format(t);
        }
        return s;
    }
    public static String DevuelveFormato(Date t) {
        String s = "";

        if (t != null) {

            
            s = new SimpleDateFormat("dd.MM.yyyy").format(t);
        }
        return s;
    }
    public static String DevuelveFormato(Date t,String formato) {
        String s = "";

        if (t != null) {

            
            s = new SimpleDateFormat(formato).format(t);
        }
        return s;
    }
    public static String DevuelveFormatoHora(Date t) {
        String s = "";

        if (t != null) {

            
            s = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(t);
        }
        return s;
    }

    public static String DevuelveAnio(Timestamp t) {
        String s = "";

        if (t != null) {
            s = new SimpleDateFormat("yyyy").format(t);
        }
        return s;
    }

    public static String FechaLetras(Timestamp t) {
        String s = "";

        if (t != null) {
            s = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(t);
        }
        return s;
    }
    public static String FechaLetrasHora(Timestamp t) {
        String s = "";

        if (t != null) {
            s = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy '  ' hh':'mm':'ss a").format(t);
        }
        return s;
    }
    public static String FechaLetrasHora(Date t) {
        String s = "";

        if (t != null) {
            s = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy '  ' hh':'mm':'ss a").format(t);
        }
        return s;
    }

    public static Timestamp StringToTimeStamp(String cadena) {
        Calendar cal = retornaCalendar(cadena);
        long date = cal.getTime().getTime();
        Timestamp timeStamp = new Timestamp(date);
        return timeStamp;
    }

    public static String FechaLetras(String t) {
        String s = "";

        if (t != null) {
            s = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy").format(StringToTimeStamp(t));
        }
        return s;
    }

    public static String getCadenaArchivo() {
        String nombre = getFechaHoraTimeStamp().toString().replace("-", "").replace(":", "").replace(".", "").replace(" ", "");
        nombre = nombre.substring(0, 8);
        return nombre;
    }

    public static String getCadena() {
        String nombre = getFechaHoraTimeStamp().toString().replace("-", "").replace(":", "").replace(".", "").replace(" ", "");
        return nombre;
    }

    public static int compare(Timestamp t1, Timestamp t2) {
        long l1 = t1.getTime();
        long l2 = t2.getTime();
        if (l2 > l1) {
            return 1;
        }
        if (l1 > l2) {
            return -1;
        }
        return 0;
    }
    public static int compare(Date t1, Date t2) {
            System.out.println("--> "+t1+" @@ "+t2);
        
        long l1 = t1.getTime();
        long l2 = t2.getTime();
//        System.out.println("fechas::::"+ l1+ " : " +l2);
        if (l2 > l1) {
            System.out.println("l2 mayor::1");
            return 1;
        }
        if (l1 > l2) {
            System.out.println("l1 mayor::-1");
            return -1;
        }
        return 0;
    }
    
/**
	 * add days to date in java
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
				
		return cal.getTime();
	}
	
	/**
	 * subtract days to date in java
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date subtractDays(Date date, int days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
				
		return cal.getTime();
	}

    
    public static String getDiffDates(Date fechaInicio, Date fechaFin) {

	// Fecha inicio

	Calendar calendarInicio = Calendar.getInstance();

	calendarInicio.setTime(fechaInicio);

	int diaInicio = calendarInicio.get(Calendar.DAY_OF_MONTH);

	int mesInicio = calendarInicio.get(Calendar.MONTH) + 1; // 0 Enero, 11 Diciembre

	int anioInicio = calendarInicio.get(Calendar.YEAR);

 

	// Fecha fin

	Calendar calendarFin = Calendar.getInstance();

	calendarFin.setTime(fechaFin);

	int diaFin = calendarFin.get(Calendar.DAY_OF_MONTH);

	int mesFin = calendarFin.get(Calendar.MONTH) + 1; // 0 Enero, 11 Diciembre

	int anioFin = calendarFin.get(Calendar.YEAR);

 

	int anios = 0;

	int mesesPorAnio = 0;

	int diasPorMes = 0;

	int diasTipoMes = 0;

 

	//

	// Calculo de días del mes

	//

	if (mesInicio == 2) {

		// Febrero

		if ((anioFin % 4 == 0) && ((anioFin % 100 != 0) || (anioFin % 400 == 0))) {

			// Bisiesto

			diasTipoMes = 29;

		} else {

			// No bisiesto

			diasTipoMes = 28;

		}

	} else if (mesInicio <= 7) {

		// De Enero a Julio los meses pares tienen 30 y los impares 31

		if (mesInicio % 2 == 0) {

			diasTipoMes = 30;

		} else {

			diasTipoMes = 31;

		}

	} else if (mesInicio > 7) {

		// De Julio a Diciembre los meses pares tienen 31 y los impares 30

		if (mesInicio % 2 == 0) {

			diasTipoMes = 31;

		} else {

			diasTipoMes = 30;

		}

	}

 

 

	//

	// Calculo de diferencia de año, mes y dia

	//

	if ((anioInicio > anioFin) || (anioInicio == anioFin && mesInicio > mesFin)

			|| (anioInicio == anioFin && mesInicio == mesFin && diaInicio > diaFin)) {

		// La fecha de inicio es posterior a la fecha fin

		// System.out.println("La fecha de inicio ha de ser anterior a la fecha fin");

		return "";

	} else {

		if (mesInicio <= mesFin) {

			anios = anioFin - anioInicio;

			if (diaInicio <= diaFin) {

				mesesPorAnio = mesFin - mesInicio;

				diasPorMes = diaFin - diaInicio;

			} else {

				if (mesFin == mesInicio) {

					anios = anios - 1;

				}

				mesesPorAnio = (mesFin - mesInicio - 1 + 12) % 12;

				diasPorMes = diasTipoMes - (diaInicio - diaFin);

			}

		} else {

			anios = anioFin - anioInicio - 1;

			System.out.println(anios);

			if (diaInicio > diaFin) {

				mesesPorAnio = mesFin - mesInicio - 1 + 12;

				diasPorMes = diasTipoMes - (diaInicio - diaFin);

			} else {

				mesesPorAnio = mesFin - mesInicio + 12;

				diasPorMes = diaFin - diaInicio;

			}

		}

	}

//	System.out.println("Han transcurrido " + anios + " Años, " + mesesPorAnio + " Meses y " + diasPorMes + " Días.");		

 

	//

	// Totales

	//

	String returnValue = anios + " Años, " + mesesPorAnio + " Meses y " + diasPorMes + " Días." ;

 



 

	return returnValue;

}
          
    
    
}
