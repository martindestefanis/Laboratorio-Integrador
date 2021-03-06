package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Reserva implements Serializable {

    public enum Estado {REALIZADO, CONFIRMADO, PENDIENTE}

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_RESERVA")
    private Integer id;
    @TypeConverters(FechaConverter.class)
    private Date fechaInicio;
    @TypeConverters(FechaConverter.class)
    private Date fechaFin;
    @TypeConverters(EstadoConverter.class)
    private Estado estado;
    @Embedded(prefix = "depto_")
    private Departamento departamento;
    private Double precio;
    @Embedded(prefix = "usu_")
    private Usuario usuario;

    public Reserva(){}

    @Ignore
    public Reserva(Integer id, Date fechaInicio, Date fechaFin, Departamento departamento) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.departamento = departamento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
