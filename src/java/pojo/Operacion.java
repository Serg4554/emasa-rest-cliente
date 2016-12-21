/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nacho
 */
@Entity
@Table(name = "operacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Operacion.findAll", query = "SELECT o FROM Operacion o"),
    @NamedQuery(name = "Operacion.findById", query = "SELECT o FROM Operacion o WHERE o.id = :id"),
    @NamedQuery(name = "Operacion.findByDescripcion", query = "SELECT o FROM Operacion o WHERE o.descripcion = :descripcion"),
    @NamedQuery(name = "Operacion.findByFecha", query = "SELECT o FROM Operacion o WHERE o.fecha = :fecha"),
    @NamedQuery(name = "Operacion.findByIdAviso", query = "SELECT o FROM Operacion o WHERE o.avisoId.id = :id")})
public class Operacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10000)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "Aviso_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Aviso avisoId;
    @JoinColumn(name = "Usuario_email", referencedColumnName = "email")
    @ManyToOne
    private Usuario usuarioemail;

    public Operacion() {
    }

    public Operacion(Integer id) {
        this.id = id;
    }

    public Operacion(Integer id, String descripcion, Date fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Aviso getAvisoId() {
        return avisoId;
    }

    public void setAvisoId(Aviso avisoId) {
        this.avisoId = avisoId;
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
        if (!(object instanceof Operacion)) {
            return false;
        }
        Operacion other = (Operacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rest.Operacion[ id=" + id + " ]";
    }
    
}
