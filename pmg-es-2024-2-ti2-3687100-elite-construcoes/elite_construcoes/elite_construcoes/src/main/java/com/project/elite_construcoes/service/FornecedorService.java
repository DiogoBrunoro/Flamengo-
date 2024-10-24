package com.project.elite_construcoes.service;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.elite_construcoes.infra.security.FornecedorTokenService;
import com.project.elite_construcoes.model.Fornecedor;
import com.project.elite_construcoes.repositories.FornecedorRepository;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class FornecedorService implements UserDetailsService {
    
    @Autowired
    private FornecedorRepository usuarioRepository;

    @Autowired
    private FornecedorTokenService tokenService;

   
    public Fornecedor findById(@NonNull Integer id) {
        Optional<Fornecedor> usuario = this.usuarioRepository.findById(id);
        return usuario.orElseThrow(() -> new ObjectNotFoundException(id, "Fornecedor não encontrado!"));
    }

    @Transactional
    public Fornecedor create(@NonNull Fornecedor usuario) {
        return this.usuarioRepository.save(usuario);
    }

    @Transactional
    public Fornecedor update(@NonNull Fornecedor usuario) {
        Fornecedor newUsuario = findById(usuario.getIdFornecedor());

        if (usuario.getNomeFornecedor() != null) {
            newUsuario.setNomeFornecedor(usuario.getNomeFornecedor());
        }
        if (usuario.getCnpj() != null) {
            newUsuario.setCnpj(usuario.getCnpj());
        }
        if (usuario.getEndereco() != null) {
            newUsuario.setEndereco(usuario.getEndereco());
        }
        if (usuario.getEmailCorporativo() != null) {
            newUsuario.setEmailCorporativo(usuario.getEmailCorporativo());
        }
        if (usuario.getSenhaFornecedor() != null) {
            newUsuario.setSenhaFornecedor(usuario.getSenhaFornecedor());
        }

        return usuarioRepository.save(newUsuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Fornecedor usuario = usuarioRepository.findByEmailCorporativo(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }
        return usuario;
    }

    @Transactional
    public void delete(@NonNull Integer id) { // Padronizei para Integer ao invés de Long
        Fornecedor usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

    public Fornecedor getUserByToken(String token) {
        String email = tokenService.validateToken(token);
        if (email.isEmpty()) {
            return null;
        }

        return usuarioRepository.findByEmailCorporativo(email);
    }
}
