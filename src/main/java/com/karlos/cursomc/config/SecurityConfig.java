package com.karlos.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.karlos.cursomc.security.JWTAuthenticationFilter;
import com.karlos.cursomc.security.JWTAuthorizationFilter;
import com.karlos.cursomc.security.JWTUtil;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // permiti as restrições de ENDPOINTS para Administradores
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private Environment env; // pra garantir que o banco H2 possa ser acessado
	
	 @Autowired
	private JWTUtil jwtUtil;
	
	private static final String[] PUBLIC_MATCHERS = {"/h2-console/**"};
	private static final String[] PUBLIC_MATCHERS_GET = {"/produtos/**", "/categorias/**", "/estados/**"};
	private static final String[] PUBLIC_MATCHERS_POST = {"/clientes", "/auth/forgot/**"};
									// Cliente nao cadastrado pode cadastrar
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }
		
		http.cors().and().csrf().disable(); // CORS() aplica as configuração do BEAN CorsConfigurationSource
		
		//POR PADRÃO O SPRING SECURITY RESTRINGE TODOS OS ENDPOINTS
		//(PORÉM SOMENTE PARA AQUELES USUARIOS QUE NAO ESTAO AUTENTICADO / LOGADO)
		http.authorizeRequests()
			.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
		// ele autoriza as url do vetor
		// REGISTRO DO FILTRO DE AUTENTICAÇÃO
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		// REGISTRO DO FILTRO DE AUTORIZAÇÃO : Isso significa que os usuarios autorizados pelo LOGIN conseguem acessar
		// end points que estejam por padrão PROTEGIDOS, utilizando TOKEN (no caso da aula 74, foi acessado /pedidos)
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//Stateless assegura que o back end não vai criar seção de usuário.
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source; // configuração básica para multiplas fontes
	}
	
	@Bean // vai ser instanciado no autowired das classes (ClienteService e DBService)
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
	// ALTERNATIVA SEM HERDAR O DEPRECIADO
/*
 * public class SecurityConfig {
 
 	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors().and().csrf().disable();
        http.authorizeHttpRequests().antMatchers(PUBLIC_MATCHERS).permitAll().anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
 */

