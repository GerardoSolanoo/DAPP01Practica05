package org.uv.dapp01practica05.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.uv.dapp01practica05.dto.DetalleVentaDTO;
import org.uv.dapp01practica05.dto.VentaDTO;
import org.uv.dapp01practica05.entity.Cliente;
import org.uv.dapp01practica05.entity.DetalleVenta;
import org.uv.dapp01practica05.entity.Producto;
import org.uv.dapp01practica05.entity.Venta;
import org.uv.dapp01practica05.repository.ClienteRepository;
import org.uv.dapp01practica05.repository.ProductoRepository;
import org.uv.dapp01practica05.repository.VentaRepository;

@RestController
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    private VentaDTO convertToDTO(Venta venta) {
        VentaDTO dto = new VentaDTO();
        dto.setClave(venta.getClave());
        dto.setFecha(venta.getFecha());
        dto.setMonto(venta.getMonto());
        dto.setIdCliente(venta.getCliente().getClave());

        List<DetalleVentaDTO> detallesDTO = new ArrayList<>();
        if (venta.getLstDetalleVenta() != null) {
            detallesDTO = venta.getLstDetalleVenta().stream()
                    .map(detalle -> {
                        DetalleVentaDTO detalleDTO = new DetalleVentaDTO();
                        detalleDTO.setIdrow(detalle.getIdrow());
                        detalleDTO.setIdVenta(venta.getClave());
                        detalleDTO.setIdProducto(detalle.getProducto().getClave());
                        detalleDTO.setNombreProducto(detalle.getProducto().getNombre());
                        detalleDTO.setPrecio(detalle.getPrecio());
                        detalleDTO.setCantidad(detalle.getCantidad());
                        detalleDTO.setDescripcion(detalle.getDescripcion());
                        return detalleDTO;
                    })
                    .collect(Collectors.toList());
        }
        dto.setLstDetalleVenta(detallesDTO);

        return dto;
    }

    private Venta convertToEntity(VentaDTO dto) throws Exception {
        Venta venta = new Venta();

        Optional<Cliente> clienteOpt = clienteRepository.findById(dto.getIdCliente());
        if (!clienteOpt.isPresent()) {
            throw new Exception("Cliente no encontrado con ID: " + dto.getIdCliente());
        }

        venta.setFecha(dto.getFecha());
        venta.setMonto(dto.getMonto());
        venta.setCliente(clienteOpt.get());

        if (dto.getLstDetalleVenta() != null && !dto.getLstDetalleVenta().isEmpty()) {
            List<DetalleVenta> detalles = new ArrayList<>();

            for (DetalleVentaDTO detalleDTO : dto.getLstDetalleVenta()) {
                DetalleVenta detalle = new DetalleVenta();

                Optional<Producto> productoOpt = productoRepository.findById(detalleDTO.getIdProducto());
                if (!productoOpt.isPresent()) {
                    throw new Exception("Producto no encontrado con ID: " + detalleDTO.getIdProducto());
                }

                detalle.setProducto(productoOpt.get());
                detalle.setPrecio(detalleDTO.getPrecio());
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setDescripcion(detalleDTO.getDescripcion());
                detalle.setIdventa(venta);

                detalles.add(detalle);
            }

            venta.setLstDetalleVenta(detalles);
        }

        return venta;
    }

    @GetMapping()
    public List<VentaDTO> list() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> get(@PathVariable Long id) {
        Optional<Venta> opt = ventaRepository.findById(id);
        if (opt.isPresent())
            return ResponseEntity.ok(convertToDTO(opt.get()));
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> put(@PathVariable Long id, @RequestBody VentaDTO input) {
        Optional<Venta> ventaOpt = ventaRepository.findById(id);

        if (!ventaOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Venta ventaExistente = ventaOpt.get();

        try {
            ventaExistente.setFecha(input.getFecha() != null ? input.getFecha() : ventaExistente.getFecha());
            ventaExistente.setMonto(input.getMonto() != null ? input.getMonto() : ventaExistente.getMonto());

            if (input.getIdCliente() != null && !ventaExistente.getCliente().getClave().equals(input.getIdCliente())) {
                Optional<Cliente> clienteOpt = clienteRepository.findById(input.getIdCliente());
                if (!clienteOpt.isPresent()) {
                    return ResponseEntity.badRequest().body(null);
                }
                ventaExistente.setCliente(clienteOpt.get());
            }

            Venta ventaActualizada = ventaRepository.save(ventaExistente);
            return ResponseEntity.ok(convertToDTO(ventaActualizada));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<VentaDTO> post(@RequestBody VentaDTO dtoVenta) {
        try {
            Venta venta = convertToEntity(dtoVenta);
            Venta ventaGuardada = ventaRepository.save(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(ventaGuardada));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VentaDTO> delete(@PathVariable Long id) {
        Optional<Venta> ventaOpt = ventaRepository.findById(id);

        if (ventaOpt.isPresent()) {
            Venta ventaToDelete = ventaOpt.get();
            VentaDTO deletedVentaDTO = convertToDTO(ventaToDelete);

            try {
                ventaRepository.deleteById(id);
                return ResponseEntity.ok(deletedVentaDTO);
            } catch (Exception ex) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleError(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en el servidor: " + ex.getMessage());
    }
}