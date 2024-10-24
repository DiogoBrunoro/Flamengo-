package com.project.elite_construcoes.model;

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
@Table(name = "fornecedor") // 
public class Fornecedor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_fornecedor")
    private Integer idFornecedor;
    
    @Column(name = "cnpj", length = 14, nullable = true, unique = true)
    private String cnpj;
    
    @Column(name = "nome_fornecedor", length = 100, nullable = true)
    private String nomeFornecedor;

    
    @Column(name = "endereco", length = 100, nullable = true)
    private String endereco;

    @Column(name = "email_corporativo", length = 100, unique = true, nullable = true)
    private String emailCorporativo;

    @Column(name = "senha_fornecedor", length = 50, nullable = true)
    private String senhaFornecedor;

    // Construtor padrão
    public Fornecedor() {
    }

    // Construtor
    public Fornecedor(Integer idFornecedor, String cnpj, String nomeFornecedor, String endereco, String emailCorporativo, String senhaFornecedor) {
        this.idFornecedor = idFornecedor;
        this.cnpj = cnpj;
        this.nomeFornecedor = nomeFornecedor;
        this.endereco = endereco;
        this.emailCorporativo = emailCorporativo;
        this.senhaFornecedor = senhaFornecedor;
    }

    // Getters e setters
    public Integer getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFornecedor() {
        return nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmailCorporativo() {
        return emailCorporativo;
    }

    public void setEmailCorporativo(String emailCorporativo) {
        this.emailCorporativo = emailCorporativo;
    }

    public String getSenhaFornecedor() {
        return senhaFornecedor;
    }

    public void setSenhaFornecedor(String senhaFornecedor) {
        this.senhaFornecedor = senhaFornecedor;
    }

    // Métodos necessários para o UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.senhaFornecedor;
    }

    @Override
    public String getUsername() {
        return this.emailCorporativo;
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
}
