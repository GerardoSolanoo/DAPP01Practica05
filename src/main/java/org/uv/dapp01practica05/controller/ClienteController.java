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
import org.uv.dapp01practica05.dto.ClienteDTO;
import org.uv.dapp01practica05.entity.Cliente;
import org.uv.dapp01practica05.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    private ClienteDTO convertToDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setClave(cliente.getClave());
        dto.setNombre(cliente.getNombre());
        return dto;
    }

    private Cliente convertToEntity(ClienteDTO dto) {
        Cliente cliente = new Cliente();
        if(dto.getClave() != null) {
            cliente.setClave(dto.getClave());
        }
        cliente.setNombre(dto.getNombre());
        return cliente;
    }

    @GetMapping()
    public List<ClienteDTO> list() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> get(@PathVariable Long id) {
        Optional<Cliente> opt = clienteRepository.findById(id);
        if(opt.isPresent())
            return ResponseEntity.ok(convertToDTO(opt.get()));
        else
            return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> put(@PathVariable Long id, @RequestBody ClienteDTO input) {
        Optional<Cliente> resCliente = clienteRepository.findById(id);

        if (resCliente.isPresent()){
            Cliente clienteToEdit = resCliente.get();

            clienteToEdit.setNombre(input.getNombre());

            Cliente clienteEdited = clienteRepository.save(clienteToEdit);

            return ResponseEntity.ok(convertToDTO(clienteEdited));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> post(@RequestBody ClienteDTO input) {
        Cliente entity = convertToEntity(input);
        Cliente clienteNew = clienteRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(clienteNew));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteDTO> delete(@PathVariable Long id) {
        Optional<Cliente> resCliente = clienteRepository.findById(id);

        if (resCliente.isPresent()){
            Cliente clienteToDelete = resCliente.get();

            clienteRepository.deleteById(id);

            return ResponseEntity.ok(convertToDTO(clienteToDelete));
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