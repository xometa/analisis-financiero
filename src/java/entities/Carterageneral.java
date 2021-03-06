package entities;
// Generated Dec 30, 2020 2:22:52 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Carterageneral generated by hbm2java
 */
@Entity
@Table(name="carterageneral"
    ,catalog="institucion_financiera"
)
public class Carterageneral  implements java.io.Serializable {


     private int id;
     private Persona persona;
     private Sucursal sucursal;
     private int estado;
     private Set<Detallecartera> detallecarteras = new HashSet<Detallecartera>(0);

    public Carterageneral() {
    }

    public Carterageneral(Persona persona, Sucursal sucursal) {
        this.persona = persona;
        this.sucursal = sucursal;
    }

    public Carterageneral(int id, Persona persona, Sucursal sucursal, int estado) {
        this.id = id;
        this.persona = persona;
        this.sucursal = sucursal;
        this.estado = estado;
    }
    public Carterageneral(int id, Persona persona, Sucursal sucursal, int estado, Set<Detallecartera> detallecarteras) {
       this.id = id;
       this.persona = persona;
       this.sucursal = sucursal;
       this.estado = estado;
       this.detallecarteras = detallecarteras;
    }

    public Carterageneral(Persona persona, Sucursal sucursal, int estado) {
        this.persona = persona;
        this.sucursal = sucursal;
        this.estado = estado;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idasesor", nullable=false)
    public Persona getPersona() {
        return this.persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idcartera", nullable=false)
    public Sucursal getSucursal() {
        return this.sucursal;
    }
    
    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    
    @Column(name="estado", nullable=false)
    public int getEstado() {
        return this.estado;
    }
    
    public void setEstado(int estado) {
        this.estado = estado;
    }

@OneToMany(fetch=FetchType.EAGER, mappedBy="carterageneral")
    public Set<Detallecartera> getDetallecarteras() {
        return this.detallecarteras;
    }
    
    public void setDetallecarteras(Set<Detallecartera> detallecarteras) {
        this.detallecarteras = detallecarteras;
    }




}


