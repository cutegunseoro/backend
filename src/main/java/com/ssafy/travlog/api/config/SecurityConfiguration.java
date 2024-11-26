package com.ssafy.travlog.api.config;

import java.util.Arrays;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	private final SecretKey key;

	public SecurityConfiguration(@Value("${jwt.secret}") String secret) {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			//                .cors((cors) -> {
			//                    cors.configurationSource(new CorsConfigurationSource() {
			//                        @Override
			//                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
			//                            CorsConfiguration corsConfiguration = new CorsConfiguration();
			//                            corsConfiguration.addAllowedOriginPattern("http://localhost:5173");
			//                            corsConfiguration.addAllowedMethod("*");
			//                            corsConfiguration.addAllowedHeader("*");
			//                            corsConfiguration.setAllowCredentials(true);
			//                            return corsConfiguration;
			//                        }
			//                    });
			//                })
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf((csrf) -> {
				csrf.disable();
			})
			.authorizeHttpRequests((authorize) -> {
				authorize.requestMatchers("/auth/*").permitAll();
				//                    authorize.requestMatchers("/members/{publicId}").permitAll();
				authorize.anyRequest().authenticated();
			})
			.sessionManagement((session) -> {
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			})
			.oauth2ResourceServer((oauth2) -> {
				oauth2.jwt(Customizer.withDefaults());
			});
		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withSecretKey(this.key).build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return webSecurity -> webSecurity.ignoring().requestMatchers("/auth/*");
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:5173", "http://localhost:4173"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
		configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
		configuration.setAllowCredentials(true); // 쿠키 허용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 설정 적용
		return source;
	}
}
