package com.project.elite_construcoes.infra.security;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return  httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Permite todas as requisições OPTIONS (CORS preflight)
                    .requestMatchers(HttpMethod.GET, "/cliente/{id}").permitAll() // Permite acesso ao cliente por ID
                    .requestMatchers(HttpMethod.POST, "/cliente/login").permitAll() // Permite login do cliente
                    .requestMatchers(HttpMethod.POST, "/cliente/register").permitAll() // Permite cadastro de cliente
                    .requestMatchers(HttpMethod.POST, "/cliente/tipoUser").authenticated() // Necessita estar autenticado para consultar o tipo de usuário
                    .requestMatchers(HttpMethod.PUT, "/cliente/{id}").authenticated() // Atualização de cliente autenticada
                    .requestMatchers(HttpMethod.DELETE, "/cliente/delete/{id}").authenticated() // Exclusão de cliente autenticada
                    .requestMatchers(HttpMethod.GET, "/fornecedor/{id}").permitAll() // Permite acesso ao fornecedor por ID
                    .requestMatchers(HttpMethod.POST, "/fornecedor/login").permitAll() // Permite login do fornecedor
                    .requestMatchers(HttpMethod.POST, "/fornecedor/register").permitAll() // Permite cadastro de fornecedor
                    .requestMatchers(HttpMethod.POST, "/fornecedor/tipoUser").authenticated() // Necessita estar autenticado para consultar o tipo de usuário
                    .requestMatchers(HttpMethod.PUT, "/fornecedor/{id}").authenticated() // Atualização de fornecedor autenticada
                    .requestMatchers(HttpMethod.DELETE, "/fornecedor/delete/{id}").authenticated() // Exclusão de fornecedor autenticada
                    .anyRequest().authenticated() // Qualquer outra requisição precisa estar autenticada
                )
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500", "http://127.0.0.1:5501")); // Adicionar o novo domínio
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
                    corsConfiguration.setAllowedHeaders(List.of("*")); // Todos os headers permitidos
                    corsConfiguration.setAllowCredentials(true); // Caso seja necessário enviar cookies/autenticação
                    return corsConfiguration;
                }))
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
