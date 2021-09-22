/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Departamento;
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
public class ModeloDepartamento {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloDepartamento() {

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

    public List<Departamento> listadoDepartamento() throws HibernateException {
        List<Departamento> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Departamento as dp order by dp.id asc").list();

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

    public Departamento recuperaDepartamento(int id, String nombre, String cadena) {
        Departamento dp = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Departamento dp WHERE " + cadena + "");
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, nombre);
            }
            dp = (Departamento) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return dp;
    }

    public int eliminar(Object de) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.delete(de);
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

    public int guardarDepartamento(Departamento dp) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(dp);
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

    public int modificarDepartamento(Departamento dp) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.update(dp);
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
