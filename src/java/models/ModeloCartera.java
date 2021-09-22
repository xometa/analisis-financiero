/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Carterageneral;
import entities.Cjuridico;
import entities.Cnatural;
import entities.Detallecartera;
import entities.Persona;
import entities.Sucursal;
import hibernateutil.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author Kevin
 */
public class ModeloCartera {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloCartera() {
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

    public List<Persona> listadoClientes() throws HibernateException {
        List<Persona> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Persona as p where not exists (from Detallecartera as dc where dc.persona=p) and (p.tipo='Juridico' or p.tipo='Natural') order by p.id asc").list();
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
            listado = ses.createQuery("from Sucursal as su order by su.id desc").list();
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

    public List<Persona> listadoPersonaAsesor() throws HibernateException {
        List<Persona> listado = null;
        try {
            iniciaOperacion();
            listado = ses.createQuery("from Persona as pe where pe.tipo='Empleado' order by pe.id asc").list();
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

    public Cjuridico recuperaClienteJ(Set cjuridicos) {
        Cjuridico c = null;
        for (Object cj : cjuridicos) {
            c = (Cjuridico) cj;
        }
        return c;
    }

    public Cnatural recuperaClienteN(Set cnaturales) {
        Cnatural c = null;
        for (Object cn : cnaturales) {
            c = (Cnatural) cn;
        }
        return c;
    }

    public Carterageneral carteraGeneral(int id, String cadena) {
        Carterageneral cg = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Carterageneral cg WHERE " + cadena);
            q.setInteger(0, id);
            cg = (Carterageneral) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return cg;
    }

    public Persona recuperaCliente(int id) {
        Persona persona = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Persona p WHERE p.id=?");
            q.setInteger(0, id);
            persona = (Persona) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return persona;
    }

    public Detallecartera recuperaClienteCartera(int idcartera, int idpersona) {
        Detallecartera dc = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Detallecartera as dc WHERE dc.carterageneral.id=? and dc.persona.id=?");
            q.setInteger(0, idcartera);
            q.setInteger(1, idpersona);
            dc = (Detallecartera) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return dc;
    }

    public int eliminarCartera(Object cn) throws HibernateException {
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

    public int guardarCartera(Carterageneral obj) throws HibernateException {
        int estado = 0;
        try {
            if (obj != null) {
                iniciaOperacion();
                ses.save(obj);
                tra.commit();
            }
            estado = 1;
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return estado;
    }

    public int guardarDetalleCartera(Detallecartera dc) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            ses.save(dc);
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

    public int modificarCartera(Object obj) throws HibernateException {
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
