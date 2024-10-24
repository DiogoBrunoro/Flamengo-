package com.project.elite_construcoes.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente implements UserDetails {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome_completo", length=200, nullable=true)
    private String nomeCompleto;

    @Column(name = "nome_de_usuario", length=50, nullable=true)
    private String nomeUsuario;

    @Column(name = "data_de_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "email", length=100, nullable=true)
    private String email;

    @Column(name = "senha", length=50, nullable=true)
    private String senha;

    public Cliente() {
    }

    // Construtor
    public Cliente(Integer id, String nomeCompleto, String nomeUsuario, LocalDate dataNascimento, String email, String senha) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;
    }

    // Getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    // Métodos necessários para o UserDetails (sem authorities)
    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

     public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
    



