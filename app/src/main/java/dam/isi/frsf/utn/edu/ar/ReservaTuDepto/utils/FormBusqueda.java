package dam.isi.frsf.utn.edu.ar.ReservaTuDepto.utils;

import java.io.Serializable;

import dam.isi.frsf.utn.edu.ar.ReservaTuDepto.modelo.Ciudad;

public class FormBusqueda implements Serializable {
    private Double precioMinimo;
    private Double precioMaximo;
    private Ciudad ciudad;
    private Boolean permiteFumar;
    private Integer huespedes;

    public FormBusqueda(){}

    public FormBusqueda(Double precioMinimo, Double precioMaximo) {
        this(precioMinimo,precioMaximo,null,null,null);
    }

    public FormBusqueda(Ciudad ciudad) {
        this(null,null,ciudad,null,null);
    }

    public FormBusqueda(Double precioMinimo, Double precioMaximo, Ciudad ciudad, Boolean permiteFumar, Integer huespedes) {
        this.precioMinimo = precioMinimo;
        this.precioMaximo = precioMaximo;
        this.ciudad = ciudad;
        this.permiteFumar = permiteFumar;
        this.huespedes = huespedes;
    }

    public Double getPrecioMinimo() {
        return precioMinimo;
    }

    public void setPrecioMinimo(Double precioMinimo) {
        this.precioMinimo = precioMinimo;
    }

    public Double getPrecioMaximo() {
        return precioMaximo;
    }

    public void setPrecioMaximo(Double precioMaximo) {
        this.precioMaximo = precioMaximo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Boolean getPermiteFumar() {
        return permiteFumar;
    }

    public void setPermiteFumar(Boolean permiteFumar) {
        this.permiteFumar = permiteFumar;
    }

    public Integer getHuespedes() {
        return huespedes;
    }

    public void setHuespedes(Integer huespedes) {
        this.huespedes = huespedes;
    }
}
