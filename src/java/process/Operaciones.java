/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import entities.Activobaja;
import entities.Activofijo;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import models.ModeloActivoFijo;

/**
 *
 * @author jsaul
 */
public class Operaciones {

    //art. 30 del ISR
    //amortización para los software
    //depreciación: para los activos tangibles
    /*
    se cálcula de la siguiente manera (nuevo):
    valor del bien/años de vida útil=valor a depreciar anualmente
    
    para usado se calcula de la siguiente forma:
    precio del bien nuevo*procentaje/años vida util
     */
    private Activofijo activofijo;
    private String acccion;

    //valores de activo fijo usado
    private static double UNO = 0.80;
    private static double DOS = 0.60;
    private static double TRES = 0.40;
    private static double CUATROMAS = 0.20;

    //porcentajes maximos de depreciación permitidos
    private static double EDIFICACIONES = 0.05;
    private static double MAQUINARIA = 0.20;
    private static double VEHICULOS = 0.25;
    private static double OTROSBIENESMUEBLES = 0.50;

    //dias, meses y años transcurridos
    private int dias;
    private int meses;
    private int year;
    private boolean proceso;
    //progreso de la depreciación tanto en días, mensual y anual
    private BigDecimal progresoDias;
    private BigDecimal progresoMeses;
    private BigDecimal progresoYear;
    //monto que se depreciaria por días, mensual y anual
    private BigDecimal montoDias;
    private BigDecimal montoMeses;
    private BigDecimal montoYears;
    //acumuladores de montos, segun los días, meses y años transcurridos
    private BigDecimal acumuladoDias;
    private BigDecimal acumuladoMeses;
    private BigDecimal acumuladoYears;
    private BigDecimal deducibleYear;
    private BigDecimal deducibleMeses;
    private BigDecimal deducibleDias;
    //fecha para comparar y obtener los valores de depreciación
    private Date actual;
    private ArrayList<Acumulador> acumulador;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public Operaciones() {
        this.activofijo = new Activofijo();
        this.acccion = "";
        this.dias = 0;
        this.meses = 0;
        this.year = 0;
        this.proceso = true;
        this.progresoDias = new BigDecimal("0.00");
        this.progresoMeses = new BigDecimal("0.00");
        this.progresoYear = new BigDecimal("0.00");
        this.montoDias = new BigDecimal("0.00");
        this.montoMeses = new BigDecimal("0.00");
        this.montoYears = new BigDecimal("0.00");
        this.acumuladoDias = new BigDecimal("0.00");
        this.acumuladoMeses = new BigDecimal("0.00");
        this.acumuladoYears = new BigDecimal("0.00");
        this.deducibleYear = new BigDecimal("0.00");
        this.deducibleMeses = new BigDecimal("0.00");
        this.deducibleDias = new BigDecimal("0.00");
        this.actual = new Date();
        this.acumulador = new ArrayList<>();
    }

    public Operaciones(Activofijo activofijo) {
        this.activofijo = activofijo;
        this.acccion = "";
        this.dias = 0;
        this.meses = 0;
        this.year = 0;
        this.proceso = true;
        this.progresoDias = new BigDecimal("0.00");
        this.progresoMeses = new BigDecimal("0.00");
        this.progresoYear = new BigDecimal("0.00");
        this.montoDias = new BigDecimal("0.00");
        this.montoMeses = new BigDecimal("0.00");
        this.montoYears = new BigDecimal("0.00");
        this.acumuladoDias = new BigDecimal("0.00");
        this.acumuladoMeses = new BigDecimal("0.00");
        this.acumuladoYears = new BigDecimal("0.00");
        this.deducibleYear = new BigDecimal("0.00");
        this.deducibleMeses = new BigDecimal("0.00");
        this.deducibleDias = new BigDecimal("0.00");
        this.actual = new Date();
        this.acumulador = new ArrayList<>();
    }

    public Operaciones(Activofijo activofijo, String acccion) {
        this.activofijo = activofijo;
        this.acccion = acccion;
        this.dias = 0;
        this.meses = 0;
        this.year = 0;
        this.proceso = true;
        this.progresoDias = new BigDecimal("0.00");
        this.progresoMeses = new BigDecimal("0.00");
        this.progresoYear = new BigDecimal("0.00");
        this.montoDias = new BigDecimal("0.00");
        this.montoMeses = new BigDecimal("0.00");
        this.montoYears = new BigDecimal("0.00");
        this.acumuladoDias = new BigDecimal("0.00");
        this.acumuladoMeses = new BigDecimal("0.00");
        this.acumuladoYears = new BigDecimal("0.00");
        this.deducibleYear = new BigDecimal("0.00");
        this.deducibleMeses = new BigDecimal("0.00");
        this.deducibleDias = new BigDecimal("0.00");
        this.actual = new Date();
        this.acumulador = new ArrayList<>();
    }

