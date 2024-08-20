package uz.pro.usm.domain.dto.response.course;

import lombok.Data;

@Data
public class LessonResponse {
    private Long id;
    private String title;
    private String content;
    private Long courseId;
}
