/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Cnatural;
import entities.Disponibilidad;
import entities.Persona;
import hibernateutil.HibernateUtil;
import java.util.ArrayList;
import java.util.Collections;
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
public class ModeloFiador {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloFiador() {
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

    public List<Cnatural> listadoFiadores() throws HibernateException {
        List<Cnatural> listadoFiadores = null;
        try {
            iniciaOperacion();
            listadoFiadores = ses.createQuery("from Cnatural as c where c.tipo='Fiador' order by c.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoFiadores.size() == 0) {
            return new ArrayList<>();
        } else {
            return listadoFiadores;
        }
    }

    public Cnatural recuperaFiador(int id) {
        Cnatural cn = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Cnatural c WHERE c.id=?");
            q.setInteger(0, id);
            cn = (Cnatural) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return cn;
    }

    public Persona recuperaPersona(int id, String dui, String cadena) {
        Persona persona = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Persona p WHERE " + cadena + " and p.tipo='Fiador'");
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

    public Disponibilidad recuperaDisponibilidad(int id, String operacion, String cadena) {
        Disponibilidad dp = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Disponibilidad d WHERE " + cadena + "");
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, operacion);
            }
            dp = (Disponibilidad) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return dp;
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

    public int guardarFiador(Persona persona, Cnatural cn) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //guardamos la persona
            ses.save(persona);
            tra.commit();
            //insertamos el fiador en cnatural
            persona.setId(recuperaPersona(0, persona.getDui(), "p.dui=?").getId());
            iniciaOperacion();
            ses.save(cn);
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

    public int modificarFiador(Persona persona, Cnatural cn) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //modificamos la persona
            ses.update(persona);
            tra.commit();
            //modificamos el fiador en cnatural
            iniciaOperacion();
            ses.update(cn);
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

    //registro de ingresos,listar
    public int guardarOperacion(Disponibilidad ds) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(ds);
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

    public int modificarOperacion(Disponibilidad ds) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.update(ds);
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

    public ArrayList<Disponibilidad> listaDisponibilidad(int id, String opcion) {
        ArrayList<Disponibilidad> lista = new ArrayList<>();
        Cnatural cn = new Cnatural();
        cn = recuperaFiador(id);
        Disponibilidad aux;
        for (Object er : cn.getDisponibilidadsForIdfiador()) {
            aux = (Disponibilidad) er;
            if (aux.getTipo().equals(opcion)) {
                lista.add(aux);
            }
        }
        Collections.sort(lista, (Disponibilidad o1, Disponibilidad o2) -> new Integer(o1.getId()).compareTo(o2.getId()));
        return lista;
    }
}
