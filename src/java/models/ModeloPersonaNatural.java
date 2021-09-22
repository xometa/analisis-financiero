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
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author BONIFACIO
 */
public class ModeloPersonaNatural {
    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloPersonaNatural() {
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
    
    public List<Cnatural> listadoPersonasNaturales() throws HibernateException {
        List<Cnatural> lstPerNaturales = null;
        try {
            iniciaOperacion();
            lstPerNaturales = ses.createQuery("from Cnatural as c where c.tipo='Natural' order by c.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (lstPerNaturales.size() == 0) {
            return new ArrayList<>();
        } else {
            return lstPerNaturales;
        }
    }
    
    public List<Cnatural> listadoFiadores() throws HibernateException {
        List<Cnatural> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Cnatural as c where c.tipo='Fiador' order by c.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado.size() == 0) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }
    
    public Cnatural recuperaPersonaNatural(int id) {
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
            q = ses.createQuery("FROM Persona p WHERE " + cadena + " and p.tipo='Natural'");
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
    
    public int guardarPersonaNatural(Persona persona, Cnatural cn) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //guardamos la persona 
            ses.save(persona);
            tra.commit();
            //insertamos la persona natural en cnatural
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
    
    public int modificarPersonaNatural(Persona persona, Cnatural cn) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //modificamos la persona
            ses.update(persona);
            tra.commit();
            //modificamos la Persona Natural en cnatural
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
    
    public int eliminarPersonaNatural(Object cn) throws HibernateException {
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
    
    //registro de ingresos,listar
    public List<Disponibilidad> listadoIngresosEgresos(int id, String tipo) throws HibernateException {
        List<Disponibilidad> listado = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("from Disponibilidad as d where d.cnaturalByIdnatural.id=? and d.tipo=? order by d.id desc");
            q.setInteger(0, id);
            q.setString(1, tipo);
            listado = q.list();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listado.size() == 0) {
            return new ArrayList<>();
        } else {
            return listado;
        }
    }
    
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
        cn = recuperaPersonaNatural(id);
        Disponibilidad aux;
        for (Object er : cn.getDisponibilidadsForIdnatural()) {
            aux = (Disponibilidad) er;
            if (aux.getTipo().equals(opcion)) {
                lista.add(aux);
            }
        }
        Collections.sort(lista, (Disponibilidad o1, Disponibilidad o2) -> new Integer(o1.getId()).compareTo(o2.getId()));
        return lista;
    }
    
    public List<Persona> listadoClientesSinCreditoActivo() throws HibernateException {
        List<Persona> listadoDeClientes = null;
        try {
            iniciaOperacion();
            SQLQuery query = ses.createSQLQuery("SELECT per.* FROM prestamo p\n" +
            "right OUTER JOIN persona per ON per.id = p.idcliente\n" +
            "WHERE (p.estado !=1  or p.estado = null) or p.idcliente IS NULL and per.tipo = 'Natural'");
            
            query.addEntity(Persona.class);
            listadoDeClientes = query.list();
            
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoDeClientes.size() == 0) {
            return new ArrayList<>();
        } else {
            return listadoDeClientes;
        }
    }
    
    public List<Persona> listadoFiadoresSinCreditoActivo() throws HibernateException {
        List<Persona> listadoDeClientes = null;
        try {
            iniciaOperacion();
            SQLQuery query = ses.createSQLQuery("SELECT p.* FROM detallefiador det\n" +
            "INNER JOIN prestamo pres ON det.idprestamo = pres.id\n" +
            "INNER JOIN cnatural cn ON det.idfiador = cn.id\n" +
            "right OUTER JOIN persona p ON cn.idpersona = p.id\n" +
            "WHERE (pres.estado !=1  or pres.estado = null) or pres.idcliente IS NULL and P.tipo = 'Fiador'  ");
            
            query.addEntity(Persona.class);
            listadoDeClientes = query.list();
            
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoDeClientes.size() == 0) {
            return new ArrayList<>();
        } else {
            return listadoDeClientes;
        }
    }
}
