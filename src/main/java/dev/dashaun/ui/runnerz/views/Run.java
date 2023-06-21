package dev.dashaun.ui.runnerz.views;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Run {
    private Integer id;
    private String title;
    private LocalDateTime startedOn = LocalDateTime.now();
    private LocalDateTime completedOn = LocalDateTime.now();
    private Integer miles;
    private Location location;
}
