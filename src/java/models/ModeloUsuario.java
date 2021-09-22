/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Persona;
import entities.Usuario;
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
 * @author BONIFACIO
 */
public class ModeloUsuario {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloUsuario() {
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

    public List<Usuario> listadoUsuarios() throws HibernateException {
        List<Usuario> listadoUsuarios = null;
        try {
            iniciaOperacion();
            listadoUsuarios = ses.createQuery("SELECT u FROM Usuario u JOIN u.persona p where p.tipo='Empleado' order by u.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoUsuarios.size() == 0) {
            return new ArrayList<>();
        } else {
            return listadoUsuarios;
        }
    }

    public Usuario recuperarUsuario(int id, String cadena) {
        Usuario u = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Usuario u WHERE " + cadena);
            q.setInteger(0, id);
            u = (Usuario) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return u;
    }

    public Usuario buscarUserName(String valor,String cadena) {
        Usuario u = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Usuario u WHERE "+cadena);
            q.setString(0, valor);
            u = (Usuario) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return u;
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

    public int eliminarUsuario(Object cn) throws HibernateException {
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

    public int guardarUsuario(Usuario u) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(u);
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

    public int modificarUsuario(Usuario u) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.update(u);
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

    public Usuario validaSesion(String user, String password) {
        Usuario u = null;
        for (Usuario us : listadoUsuarios()) {
            if (us.getUsuario().equals(user) && us.getClave().equals(password)) {
                return us;
            }
        }
        return u;
    }
}
