package uz.pro.usm.domain.dto.request;

import lombok.Data;

import java.util.Set;

@Data
public class GroupRequest {
    private String name;
    private String description;
    private Long creatorId;
    private Long mentorId;
    private Set<Long> userIds;
}
