/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Tipoprestamo;
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
 * @author jsaul
 */
public class ModeloTipoPrestamo {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloTipoPrestamo() {
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

    public List<Tipoprestamo> listadoTipoPrestamos() throws HibernateException {
        List<Tipoprestamo> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Tipoprestamo as tp order by tp.id desc").list();
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

    public Tipoprestamo recuperaTipoPrestamo(int id, String nombre, String cadena) {
        Tipoprestamo tp = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Tipoprestamo tp WHERE " + cadena + "");
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, nombre);
            }
            tp = (Tipoprestamo) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return tp;
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

    public int guardar(Object obj) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(obj);
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

    public int modificar(Object obj) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.update(obj);
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
