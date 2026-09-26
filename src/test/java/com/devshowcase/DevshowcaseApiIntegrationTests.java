package com.devshowcase;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DevshowcaseApiIntegrationTests {
    private final HttpClient client = HttpClient.newHttpClient();

    @LocalServerPort
    private int port;

    @Test
    void completeRequiredEndpointFlow() throws Exception {
        HttpResponse<String> profile = post("/api/profiles",
                "{\"name\":\"Bruno\",\"email\":\"bruno@example.com\",\"bio\":\"Desenvolvedor\"}");
        assertThat(profile.statusCode()).isEqualTo(201);
        long profileId = readId(profile.body());

        HttpResponse<String> technology = post("/api/technologies", "{\"name\":\"Spring Boot\"}");
        assertThat(technology.statusCode()).isEqualTo(201);
        long technologyId = readId(technology.body());

        HttpResponse<String> project = post("/api/projects", """
                {"title":"DevShowcase","description":"API de portfólio",
                "repositoryUrl":"https://github.com/example/devshowcase",
                "profileId":%d,"technologyIds":[%d]}
                """.formatted(profileId, technologyId));
        assertThat(project.statusCode()).isEqualTo(201);
        assertThat(project.body()).contains("DevShowcase", "Spring Boot");

        assertThat(get("/api/profiles/" + profileId).statusCode()).isEqualTo(200);
        assertThat(get("/api/technologies").body()).contains("Spring Boot");
        assertThat(get("/api/projects").body()).contains("DevShowcase");
    }

    @Test
    void rejectsInvalidProfile() throws Exception {
        HttpResponse<String> response = post("/api/profiles", "{\"name\":\"\",\"email\":\"invalido\"}");
        assertThat(response.statusCode()).isEqualTo(400);
        assertThat(response.body()).contains("name", "email", "Dados inválidos");
    }

    private HttpResponse<String> post(String path, String json) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url(path)))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private HttpResponse<String> get(String path) throws Exception {
        return client.send(HttpRequest.newBuilder(URI.create(url(path))).GET().build(),
                HttpResponse.BodyHandlers.ofString());
    }

    private long readId(String json) {
        Matcher matcher = Pattern.compile("\\\"id\\\"\\s*:\\s*(\\d+)").matcher(json);
        assertThat(matcher.find()).as("A resposta deve conter um id").isTrue();
        return Long.parseLong(matcher.group(1));
    }

    private String url(String path) {
        return "http://localhost:" + port + path;
    }
}
