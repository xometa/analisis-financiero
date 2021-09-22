/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Activobaja;
import entities.Activofijo;
import entities.Departamento;
import entities.Eresultado;
import entities.Sucursal;
import entities.Tipoactivo;
import hibernateutil.HibernateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import process.Operaciones;

/**
 *
 * @author jsaul
 */
public class ModeloActivoFijo {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloActivoFijo() {
    }

    /*metodos de acceso*/
    private void iniciaOperacion() throws HibernateException {
        sf = HibernateUtil.getSessionFactory();
        ses = sf.openSession();
        tra = ses.beginTransaction();
    }

    private void manejaExcepcion(HibernateException e) throws HibernateException {
        tra.rollback();
        throw new HibernateException("Ocurrio un error", e);
    }

    public List<Activofijo> listadoActivo() throws HibernateException {
        List<Activofijo> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Activofijo as af order by af.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado == null) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }

    public List<Activofijo> listadoActivoTangible(String tipo) throws HibernateException {
        List<Activofijo> listado = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("from Activofijo as af where af.tipoactivo.activo=? and "
                    + "(af.procedencia='Nuevo' or af.procedencia='Usado') order by af.id desc");
            q.setString(0, tipo);
            listado = q.list();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado == null) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }

    public List<Sucursal> listadoSucursal() throws HibernateException {
        List<Sucursal> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Sucursal as s order by s.id desc").list();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado == null) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }

    public List<Departamento> listadoDepartamento() throws HibernateException {
        List<Departamento> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Departamento as d order by d.id desc").list();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado == null) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }

    public List<Tipoactivo> listadoTipoActivo() throws HibernateException {
        List<Tipoactivo> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Tipoactivo as ta order by ta.id desc").list();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado == null) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }
    
    //Insertado para el reporte de activos dados de baja
    public List<Activobaja> listadoActivoBaja() throws HibernateException {
        List<Activobaja> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Activobaja as ab order by ab.id desc").list();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado == null) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }

    public Activofijo recuperaActivo(int id, String nombre, String cadena) {
        Activofijo af = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Activofijo af WHERE " + cadena + "");
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, nombre);
            }
            af = (Activofijo) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return af;
    }

    public Sucursal recuperaSucursal(int id) {
        Sucursal sc = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Sucursal s WHERE s.id=?");
            q.setInteger(0, id);
            sc = (Sucursal) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return sc;
    }

    public Departamento recuperaDepartamento(int id) {
        Departamento dp = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Departamento dp WHERE dp.id=?");
            q.setInteger(0, id);
            dp = (Departamento) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return dp;
    }

    public Tipoactivo recuperaTipoactivo(int id) {
        Tipoactivo ta = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Tipoactivo ta WHERE ta.id=?");
            q.setInteger(0, id);
            ta = (Tipoactivo) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return ta;
    }

    public Activobaja recuperaActivobaja(int id) {
        Activobaja ab = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Activobaja ab WHERE ab.activofijo.id=?");
            q.setInteger(0, id);
            ab = (Activobaja) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return ab;
    }

    public int eliminar(Object cn) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.delete(cn);
            tra.commit();
            estado = 1;
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return estado;
    }

    public int guardarActivo(Object af) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(af);
            tra.commit();
            estado = 1;
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return estado;
    }

    public int modificarActivo(Activofijo af) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.update(af);
            tra.commit();
            estado = 1;
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return estado;
    }

    public Operaciones obtenerDepreciacion(int id, String opcion) {
        Activofijo af = recuperaActivo(id, "", "af.id=?");
        if (af != null) {
            Operaciones dpr = new Operaciones(af, opcion);
            dpr.calcularDepreciacion();
            return dpr;
        }
        return null;
    }

    public Operaciones obtenerAmortizacion(int id, String opcion) {
        Activofijo af = recuperaActivo(id, "", "af.id=?");
        if (af != null) {
            Operaciones dpr = new Operaciones(af, opcion);
            dpr.calcularAmortizacion();
            return dpr;
        }
        return null;
    }

    public Activobaja recuperaAFBaja(Set activosbajas) {
        Activobaja activobaja = null;
        for (Object ab : activosbajas) {
            activobaja = (Activobaja) ab;
        }
        return activobaja;
    }

    public List<Activofijo> finFuncionales() {
        List<Activofijo> lista = new ArrayList<>();
        Operaciones op;
        for (Activofijo af : listadoActivo()) {
            if (af.getActivobajas().size() <= 0) {
                if (af.getTipoactivo().getActivo().equals("Tangible")) {
                    op = this.obtenerDepreciacion(af.getId(), "");
                    if (!op.isProceso() && af.getUso() == 1) {
                        lista.add(af);
                    }
                } else {
                    op = this.obtenerAmortizacion(af.getId(), "");
                    if (!op.isProceso() && af.getUso() == 1) {
                        lista.add(af);
                    }
                }
            }
        }
        return lista;
    }
}
