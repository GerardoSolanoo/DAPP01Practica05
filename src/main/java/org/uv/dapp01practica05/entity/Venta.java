package org.uv.dapp01practica05.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ventas")
public class Venta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ventas_clave_seq")
    @SequenceGenerator(name = "ventas_clave_seq", sequenceName = "ventas_clave_seq",
            initialValue = 1, allocationSize = 1)
    @Column
    private Long clave;

    @Column
    private Date fecha;

    @Column
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "idventa",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<DetalleVenta> lstDetalleVenta;

    public Long getClave() {
        return clave;
    }

    public void setClave(Long clave) {
        this.clave = clave;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleVenta> getLstDetalleVenta() {
        return lstDetalleVenta;
    }

    public void setLstDetalleVenta(List<DetalleVenta> lstDetalleVenta) {
        this.lstDetalleVenta = lstDetalleVenta;
    }
}
