package com.project.elite_construcoes.service;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.elite_construcoes.infra.security.TokenService;
import com.project.elite_construcoes.model.Cliente;
import com.project.elite_construcoes.repositories.ClienteRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class ClienteService implements UserDetailsService {
    
    @Autowired
    private ClienteRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    // Correção do método findById para lançar ObjectNotFoundException corretamente
    public Cliente findById(@NonNull Integer id) {
        Optional<Cliente> usuario = this.usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ObjectNotFoundException(id, "Cliente não encontrado!"));
    }

    @Transactional
    public Cliente create(@NonNull Cliente usuario) {
        return this.usuarioRepository.save(usuario);
    }

    @Transactional
    public Cliente update(@NonNull Cliente usuario) {
        Cliente newUsuario = findById(usuario.getId());

        if (usuario.getNomeCompleto() != null) {
            newUsuario.setNomeCompleto(usuario.getNomeCompleto());
        }
        if (usuario.getNomeUsuario() != null) {
            newUsuario.setNomeUsuario(usuario.getNomeUsuario());
        }
        if (usuario.getDataNascimento() != null) {
            newUsuario.setDataNascimento(usuario.getDataNascimento());
        }
        if (usuario.getEmail() != null) {
            newUsuario.setEmail(usuario.getEmail());
        }
        if (usuario.getSenha() != null) {
            newUsuario.setSenha(usuario.getSenha());
        }

        return usuarioRepository.save(newUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente usuario = usuarioRepository.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return usuario;
    }

    @Transactional
    public void delete(@NonNull Integer id) { // Padronizei para Integer ao invés de Long
        Cliente usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

    public Cliente getUserByToken(String token) {
        String email = tokenService.validateToken(token);
        if (email.isEmpty()) {
            return null;
        }

        return usuarioRepository.findByEmail(email);
    }
}
