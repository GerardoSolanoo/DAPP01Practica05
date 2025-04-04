package org.uv.dapp01practica05.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_venta_idrow_seq")
    @SequenceGenerator(name = "detalle_venta_idrow_seq", sequenceName = "detalle_venta_idrow_seq",
            initialValue = 1, allocationSize = 1)
    @Column
    private Long idrow;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idventa")
    private Venta idventa;

    @ManyToOne
    @JoinColumn(name = "producto")
    private Producto producto;

    @Column
    private double precio;

    @Column
    private double cantidad;

    @Column
    private String descripcion;

    public Long getIdrow() {
        return idrow;
    }

    public void setIdrow(Long idrow) {
        this.idrow = idrow;
    }

    public Venta getIdventa() {
        return idventa;
    }

    public void setIdventa(Venta idventa) {
        this.idventa = idventa;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
