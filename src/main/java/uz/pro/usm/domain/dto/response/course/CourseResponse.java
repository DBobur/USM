package uz.pro.usm.domain.dto.response.course;


import lombok.Data;
import java.util.List;

@Data
public class CourseResponse {
    private Long id;
    private String title;
    private String description;
    private List<LessonResponse> lessons;
}

