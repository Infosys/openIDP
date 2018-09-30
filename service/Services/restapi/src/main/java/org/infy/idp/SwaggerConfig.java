/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ImplicitGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 
 * The class SwaggerConfig contains swagger configuration methods for REST
 * @author Infosys
 *
 */
@SpringBootApplication
@EnableSwagger2
@ComponentScan("org.infy.idp.controller")

public class SwaggerConfig {
	private static final String SECURITY_SCHEMA_OAUTH2 = "oauth2";
	@Value("${oauthurl}")
	private String authorizeUrl;
	@Value("${clientid}")
	private String swaggerAppClientId;

	@Value("${appsecret}")
	private String swaggerClientSecret;

	@Value("${appversion}")
	private String version;

	/**
	 * method api
	 *
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().pathMapping("/").apiInfo(apiInfo())
				.securitySchemes(newArrayList(oauth()));
	}

	/**
	 * 
	 * method oauth
	 *
	 * @return SecurityScheme
	 */
	@Bean
	public SecurityScheme oauth() {
		return new OAuthBuilder().name(SECURITY_SCHEMA_OAUTH2).grantTypes(grantTypes()).scopes(scopes()).build();
	}

	/**
	 * 
	 * method securityInfo
	 *
	 * @return SecurityConfiguration
	 */
	@Bean
	public SecurityConfiguration securityInfo() {
		return new SecurityConfiguration(swaggerAppClientId, swaggerClientSecret, "realm", "swagger", "",
				ApiKeyVehicle.HEADER, "", " ");
	}

	/**
	 * 
	 * @return List<AuthorizationScope>
	 */
	private List<AuthorizationScope> scopes() {
		List<AuthorizationScope> scopes = new ArrayList<>();
		scopes.add(new AuthorizationScope("read", "Read access on the API"));
		scopes.add(new AuthorizationScope("write", "Write access on the API"));
		return scopes;
	}

	/**
	 * 
	 * @return List<GrantType>
	 */
	private List<GrantType> grantTypes() {
		GrantType grantType = new ImplicitGrantBuilder().loginEndpoint(new LoginEndpoint(authorizeUrl))
				.tokenName("access_code").build();

		return newArrayList(grantType);
	}

	/**
	 * 
	 * @return ApiInfo
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("IDP REST").description("REST Implementation for IDP Application ")
				.version(version).build();
	}

}