    public Activofijo getActivofijo() {
        return activofijo;
    }

    public void setActivofijo(Activofijo activofijo) {
        this.activofijo = activofijo;
    }

    public String getAcccion() {
        return acccion;
    }

    public void setAcccion(String acccion) {
        this.acccion = acccion;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getMeses() {
        return meses;
    }

    public void setMeses(int meses) {
        this.meses = meses;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isProceso() {
        return proceso;
    }

    public void setProceso(boolean proceso) {
        this.proceso = proceso;
    }

    public BigDecimal getProgresoDias() {
        return progresoDias;
    }

    public void setProgresoDias(BigDecimal progresoDias) {
        this.progresoDias = progresoDias;
    }

    public BigDecimal getProgresoMeses() {
        return progresoMeses;
    }

    public void setProgresoMeses(BigDecimal progresoMeses) {
        this.progresoMeses = progresoMeses;
    }

    public BigDecimal getProgresoYear() {
        return progresoYear;
    }

    public void setProgresoYear(BigDecimal progresoYear) {
        this.progresoYear = progresoYear;
    }

    public BigDecimal getMontoDias() {
        return montoDias;
    }

    public void setMontoDias(BigDecimal montoDias) {
        this.montoDias = montoDias;
    }

    public BigDecimal getMontoMeses() {
        return montoMeses;
    }

    public void setMontoMeses(BigDecimal montoMeses) {
        this.montoMeses = montoMeses;
    }

    public BigDecimal getMontoYears() {
        return montoYears;
    }

    public void setMontoYears(BigDecimal montoYears) {
        this.montoYears = montoYears;
    }

    public BigDecimal getAcumuladoDias() {
        return acumuladoDias;
    }

    public void setAcumuladoDias(BigDecimal acumuladoDias) {
        this.acumuladoDias = acumuladoDias;
    }

    public BigDecimal getAcumuladoMeses() {
        return acumuladoMeses;
    }

    public void setAcumuladoMeses(BigDecimal acumuladoMeses) {
        this.acumuladoMeses = acumuladoMeses;
    }

    public BigDecimal getAcumuladoYears() {
        return acumuladoYears;
    }

    public void setAcumuladoYears(BigDecimal acumuladoYears) {
        this.acumuladoYears = acumuladoYears;
    }

    public BigDecimal getDeducibleYear() {
        return deducibleYear;
    }

    public void setDeducibleYear(BigDecimal deducibleYear) {
        this.deducibleYear = deducibleYear;
    }

    public BigDecimal getDeducibleMeses() {
        return deducibleMeses;
    }

    public void setDeducibleMeses(BigDecimal deducibleMeses) {
        this.deducibleMeses = deducibleMeses;
    }

    public BigDecimal getDeducibleDias() {
        return deducibleDias;
    }

    public void setDeducibleDias(BigDecimal deducibleDias) {
        this.deducibleDias = deducibleDias;
    }

    public Date getActual() {
        return actual;
    }

    public void setActual(Date actual) {
        this.actual = actual;
    }

    public ArrayList<Acumulador> getAcumulador() {
        return acumulador;
    }

    public void setAcumulador(ArrayList<Acumulador> acumulador) {
        this.acumulador = acumulador;
    }

    public void calcularDepreciacion() {
        GeneraCodigos gc = new GeneraCodigos();
        Activobaja afb;
        ModeloActivoFijo maf = new ModeloActivoFijo();

        //obtenemos la fecha final de vida útil del activo
        Date fechaFin = gc.fechaFinal(this.activofijo.getFechaadquisicion(), this.activofijo.getVidautil());
        //System.out.println(sdf.format(fechaFin));
        //SE COMPRUEBA SI SU VIDA ÚTIL YA TERMINO O SI ESTE SE DONO, EN CASO DE QUE NO SEA ASI
        //SE SEGUIRA OBTENIENDO LA DIFERENCIA DE DÍAS MESES Y AÑOS CON LA FECHA ACTUAL

        //COMPROBANDO SI EL ACTIVO SE DIO DE BAJA ANTES QUE FINALIZARA SU VIDA ÚTIL
        if (this.activofijo.getActivobajas().size() > 0) {
            afb = maf.recuperaAFBaja(this.activofijo.getActivobajas());
            if (afb != null) {
                //si la fecha dada de baja es menor a la fecha final de vida útil del activo
                //de no ser así quiere decir que es mayor y se debe calcular hasta la vida final del activo,
                //esto quiere decir que se dono o vendio, ya con la vida finalizada
                if (afb.getFecha().before(fechaFin)) {
                    this.proceso = false;
                    dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), afb.getFecha());
                    meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), afb.getFecha());
                    year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), afb.getFecha());
                    this.actual = afb.getFecha();
                } else {
                    this.proceso = false;
                    dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), fechaFin);
                    meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), fechaFin);
                    year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), fechaFin);
                    this.actual = fechaFin;
                }
            }
        } else if (actual.after(fechaFin)) {
            //VERIFICANDO SI LA VIDA ÚTIL LLEGO A SU FÍN (FECHA ACTUAL SUPERA LA VIDA ÚTIL)
            this.proceso = false;
            dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), fechaFin);
            meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), fechaFin);
            year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), fechaFin);
            this.actual = fechaFin;
        } else {
            //SEGUIR COMPROBANDO CON LA FECHA ACTUAL
            this.proceso = true;
            dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), actual);
            meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), actual);
            year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), actual);
        }
        //System.out.println("Diferencias: " + dias + " " + meses + " " + year);

        //PROCESO PARA CÁLCULO DE LA DEPRECIACIÓN
        double porcentajeYears = 0;
        double deducible = 0;
        BigDecimal pYear = new BigDecimal("0.00");
        BigDecimal pMeses = new BigDecimal("0.00");
        BigDecimal pDias = new BigDecimal("0.00");
        BigDecimal acum = new BigDecimal("0.00");
        BigDecimal resto = new BigDecimal("0.00");
        BigDecimal precioActivo = new BigDecimal("0.00");
        BigDecimal ajustado = new BigDecimal("0.00");

        //obteniendo el tipo de bien
        switch (this.activofijo.getTipoactivo().getNombre().toUpperCase()) {
            case "EDIFICACIONES":
                deducible = (Operaciones.EDIFICACIONES);
                break;
            case "MAQUINARIA":
                deducible = (Operaciones.MAQUINARIA);
                break;
            case "VEHICULOS":
                deducible = (Operaciones.VEHICULOS);
                break;
            case "OTROS BIENES MUEBLES":
                deducible = (Operaciones.OTROSBIENESMUEBLES);
                break;
            default:
                deducible = 0;
                break;
        }

        if (this.activofijo.getProcedencia().equals("Nuevo")) {

            //PRECIO DEL ACTIVO FIJO SIN AJUSTAR DE UN BIEN NUEVO
            precioActivo = BigDecimal.valueOf(this.activofijo.getPrecio().floatValue()).setScale(2, RoundingMode.HALF_UP);
            ajustado = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);

            //generando lista de depreciación anual
            this.acumulador.add(new Acumulador(0, new BigDecimal("0.00"), new BigDecimal("0.00"), precioActivo, new BigDecimal("0.00")));

            //depreciación anual
            this.montoYears = BigDecimal.valueOf(precioActivo.floatValue() / this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);
            pYear = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);

            //depreciación mensual
            this.montoMeses = BigDecimal.valueOf(this.montoYears.floatValue() / 12).setScale(2, RoundingMode.HALF_UP);
            pMeses = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);

            //depreaciación diaria
            this.montoDias = BigDecimal.valueOf(this.montoYears.floatValue() / 360).setScale(2, RoundingMode.HALF_UP);
            pDias = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);

        } else if (this.activofijo.getProcedencia().equals("Usado")) {

            //si el bien es usado hay que deducir con los procentaje
            switch (this.activofijo.getVidautil()) {
                case 1:
                    porcentajeYears = (Operaciones.UNO);
                    break;
                case 2:
                    porcentajeYears = (Operaciones.DOS);
                    break;
                case 3:
                    porcentajeYears = (Operaciones.TRES);
                    break;
                case 4:
                    porcentajeYears = (Operaciones.CUATROMAS);
                    break;
                default:
                    porcentajeYears = (Operaciones.CUATROMAS);
                    break;
            }

            //PRECIO DEL ACTIVO FIJO USADO AJUSTADO
            precioActivo = BigDecimal.valueOf((this.activofijo.getPrecio().floatValue() / this.activofijo.getVidautil()) * porcentajeYears);
            ajustado = BigDecimal.valueOf(precioActivo.floatValue() * this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);

            //generando lista de depreciación anual
            this.acumulador.add(new Acumulador(0, new BigDecimal("0.00"), new BigDecimal("0.00"),
                    BigDecimal.valueOf(precioActivo.floatValue() * this.activofijo.getVidautil()), new BigDecimal("0.00")));
            //depreciación anual
            //porcentajeYears = (this.activofijo.getPrecio().floatValue() / this.activofijo.getVidautil()) * porcentajeYears;
            this.montoYears = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);
            pYear = BigDecimal.valueOf(this.montoYears.floatValue() * this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);
            //this.progresoYear = BigDecimal.valueOf((this.montoYears.floatValue() / (this.montoYears.floatValue() * this.activofijo.getVidautil())) * 100).setScale(2, RoundingMode.HALF_UP);

            //depreciación mensual
            this.montoMeses = BigDecimal.valueOf(this.montoYears.floatValue() / 12).setScale(2, RoundingMode.HALF_UP);
            pMeses = BigDecimal.valueOf(this.montoYears.floatValue() * this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);
            //this.progresoMeses = BigDecimal.valueOf((this.montoMeses.floatValue() / (this.montoYears.floatValue() * this.activofijo.getVidautil())) * 100).setScale(2, RoundingMode.HALF_UP);

            //depreaciación diaria
            this.montoDias = BigDecimal.valueOf(this.montoYears.floatValue() / 360).setScale(2, RoundingMode.HALF_UP);
            pDias = BigDecimal.valueOf(this.montoYears.floatValue() * this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);
            //this.progresoDias = BigDecimal.valueOf((this.montoDias.floatValue() / (this.montoYears.floatValue() * this.activofijo.getVidautil())) * 100).setScale(2, RoundingMode.HALF_UP);
        }

        if (acccion.equals("Anual")) {
            //cargando la lista de datos con la depreciación anual
            for (int i = 1; i <= year; i++) {
                acum = acum.add(new BigDecimal(this.montoYears.floatValue()).setScale(2, RoundingMode.HALF_UP));
                resto = BigDecimal.valueOf(this.acumulador.get(i - 1).getvLibros().floatValue() - this.montoYears.floatValue()).setScale(2, RoundingMode.HALF_UP);
                this.acumulador.add(new Acumulador(i, this.montoYears, acum, resto, new BigDecimal(deducible)));
            }
        } else if (acccion.equals("Mensual")) {
            //cargando la lista de datos con la depreciación mensual
            for (int i = 1; i <= meses; i++) {
                acum = acum.add(new BigDecimal(this.montoMeses.floatValue()).setScale(2, RoundingMode.HALF_UP));
                resto = BigDecimal.valueOf(this.acumulador.get(i - 1).getvLibros().floatValue() - this.montoMeses.floatValue()).setScale(2, RoundingMode.HALF_UP);
                this.acumulador.add(new Acumulador(i, this.montoMeses, acum, resto, new BigDecimal(deducible)));
            }

        } else if (acccion.equals("Diaria")) {
            //cargando la lista de datos con la depreciación diaria
            for (int i = 1; i <= dias; i++) {
                acum = acum.add(new BigDecimal(this.montoDias.floatValue()).setScale(2, RoundingMode.HALF_UP));
                resto = BigDecimal.valueOf(this.acumulador.get(i - 1).getvLibros().floatValue() - this.montoDias.floatValue()).setScale(2, RoundingMode.HALF_UP);
                this.acumulador.add(new Acumulador(i, this.montoDias, acum, resto, new BigDecimal(deducible)));
            }

        }

        //mostrando lo acumulado, por días, meses y años
        this.acumuladoYears = BigDecimal.valueOf(this.montoYears.floatValue() * year).setScale(2, RoundingMode.HALF_UP);
        this.acumuladoMeses = BigDecimal.valueOf(this.montoMeses.floatValue() * meses).setScale(2, RoundingMode.HALF_UP);
        this.acumuladoDias = BigDecimal.valueOf(this.montoDias.floatValue() * dias).setScale(2, RoundingMode.HALF_UP);

        //DEDUCIBLE DÍA,mes y año
        //System.out.println("Precio " + ajustado.floatValue() + " acumulado " + acumuladoMeses.floatValue() + " deduccion " + deducible);
        if (this.year > 0) {
            this.deducibleYear = BigDecimal.valueOf((ajustado.floatValue() - this.acumuladoYears.floatValue()) * deducible).setScale(2, RoundingMode.HALF_UP);
        }

        if (this.meses > 0) {
            this.deducibleMeses = BigDecimal.valueOf((ajustado.floatValue() - this.acumuladoMeses.floatValue()) * deducible).setScale(2, RoundingMode.HALF_UP);
        }

        if (this.dias > 0) {
            this.deducibleDias = BigDecimal.valueOf((ajustado.floatValue() - this.acumuladoDias.floatValue()) * deducible).setScale(2, RoundingMode.HALF_UP);
        }

        //progreso de depreciación
        this.progresoYear = BigDecimal.valueOf((acumuladoYears.floatValue() / pYear.floatValue()) * 100).setScale(2, RoundingMode.HALF_UP);
        this.progresoMeses = BigDecimal.valueOf((acumuladoMeses.floatValue() / pMeses.floatValue()) * 100).setScale(2, RoundingMode.HALF_UP);
        this.progresoDias = BigDecimal.valueOf((acumuladoDias.floatValue() / pDias.floatValue()) * 100).setScale(2, RoundingMode.HALF_UP);
    }

    public void calcularAmortizacion() {
        GeneraCodigos gc = new GeneraCodigos();
        Activobaja afb;
        ModeloActivoFijo maf = new ModeloActivoFijo();
        Date fechaFin = gc.fechaFinal(this.activofijo.getFechaadquisicion(), this.activofijo.getVidautil());
        if (this.activofijo.getActivobajas().size() > 0) {
            afb = maf.recuperaAFBaja(this.activofijo.getActivobajas());
            if (afb != null) {
                if (afb.getFecha().before(fechaFin)) {
                    this.proceso = false;
                    dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), afb.getFecha());
                    meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), afb.getFecha());
                    year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), afb.getFecha());
                    this.actual = afb.getFecha();
                } else {
                    this.proceso = false;
                    dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), fechaFin);
                    meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), fechaFin);
                    year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), fechaFin);
                    this.actual = fechaFin;
                }
            }
        } else if (actual.after(fechaFin)) {
            this.proceso = false;
            dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), fechaFin);
            meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), fechaFin);
            year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), fechaFin);
            this.actual = fechaFin;
        } else {
            this.proceso = true;
            dias = gc.obtenerDias(this.activofijo.getFechaadquisicion(), actual);
            meses = gc.obtenerMeses(this.activofijo.getFechaadquisicion(), actual);
            year = gc.obtenerAños(this.activofijo.getFechaadquisicion(), actual);
        }

        //PROCESO PARA CÁLCULO DE LA DEPRECIACIÓN
        double porcentajeYears = 0;
        BigDecimal pYear = new BigDecimal("0.00");
        BigDecimal pMeses = new BigDecimal("0.00");
        BigDecimal pDias = new BigDecimal("0.00");
        BigDecimal acum = new BigDecimal("0.00");
        BigDecimal resto = new BigDecimal("0.00");
        BigDecimal precioActivo = new BigDecimal("0.00");

        if (this.activofijo.getProcedencia().equals("Nuevo")) {

            //PRECIO DEL ACTIVO FIJO SIN AJUSTAR DE UN BIEN NUEVO
            precioActivo = BigDecimal.valueOf(this.activofijo.getPrecio().floatValue()).setScale(2, RoundingMode.HALF_UP);

            //generando lista de amortización anual
            this.acumulador.add(new Acumulador(0, new BigDecimal("0.00"), new BigDecimal("0.00"), precioActivo, new BigDecimal("0.00")));

            //depreciación anual
            this.montoYears = BigDecimal.valueOf(precioActivo.floatValue() / this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);
            pYear = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);

            //depreciación mensual
            this.montoMeses = BigDecimal.valueOf(this.montoYears.floatValue() / 12).setScale(2, RoundingMode.HALF_UP);
            pMeses = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);

            //depreaciación diaria
            this.montoDias = BigDecimal.valueOf(this.montoYears.floatValue() / 360).setScale(2, RoundingMode.HALF_UP);
            pDias = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);

        } else if (this.activofijo.getProcedencia().equals("Usado")) {
            //si el bien es usado hay que deducir con los procentaje
            switch (this.activofijo.getVidautil()) {
                case 1:
                    porcentajeYears = (Operaciones.UNO);
                    break;
                case 2:
                    porcentajeYears = (Operaciones.DOS);
                    break;
                case 3:
                    porcentajeYears = (Operaciones.TRES);
                    break;
                case 4:
                    porcentajeYears = (Operaciones.CUATROMAS);
                    break;
                default:
                    porcentajeYears = (Operaciones.CUATROMAS);
                    break;
            }

            //PRECIO DEL ACTIVO FIJO USADO AJUSTADO
            precioActivo = BigDecimal.valueOf((this.activofijo.getPrecio().floatValue() / this.activofijo.getVidautil()) * porcentajeYears);

            //generando lista de amortización anual
            this.acumulador.add(new Acumulador(0, new BigDecimal("0.00"), new BigDecimal("0.00"),
                    BigDecimal.valueOf(precioActivo.floatValue() * this.activofijo.getVidautil()), new BigDecimal("0.00")));
            //depreciación anual
            this.montoYears = BigDecimal.valueOf(precioActivo.floatValue()).setScale(2, RoundingMode.HALF_UP);
            pYear = BigDecimal.valueOf(this.montoYears.floatValue() * this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);

            //depreciación mensual
            this.montoMeses = BigDecimal.valueOf(this.montoYears.floatValue() / 12).setScale(2, RoundingMode.HALF_UP);
            pMeses = BigDecimal.valueOf(this.montoYears.floatValue() * this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);

            //depreaciación diaria
            this.montoDias = BigDecimal.valueOf(this.montoYears.floatValue() / 360).setScale(2, RoundingMode.HALF_UP);
            pDias = BigDecimal.valueOf(this.montoYears.floatValue() * this.activofijo.getVidautil()).setScale(2, RoundingMode.HALF_UP);

        }

        if (acccion.equals("Anual")) {
            //cargando la lista de datos con la depreciación anual
            for (int i = 1; i <= year; i++) {
                acum = acum.add(new BigDecimal(this.montoYears.floatValue()).setScale(2, RoundingMode.HALF_UP));
                resto = BigDecimal.valueOf(this.acumulador.get(i - 1).getvLibros().floatValue() - this.montoYears.floatValue()).setScale(2, RoundingMode.HALF_UP);
                this.acumulador.add(new Acumulador(i, this.montoYears, acum, resto, new BigDecimal("0.00")));
            }
        } else if (acccion.equals("Mensual")) {
            //cargando la lista de datos con la depreciación mensual
            for (int i = 1; i <= meses; i++) {
                acum = acum.add(new BigDecimal(this.montoMeses.floatValue()).setScale(2, RoundingMode.HALF_UP));
                resto = BigDecimal.valueOf(this.acumulador.get(i - 1).getvLibros().floatValue() - this.montoMeses.floatValue()).setScale(2, RoundingMode.HALF_UP);
                this.acumulador.add(new Acumulador(i, this.montoMeses, acum, resto, new BigDecimal("0.00")));
            }

        } else if (acccion.equals("Diaria")) {
            //cargando la lista de datos con la depreciación diaria
            for (int i = 1; i <= dias; i++) {
                acum = acum.add(new BigDecimal(this.montoDias.floatValue()).setScale(2, RoundingMode.HALF_UP));
                resto = BigDecimal.valueOf(this.acumulador.get(i - 1).getvLibros().floatValue() - this.montoDias.floatValue()).setScale(2, RoundingMode.HALF_UP);
                this.acumulador.add(new Acumulador(i, this.montoDias, acum, resto, new BigDecimal("0.00")));
            }

        }

        //mostrando lo acumulado, por días, meses y años
        this.acumuladoYears = BigDecimal.valueOf(this.montoYears.floatValue() * year).setScale(2, RoundingMode.HALF_UP);
        this.acumuladoMeses = BigDecimal.valueOf(this.montoMeses.floatValue() * meses).setScale(2, RoundingMode.HALF_UP);
        this.acumuladoDias = BigDecimal.valueOf(this.montoDias.floatValue() * dias).setScale(2, RoundingMode.HALF_UP);

        //progreso de depreciación
        this.progresoYear = BigDecimal.valueOf((acumuladoYears.floatValue() / pYear.floatValue()) * 100).setScale(2, RoundingMode.HALF_UP);
        this.progresoMeses = BigDecimal.valueOf((acumuladoMeses.floatValue() / pMeses.floatValue()) * 100).setScale(2, RoundingMode.HALF_UP);
        this.progresoDias = BigDecimal.valueOf((acumuladoDias.floatValue() / pDias.floatValue()) * 100).setScale(2, RoundingMode.HALF_UP);
    }

}
