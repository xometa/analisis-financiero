/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author jsaul
 */
public class Acumulador {

    private int anio;
    private BigDecimal dAnual;
    private BigDecimal dAcumulada;
    private BigDecimal vLibros;
    private BigDecimal dDeducible;

    public Acumulador() {
    }

    public Acumulador(int anio, BigDecimal dAnual, BigDecimal dAcumulada, BigDecimal vLibros) {
        this.anio = anio;
        this.dAnual = dAnual;
        this.dAcumulada = dAcumulada;
        this.vLibros = vLibros;
    }

    public Acumulador(int anio, BigDecimal dAnual, BigDecimal dAcumulada, BigDecimal vLibros, BigDecimal dDeducible) {
        this.anio = anio;
        this.dAnual = dAnual;
        this.dAcumulada = dAcumulada;
        this.vLibros = vLibros;
        this.dDeducible = dDeducible;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public BigDecimal getdAnual() {
        return dAnual;
    }

    public void setdAnual(BigDecimal dAnual) {
        this.dAnual = dAnual;
    }

    public BigDecimal getdAcumulada() {
        return dAcumulada;
    }

    public void setdAcumulada(BigDecimal dAcumulada) {
        this.dAcumulada = dAcumulada;
    }

    public BigDecimal getvLibros() {
        return vLibros;
    }

    public void setvLibros(BigDecimal vLibros) {
        this.vLibros = vLibros;
    }

    public BigDecimal getdDeducible() {
        return dDeducible;
    }

    public void setdDeducible(BigDecimal dDeducible) {
        this.dDeducible = dDeducible;
    }

    public BigDecimal getdDeduciblePeriodo() {
        return new BigDecimal(this.dDeducible.floatValue() * this.vLibros.floatValue()).setScale(2, RoundingMode.HALF_UP);
    }
}
