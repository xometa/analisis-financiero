/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Bgeneral;
import entities.Cjuridico;
import entities.Cnatural;
import entities.Detallefiador;
import entities.Persona;
import entities.Prestamo;
import entities.Tipoprestamo;
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
public class ModeloPrestamo {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloPrestamo() {
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

    public List<Prestamo> listadoPrestamosNatural() throws HibernateException {
        List<Prestamo> listadoPrestamos = null;
        try {
            iniciaOperacion();
            listadoPrestamos = ses.createQuery(" select pres from Prestamo pres\n"
                    + "join pres.persona p \n"
                    + "where pres.estado = 1 and p.tipo = 'Natural' ").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoPrestamos.size() == 0) {
            return new ArrayList<>();
        } else {
            return listadoPrestamos;
        }
    }

    public List<Prestamo> listadoPrestamosJuridico() throws HibernateException {
        List<Prestamo> listadoUsuarios = null;
        try {
            iniciaOperacion();
            listadoUsuarios = ses.createQuery(" select pres from Prestamo pres join pres.persona p where pres.estado = 1 and p.tipo = 'Juridico' ").list();

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

    public Prestamo recuperarPrestamo(int id) {
        Prestamo u = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Prestamo u WHERE u.id=?");
            q.setInteger(0, id);
            u = (Prestamo) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return u;
    }

    public Persona recuperaPersona(int id, String cadena) {
        Persona persona = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Persona p WHERE " + cadena);
            if (id != 0) {
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

    public Persona recuperarRepresentanteLegal(int id) {
        Cjuridico cj = null;
        try {
            iniciaOperacion();
            q = ses.createQuery(" FROM Cjuridico WHERE id =?  ");
            if (id != 0) {
                q.setInteger(0, id);
            }
            cj = (Cjuridico) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return cj.getPersona();
    }

    public Tipoprestamo recuperaTipoPrestamo(int id) {
        Tipoprestamo tipoprestamo = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Tipoprestamo t WHERE t.id =? ");
            if (id != 0) {
                q.setInteger(0, id);
            }
            tipoprestamo = (Tipoprestamo) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return tipoprestamo;
    }

    public Cnatural recuperaFiador(int id) {
        Cnatural fiador = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("select f from Cnatural f join f.persona p where p.id =? ");
            if (id != 0) {
                q.setInteger(0, id);
            }
            fiador = (Cnatural) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return fiador;
    }

    public int guardarPrestamo(Prestamo p) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            iniciaOperacion();
            ses.save(p);
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

    public Prestamo obtenerUltmoPrestamoReg() {
        List<Prestamo> prestamos = null;
        try {
            iniciaOperacion();
            prestamos = ses.createQuery("from Prestamo p order by p.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (prestamos.size() > 0) {
            return prestamos.get(0);
        } else {
            return null;
        }

    }

    public int guardarDetFiador(Persona objfiador) throws HibernateException {
        Cnatural fiador = null;
        Detallefiador detFi = new Detallefiador();
        Prestamo prestamo = null;
        int estado = 0;
        try {
            iniciaOperacion();
            //obtener el ultimo id prestamo
            prestamo = obtenerUltmoPrestamoReg();
            //obtener el cnatural de fiador
            fiador = recuperaFiador(objfiador.getId());
            //insertamos datos al objeto detfi
            detFi.setCnatural(fiador);
            detFi.setPrestamo(prestamo);
            //registramos detfiador
            iniciaOperacion();
            ses.save(detFi);
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

    public int obtnerTotalActivos(int id) {
        List<Bgeneral> totalActivos = null;
        double totAct = 0.00;
        try {
            iniciaOperacion();
            SQLQuery query = ses.createSQLQuery(" SELECT  * FROM bgeneral WHERE clasificacion = 'Activo Corriente' and idcjuridico = '" + id + "' and fecha=(SELECT MAX(fecha) from bgeneral)");

            query.addEntity(Bgeneral.class);
            totalActivos = query.list();

            //totalActivos =  ses.createQuery("FROM Bgeneral bg join bg.cjuridico j WHERE bg.clasificacion = 'Activo Corriente'  and j.id = '"+id+"' ").list();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (totalActivos.size() > 0) {
            for (Bgeneral b : totalActivos) {
                totAct += b.getMonto();
            }
        }
        //totAct = totalActivos;

        return (int) totAct;

    }

    public int obtnerTotalPasivos(int id) {
        List<Bgeneral> totalActivos = null;
        double totAct = 0.00;
        try {
            iniciaOperacion();
            SQLQuery query = ses.createSQLQuery(" SELECT  * FROM bgeneral WHERE clasificacion = 'Pasivo Corriente' and idcjuridico = '" + id + "' and fecha=(SELECT MAX(fecha) from bgeneral)");

            query.addEntity(Bgeneral.class);
            totalActivos = query.list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (totalActivos.size() > 0) {
            for (Bgeneral b : totalActivos) {
                totAct += b.getMonto();
            }
        }

        return (int) totAct;
    }
}
