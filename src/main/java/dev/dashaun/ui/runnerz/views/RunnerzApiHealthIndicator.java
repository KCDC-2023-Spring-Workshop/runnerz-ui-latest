package dev.dashaun.ui.runnerz.views;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
class RunnerzApiHealthIndicator implements HealthIndicator {

    private final RunnerService runnerService;

    public RunnerzApiHealthIndicator(RunnerService runnerService) {
        this.runnerService = runnerService;
    }

    @Override
    public Health health() {
        int errorCode = check();
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    private int check() {
        if (!runnerService.isHealthy()) {
            return 1;
        }
        return 0; // healthy
    }

}
