package uz.pro.usm.domain.dto.response.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LessonResponse {
    private Long id;
    private String title;
    private String content;
    private Long courseId;
}
