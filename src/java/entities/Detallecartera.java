package entities;
// Generated Dec 30, 2020 2:22:52 PM by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Detallecartera generated by hbm2java
 */
@Entity
@Table(name="detallecartera"
    ,catalog="institucion_financiera"
)
public class Detallecartera  implements java.io.Serializable {


     private int id;
     private Carterageneral carterageneral;
     private Persona persona;

    public Detallecartera() {
    }

    public Detallecartera(int id, Carterageneral carterageneral, Persona persona) {
       this.id = id;
       this.carterageneral = carterageneral;
       this.persona = persona;
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
    @JoinColumn(name="idcarterag", nullable=false)
    public Carterageneral getCarterageneral() {
        return this.carterageneral;
    }
    
    public void setCarterageneral(Carterageneral carterageneral) {
        this.carterageneral = carterageneral;
    }

@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="idcliente", nullable=false)
    public Persona getPersona() {
        return this.persona;
    }
    
    public void setPersona(Persona persona) {
        this.persona = persona;
    }




}


