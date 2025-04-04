package org.uv.dapp01practica05.dto;

public class ClienteDTO {
    private Long clave;
    private String nombre;

    public ClienteDTO() {
    }

    public ClienteDTO(Long clave, String nombre) {
        this.clave = clave;
        this.nombre = nombre;
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
}
