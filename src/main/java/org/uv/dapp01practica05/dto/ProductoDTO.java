package org.uv.dapp01practica05.dto;

public class ProductoDTO {
    private Long clave;
    private String nombre;
    private double precio;

    public ProductoDTO() {
    }

    public ProductoDTO(Long clave, String nombre, double precio) {
        this.clave = clave;
        this.nombre = nombre;
        this.precio = precio;
    }

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
}