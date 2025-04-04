package org.uv.dapp01practica05.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable{
    @Id
    @GeneratedValue(    generator = "clientes_clave_seq",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator( name = "clientes_clave_seq",
            sequenceName = "clientes_clave_seq",
            initialValue = 1,
            allocationSize= 1
    )
    @Column
    private Long clave;

    @Column
    private String nombre;

    @JsonIgnore
    @OneToMany(mappedBy = "cliente",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Venta> lstVenta;

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

    public List<Venta> getLstVenta() {
        return lstVenta;
    }

    public void setLstVenta(List<Venta> lstVenta) {
        this.lstVenta = lstVenta;
    }
}
