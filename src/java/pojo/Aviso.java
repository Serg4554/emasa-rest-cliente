/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nacho
 */
@Entity
@Table(name = "aviso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Aviso.findAll", query = "SELECT a FROM Aviso a"),
    @NamedQuery(name = "Aviso.findById", query = "SELECT a FROM Aviso a WHERE a.id = :id"),
    @NamedQuery(name = "Aviso.findByFechacreacion", query = "SELECT a FROM Aviso a WHERE a.fechacreacion = :fechacreacion"),
    @NamedQuery(name = "Aviso.findByUbicacion", query = "SELECT a FROM Aviso a WHERE a.ubicacion = :ubicacion"),
    @NamedQuery(name = "Aviso.findByEstado", query = "SELECT a FROM Aviso a WHERE a.estado = :estado"),
    @NamedQuery(name = "Aviso.findByObservaciones", query = "SELECT a FROM Aviso a WHERE a.observaciones = :observaciones"),
    @NamedQuery(name = "Aviso.findByUbicacionTecnica", query = "SELECT a FROM Aviso a WHERE a.ubicacionTecnica = :ubicacionTecnica"),
    @NamedQuery(name = "Aviso.findByPrioridad", query = "SELECT a FROM Aviso a WHERE a.prioridad = :prioridad"),
    @NamedQuery(name = "Aviso.findByInicioReparacion", query = "SELECT a FROM Aviso a WHERE a.inicioReparacion = :inicioReparacion"),
    @NamedQuery(name = "Aviso.findByFinReparacion", query = "SELECT a FROM Aviso a WHERE a.finReparacion = :finReparacion"),
    @NamedQuery(name = "Aviso.findByPosGPS", query = "SELECT a FROM Aviso a WHERE a.posGPS = :posGPS"),
    @NamedQuery(name = "Aviso.findByTipo", query = "SELECT a FROM Aviso a WHERE a.tipo = :tipo"),
    @NamedQuery(name = "Aviso.findByUsuario", query = "SELECT a FROM Aviso a WHERE a.usuarioemail.email = :param"),
    @NamedQuery(name = "Aviso.findByMargen", query = "SELECT a FROM Aviso a WHERE a.finReparacion BETWEEN :fecha1 AND :fecha2")})
public class Aviso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "ubicacion")
    private String ubicacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "observaciones")
    private String observaciones;
    @Size(max = 1000)
    @Column(name = "ubicacion_tecnica")
    private String ubicacionTecnica;
    @Column(name = "prioridad")
    private Integer prioridad;
    @Column(name = "inicio_reparacion")
    @Temporal(TemporalType.DATE)
    private Date inicioReparacion;
    @Column(name = "fin_reparacion")
    @Temporal(TemporalType.DATE)
    private Date finReparacion;
    @Size(max = 100)
    @Column(name = "pos_GPS")
    private String posGPS;
    @Size(max = 100)
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "avisoId")
    private Collection<Operacion> operacionCollection;
    @OneToMany(mappedBy = "avisoIdDuplicado")
    private Collection<Aviso> avisoCollection;
    @JoinColumn(name = "Aviso_Id_Duplicado", referencedColumnName = "Id")
    @ManyToOne
    private Aviso avisoIdDuplicado;
    @JoinColumn(name = "Usuario_email", referencedColumnName = "email")
    @ManyToOne
    private Usuario usuarioemail;

    public Aviso() {
    }

    public Aviso(Integer id) {
        this.id = id;
    }

    public Aviso(Integer id, Date fechacreacion, String ubicacion, String estado, String observaciones) {
        this.id = id;
        this.fechacreacion = fechacreacion;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.observaciones = observaciones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getUbicacionTecnica() {
        return ubicacionTecnica;
    }

    public void setUbicacionTecnica(String ubicacionTecnica) {
        this.ubicacionTecnica = ubicacionTecnica;
    }

    public Integer getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    public Date getInicioReparacion() {
        return inicioReparacion;
    }

    public void setInicioReparacion(Date inicioReparacion) {
        this.inicioReparacion = inicioReparacion;
    }

    public Date getFinReparacion() {
        return finReparacion;
    }

    public void setFinReparacion(Date finReparacion) {
        this.finReparacion = finReparacion;
    }

    public String getPosGPS() {
        return posGPS;
    }

    public void setPosGPS(String posGPS) {
        this.posGPS = posGPS;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @XmlTransient
    public Collection<Operacion> getOperacionCollection() {
        return operacionCollection;
    }

    public void setOperacionCollection(Collection<Operacion> operacionCollection) {
        this.operacionCollection = operacionCollection;
    }

    @XmlTransient
    public Collection<Aviso> getAvisoCollection() {
        return avisoCollection;
    }

    public void setAvisoCollection(Collection<Aviso> avisoCollection) {
        this.avisoCollection = avisoCollection;
    }

    public Aviso getAvisoIdDuplicado() {
        return avisoIdDuplicado;
    }

    public void setAvisoIdDuplicado(Aviso avisoIdDuplicado) {
        this.avisoIdDuplicado = avisoIdDuplicado;
    }

    public Usuario getUsuarioemail() {
        return usuarioemail;
    }

    public void setUsuarioemail(Usuario usuarioemail) {
        this.usuarioemail = usuarioemail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aviso)) {
            return false;
        }
        Aviso other = (Aviso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.Aviso[ id=" + id + " ]";
    }
    
}
