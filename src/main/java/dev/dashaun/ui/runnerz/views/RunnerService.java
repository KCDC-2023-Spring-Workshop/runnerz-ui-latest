package dev.dashaun.ui.runnerz.views;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
class RunnerService {
    private final RestTemplate restTemplate;

    public RunnerService(RestTemplateBuilder restTemplateBuilder, @Value("${apiBase}") String runnerServiceUri) {
        this.restTemplate = restTemplateBuilder.rootUri(runnerServiceUri).build();
    }

    public String createRun(Run run) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Run> request = new HttpEntity<>(run, headers);
        return restTemplate.postForObject("/api/runs", request, String.class);
    }

    public Run[] getAll() {
        return restTemplate.getForObject("/api/runs", Run[].class);
    }

    public Boolean isHealthy() {
        try {
            String response = restTemplate.getForObject("/actuator/health", String.class);
            if (response != null && response.contains("status\":\"UP")) {
                return Boolean.TRUE;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return Boolean.FALSE;
    }
}
