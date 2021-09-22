/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Sucursal;
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
public class ModeloSucursal {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloSucursal() {
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

    public List<Sucursal> listadoSucursal() throws HibernateException {
        List<Sucursal> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Sucursal as su order by su.id asc").list();

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

    public Sucursal recuperaSucursal(int id, String nombre, String cadena) {
        Sucursal su = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Sucursal su WHERE " + cadena + "");
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, nombre);
            }
            su = (Sucursal) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return su;
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

    public int guardarSucursal(Sucursal su) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(su);
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

    public int modificarSucursal(Sucursal su) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.update(su);
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
