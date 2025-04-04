package org.uv.dapp01practica05.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
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
import org.uv.dapp01practica05.dto.ProductoDTO;
import org.uv.dapp01practica05.entity.Producto;
import org.uv.dapp01practica05.repository.ProductoRepository;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    private ProductoDTO convertToDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setClave(producto.getClave());
        dto.setNombre(producto.getNombre());
        dto.setPrecio(producto.getPrecio());
        return dto;
    }

    private Producto convertToEntity(ProductoDTO dto) {
        Producto producto = new Producto();
        if(dto.getClave() != null) {
            producto.setClave(dto.getClave());
        }
        producto.setNombre(dto.getNombre());
        producto.setPrecio(dto.getPrecio());
        return producto;
    }

    @GetMapping()
    public List<ProductoDTO> list() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> get(@PathVariable Long id) {
        Optional<Producto> opt = productoRepository.findById(id);
        if(opt.isPresent())
            return ResponseEntity.ok(convertToDTO(opt.get()));
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> put(@PathVariable Long id, @RequestBody ProductoDTO input) {
        Optional<Producto> resProd = productoRepository.findById(id);

        if (resProd.isPresent()){
            Producto productoToEdit = resProd.get();

            productoToEdit.setNombre(input.getNombre());
            productoToEdit.setPrecio(input.getPrecio());

            Producto productEdited = productoRepository.save(productoToEdit);

            return ResponseEntity.ok(convertToDTO(productEdited));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> post(@RequestBody ProductoDTO input) {
        Producto entity = convertToEntity(input);
        Producto productNew = productoRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(productNew));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductoDTO> delete(@PathVariable Long id) {
        Optional<Producto> resProd = productoRepository.findById(id);

        if (resProd.isPresent()){
            Producto productToDelete = resProd.get();

            productoRepository.deleteById(id);

            return ResponseEntity.ok(convertToDTO(productToDelete));
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