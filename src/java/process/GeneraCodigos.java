/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import entities.Cjuridico;
import entities.Cnatural;
import entities.Departamento;
import entities.Sucursal;
import entities.Tipoactivo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jsaul
 */
public class GeneraCodigos {

    final int milisecondsByDay = 86400000;

    public GeneraCodigos() {
    }

    public String codigo(String cadena, int entero) {
        String codigo = cadena;
        if (entero > 0 && entero < 10) {
            codigo += "0000" + entero;
        } else if (entero >= 10 && entero < 100) {
            codigo += "000" + entero;
        } else if (entero >= 100 && entero < 1000) {
            codigo += "00" + entero;
        } else if (entero >= 1000 && entero < 10000) {
            codigo += "0" + entero;
        } else {
            codigo += String.valueOf(entero);
        }
        return codigo;
    }

    public String codigok(String cadena, int entero) {
        String codigo = cadena;
        if (entero > 0 && entero < 10) {
            codigo += "00" + entero;
        } else if (entero >= 10 && entero < 100) {
            codigo += "0" + entero;
        } else {
            codigo += String.valueOf(entero);
        }
        return codigo;
    }

    public int autoincrementableFiador(List<Cnatural> lista) {
        int auto_increment = 0;

        if (lista.size() > 0) {
            auto_increment = lista.get(lista.size() - 1).getId() + 1;
        } else {
            auto_increment = 1;
        }
        return auto_increment;
    }

    public int autoincrementableJuridico(List<Cjuridico> lista) {
        int auto_increment = 0;

        if (lista.size() > 0) {
            auto_increment = lista.get(lista.size() - 1).getId() + 1;
        } else {
            auto_increment = 1;
        }
        return auto_increment;
    }

    public int autoincrementableDepartamento(List<Departamento> lista) {
        int auto_increment = 0;

        if (lista.size() > 0) {
            auto_increment = lista.get(lista.size() - 1).getId() + 1;
        } else {
            auto_increment = 1;
        }
        return auto_increment;
    }

    public int autoincrementableSucursal(List<Sucursal> lista) {
        int auto_increment = 0;

        if (lista.size() > 0) {
            auto_increment = lista.get(lista.size() - 1).getId() + 1;
        } else {
            auto_increment = 1;
        }
        return auto_increment;
    }

    public int autoincrementableTipo(List<Tipoactivo> lista) {
        int auto_increment = 0;

        if (lista.size() > 0) {
            auto_increment = lista.get(lista.size() - 1).getId() + 1;
        } else {
            auto_increment = 1;
        }
        return auto_increment;
    }

    public int obtenerDias(Date a, Date b) {
        Calendar inicio = getCalendar(a);
        Calendar fin = getCalendar(b);
        inicio.set(Calendar.HOUR, 0);
        inicio.set(Calendar.HOUR_OF_DAY, 0);
        inicio.set(Calendar.MINUTE, 0);
        inicio.set(Calendar.SECOND, 0);

        fin.set(Calendar.HOUR, 0);
        fin.set(Calendar.HOUR_OF_DAY, 0);
        fin.set(Calendar.MINUTE, 0);
        fin.set(Calendar.SECOND, 0);

        long inicioMS = inicio.getTimeInMillis();
        long finMS = fin.getTimeInMillis();

        int dias = (int) ((Math.abs(finMS - inicioMS)) / milisecondsByDay);
        dias++;
        return dias;
    }

    public int obtenerMeses(Date a, Date b) {
        Calendar inicio = getCalendar(a);
        Calendar fin = getCalendar(b);
        int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        int difM = (difA * 12) + (fin.get(Calendar.MONTH) - inicio.get(Calendar.MONTH));

        if (fin.get(Calendar.DATE) < inicio.get(Calendar.DATE)) {
            difM--;
        }

        return difM;
    }

    public int obtenerAños(Date a, Date b) {
        Calendar inicio = getCalendar(a);
        Calendar fin = getCalendar(b);
        int difA = fin.get(Calendar.YEAR) - inicio.get(Calendar.YEAR);
        if (inicio.get(Calendar.MONTH) > fin.get(Calendar.MONTH)
                || (inicio.get(Calendar.MONTH) == fin.get(Calendar.MONTH)
                && inicio.get(Calendar.DATE) > fin.get(Calendar.DATE))) {
            difA--;
        }
        return difA;
    }

    public Date fechaFinal(Date date, int vidautil) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar inicio = getCalendar(date);
        String fechaFin = "" + (inicio.get(Calendar.YEAR) + vidautil) + "-" + (inicio.get(Calendar.MONTH) + 1) + "-" + inicio.get(Calendar.DAY_OF_MONTH);
        Date fecha = null;
        try {
            fecha = sdf.parse(fechaFin);
        } catch (ParseException ex) {
            Logger.getLogger(GeneraCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }

    public static Calendar getCalendar(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(sdf.format(date)));
        } catch (ParseException ex) {
            Logger.getLogger(GeneraCodigos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cal;
    }
}
