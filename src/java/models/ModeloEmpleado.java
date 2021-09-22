/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Cnatural;
import entities.Persona;
import hibernateutil.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author BONIFACIO
 */
public class ModeloEmpleado {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloEmpleado() {
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

    public List<Persona> listadoEmpleados() throws HibernateException {
        List<Persona> listadoEmpleados = null;
        try {
            iniciaOperacion();
            listadoEmpleados = ses.createQuery("from Persona as p where p.tipo='Empleado' order by p.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoEmpleados.size() == 0) {
            return new ArrayList<>();
        } else {
            return listadoEmpleados;
        }
    }

    public Persona recuperaPersona(int id, String dui, String cadena) {
        Persona persona = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Persona p WHERE " + cadena + " and p.tipo='Empleado'");
            if (id == 0) {
                q.setString(0, dui);
            } else {
                q.setInteger(0, id);
            }
            persona = (Persona) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return persona;
    }

    public boolean recupPer(int id) {
        List<Persona> listadoEmpleados = null;
        try {
            iniciaOperacion();
            SQLQuery query = ses.createSQLQuery(" SELECT p.* FROM usuario u\n"
                    + "INNER JOIN persona p ON u.idempleado = p.id\n"
                    + " WHERE p.tipo = 'Empleado' AND p.id ='" + id + "' ");
            query.addEntity(Persona.class);
            listadoEmpleados = query.list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoEmpleados == null || listadoEmpleados.size() == 0) {
            return true;
        }
        return false;
    }

    public int eliminarEmpleado(Object cn) throws HibernateException {
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

    public int guardarEmpleado(Persona persona) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //guardamos la persona
            ses.save(persona);
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

    public int modificarEmpleado(Persona persona) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //modificamos la persona
            ses.update(persona);
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
