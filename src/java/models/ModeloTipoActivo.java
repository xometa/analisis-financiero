/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Tipoactivo;
import hibernateutil.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Kevin
 */
public class ModeloTipoActivo {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloTipoActivo() {
    }

    private void iniciaOperacion() throws HibernateException {
        sf = HibernateUtil.getSessionFactory();
        ses = sf.openSession();
        tra = ses.beginTransaction();
    }

    private void manejaExcepcion(HibernateException e) throws HibernateException {
        tra.rollback();
        throw new HibernateException("Ocurrio un error", e);
    }

    public List<Tipoactivo> listadoTipo() throws HibernateException {
        List<Tipoactivo> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Tipoactivo as ta order by ta.id asc").list();
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

    public Tipoactivo recuperaTipo(int id, String nombre, String cadena) {
        Tipoactivo ta = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Tipoactivo ta WHERE " + cadena + "");
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, nombre);
            }
            ta = (Tipoactivo) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return ta;
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

    public int guardarTipo(Tipoactivo ta) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(ta);
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

    public int modificarTipo(Tipoactivo ta) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.update(ta);
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
}
