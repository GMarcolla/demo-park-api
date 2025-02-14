package com.gmarcolla.demo_park_api;

import com.gmarcolla.demo_park_api.web.dto.VagaCreateDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vagas/vagas-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/vagas/vagas-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VagaIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void CriarVaga_ComDadosValidos_RetornarLocationStatus201() {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(new VagaCreateDto("A-05", "LIVRE"))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().exists(HttpHeaders.LOCATION);

    }

    @Test
    public void CriarVaga_ComCodigoJaExistente_RetornarErrorMessageComStatus409() {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(new VagaCreateDto("A-04", "LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody()
                .jsonPath("status").isEqualTo(409);
    }

    @Test
    public void CriarVaga_ComDadosInvalidos_RetornarErrorMessageComStatus422() {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(new VagaCreateDto("", ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(new VagaCreateDto("A-0", "LIVRE"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422);

        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .bodyValue(new VagaCreateDto("A-04", "LIVR"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody()
                .jsonPath("status").isEqualTo(422);
    }

    @Test
    public void CriarVaga_ComCodigoInexistenteComCliente_RetornarErrorMessageComStatus403() {
        testClient
                .post()
                .uri("/api/v1/vagas")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
                .bodyValue(new VagaCreateDto("A-06", "LIVRE"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo(403);
    }

    @Test
    public void BuscarVagaPeloCodigo_ComCodigoExistente_RetornarVagaStatus200() {
        testClient
                .get()
                .uri("/api/v1/vagas/{codigo}", "A-01")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("id").isEqualTo("10")
                .jsonPath("codigo").isEqualTo("A-01")
                .jsonPath("status").isEqualTo("LIVRE");
    }

    @Test
    public void BuscarVagaPeloCodigo_ComCodigoInexistente_RetornarErrorMessageStatus404() {
        testClient
                .get()
                .uri("/api/v1/vagas/{codigo}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vagas/A-10");
    }

    @Test
    public void BuscarVagaPeloCodigo_ComCodigoExistentePorCliente_RetornarErrorMessageStatus403() {
        testClient
                .get()
                .uri("/api/v1/vagas/{codigo}", "A-10")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@gmail.com", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("method").isEqualTo("GET")
                .jsonPath("path").isEqualTo("/api/v1/vagas/A-10");
    }

}
