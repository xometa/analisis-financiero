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
 * Detallehipoteca generated by hbm2java
 */
@Entity
@Table(name="detallehipoteca"
    ,catalog="institucion_financiera"
)
public class Detallehipoteca  implements java.io.Serializable {


     private int id;
     private Hipoteca hipoteca;
     private Prestamo prestamo;

    public Detallehipoteca() {
    }

    public Detallehipoteca(int id, Hipoteca hipoteca, Prestamo prestamo) {
       this.id = id;
       this.hipoteca = hipoteca;
       this.prestamo = prestamo;
    }
   
     @Id 

    
    @Column(name="id", unique=true, nullable=false)
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idhipoteca", nullable=false)
    public Hipoteca getHipoteca() {
        return this.hipoteca;
    }
    
    public void setHipoteca(Hipoteca hipoteca) {
        this.hipoteca = hipoteca;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="idprestamo", nullable=false)
    public Prestamo getPrestamo() {
        return this.prestamo;
    }
    
    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }




}


