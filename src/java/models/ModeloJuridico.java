/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import entities.Bgeneral;
import entities.Cjuridico;
import entities.Cnatural;
import entities.Eresultado;
import entities.Persona;
import hibernateutil.HibernateUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 *
 * @author jsaul
 */
public class ModeloJuridico {

    private SessionFactory sf;
    private Session ses;
    private Transaction tra;
    Query q;

    public ModeloJuridico() {
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

    public List<Cjuridico> listadoJuridico() throws HibernateException {
        List<Cjuridico> listadoJuridicos = null;
        try {
            iniciaOperacion();
            listadoJuridicos = ses.createQuery("from Cjuridico as c order by c.id desc").list();

        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        if (listadoJuridicos.size() == 0) {
            return new ArrayList<>();
        } else {
            return listadoJuridicos;
        }
    }

    public int guardarFiador(Persona persona, Cjuridico cj) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //guardamos la persona
            ses.save(persona);
            tra.commit();
            //asignamos el representante al cliente jurídico
            persona.setId(recuperaPersona(0, persona.getDui(), "p.dui=?").getId());
            iniciaOperacion();
            ses.save(cj);
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

    public int modificarJuridico(Persona persona, Cjuridico cj) throws HibernateException {
        int estado = 0;
        try {
            iniciaOperacion();
            //modificamos la persona
            ses.update(persona);
            tra.commit();
            //modificamos el representante de la entidad
            iniciaOperacion();
            ses.update(cj);
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

    public Cjuridico recuperaJuridico(int id) {
        Cjuridico cj = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Cjuridico c WHERE c.id=?");
            q.setInteger(0, id);
            cj = (Cjuridico) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return cj;
    }

    public Persona recuperaPersona(int id, String dui, String cadena) {
        Persona persona = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Persona p WHERE " + cadena + " and p.tipo='Juridico'");
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

    public Bgeneral recuperaBG(int id, String nombre, String cadena) {
        Bgeneral bg = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Bgeneral bg WHERE " + cadena);
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, nombre);
            }
            bg = (Bgeneral) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return bg;
    }

    public Eresultado recuperaER(int id, String nombre, String cadena) {
        Eresultado er = null;
        try {
            iniciaOperacion();
            q = ses.createQuery("FROM Eresultado er WHERE " + cadena);
            if (id > 0) {
                q.setInteger(0, id);
            } else {
                q.setString(0, nombre);
            }
            er = (Eresultado) q.uniqueResult();
        } catch (HibernateException e) {
            manejaExcepcion(e);
            throw e;
        } finally {
            ses.close();
        }
        return er;
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

    public ArrayList<Bgeneral> listadoCuentas(Set bgeneral) {
        ArrayList<Bgeneral> lista = new ArrayList();
        Bgeneral aux;
        for (Object bg : bgeneral) {
            aux = (Bgeneral) bg;
            lista.add(aux);
        }
        Collections.sort(lista, (Bgeneral o1, Bgeneral o2) -> new Integer(o1.getId()).compareTo(o2.getId()));
        return lista;
    }

    public ArrayList<Eresultado> listadoCuentasER(Set estadoR) {
        ArrayList<Eresultado> lista = new ArrayList();
        Eresultado aux;
        for (Object er : estadoR) {
            aux = (Eresultado) er;
            lista.add(aux);
        }
        Collections.sort(lista, (Eresultado o1, Eresultado o2) -> new Integer(o1.getId()).compareTo(o2.getId()));
        return lista;
    }

    public int guardarInformacion(Object obj) throws HibernateException {
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

    public int modificarInformacion(Object obj) throws HibernateException {
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

    public List<Cjuridico> lstClientesJuridicosSinCreditoActivo() throws HibernateException {
        List<Cjuridico> listadoDeClientes = null;
        try {
            iniciaOperacion();
            SQLQuery query = ses.createSQLQuery(" SELECT cj.* FROM prestamo pres\n"
                    + "right OUTER JOIN persona p ON pres.idcliente = p.id\n"
                    + "INNER JOIN cjuridico cj ON cj.idrepresentate = p.id\n"
                    + "WHERE (pres.estado !=1  or pres.estado = null) or pres.idcliente IS NULL and P.tipo = 'Juridico' ");

            query.addEntity(Cjuridico.class);
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
