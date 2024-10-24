package com.project.elite_construcoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.elite_construcoes.infra.security.TokenService;
import com.project.elite_construcoes.model.Cliente;
import com.project.elite_construcoes.model.DTOs.AuthenticationDTO;
import com.project.elite_construcoes.model.DTOs.LoginResponseDTO;
import com.project.elite_construcoes.model.DTOs.RegisterDTO;
import com.project.elite_construcoes.model.DTOs.TokenDTO;
import com.project.elite_construcoes.repositories.ClienteRepository;
import com.project.elite_construcoes.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/cliente")
@Validated
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable("id") Integer id) { 
        return ResponseEntity.ok(this.clienteService.findById(id));
    }

    @PostMapping("/login")
public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    // Obtém o cliente autenticado
    var cliente = (Cliente) auth.getPrincipal();
    if (cliente != null) {
        var token = tokenService.generateToken(cliente);
        System.out.println("Cliente autenticado com ID: " + cliente.getId()); // Log para verificar o ID
        return ResponseEntity.ok(new LoginResponseDTO(token, cliente.getId()));
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        if (this.clienteRepository.findByEmail(data.email()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String senhaCriptografada = new BCryptPasswordEncoder().encode(data.senha());

        Cliente novoCliente = new Cliente(
            null, 
            data.nomeCompleto(), 
            data.nomeUsuario(), 
            data.dataNascimento(),
            data.email(), 
            senhaCriptografada
        );

        this.clienteService.create(novoCliente);
        return ResponseEntity.ok().build();
    }

@PutMapping("/{id}")
public ResponseEntity<Cliente> update(@PathVariable("id") Integer id, @Valid @RequestBody Cliente cliente) {
    cliente.setId(id);

    // Criptografar a nova senha antes de atualizar
    if (cliente.getSenha() != null && !cliente.getSenha().isEmpty()) {
        String senhaCriptografada = new BCryptPasswordEncoder().encode(cliente.getSenha());
        cliente.setSenha(senhaCriptografada);
    }

    this.clienteService.update(cliente);
    return ResponseEntity.noContent().build();
}


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id) { 
        this.clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/tipoUser")
    public ResponseEntity<Cliente> getUserByToken(@RequestBody @Valid TokenDTO tokenDTO) {
        String authHeader = tokenDTO.token();
        if (authHeader == null || authHeader.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        String token = authHeader.replace("Bearer ", "");
        Cliente cliente = clienteService.getUserByToken(token);
        if (cliente == null) {
            throw new RuntimeException("Token inválido, faça login novamente");
        }
        return ResponseEntity.ok(cliente);
    }
}
