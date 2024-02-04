package domain.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserAudit {
    private Long id;
    private Long user_id;
    private String action;
    private Timestamp dataTime;
}
