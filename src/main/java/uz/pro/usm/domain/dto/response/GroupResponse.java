package uz.pro.usm.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponse {
    private Long id;
    private String name;
    private String description;
    private Long creatorId;
    private Long mentorId;
    private Set<Long> userIds;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
