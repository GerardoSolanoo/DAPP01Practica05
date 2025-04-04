package org.uv.dapp01practica05.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productos_clave_seq")
    @SequenceGenerator(name = "productos_clave_seq", sequenceName = "productos_clave_seq",
            initialValue = 1, allocationSize = 1)
    @Column
    private Long clave;

    @Column
    private String nombre;

    @Column
    private double precio;

    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<DetalleVenta> lstDetalleVenta;

    public Long getClave() {
        return clave;
    }

    public void setClave(Long clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<DetalleVenta> getLstDetalleVenta() {
        return lstDetalleVenta;
    }

    public void setLstDetalleVenta(List<DetalleVenta> lstDetalleVenta) {
        this.lstDetalleVenta = lstDetalleVenta;
    }
}
