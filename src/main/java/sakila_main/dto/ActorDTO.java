package sakila_main.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActorDTO {

    private int actor_id;
    private String first_name;
    private String last_name;
    private String last_update;

    private LocalDateTime created_at;

}
