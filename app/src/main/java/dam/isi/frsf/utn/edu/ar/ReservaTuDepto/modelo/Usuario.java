package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.List;

@Entity
public class Usuario implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_USUARIO")
    private Integer id;
    private String correo;
    @Ignore
    private List<Reserva> reservas;
    private Integer puntosSuperPremio;

    public Usuario(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public Integer getPuntosSuperPremio() {
        return puntosSuperPremio;
    }

    public void setPuntosSuperPremio(Integer puntosSuperPremio) {
        this.puntosSuperPremio = puntosSuperPremio;
    }

}
