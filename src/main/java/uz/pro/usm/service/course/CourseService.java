package uz.pro.usm.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pro.usm.domain.dto.request.course.CourseRequest;
import uz.pro.usm.domain.dto.response.course.CourseResponse;
import uz.pro.usm.domain.dto.response.course.LessonResponse;
import uz.pro.usm.domain.entity.course.Course;
import uz.pro.usm.repository.course.CourseRepository;
import uz.pro.usm.repository.course.LessonRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    public CourseResponse createCourse(CourseRequest courseRequest) {
        Course course = new Course();
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        course = courseRepository.save(course);

        return mapToCourseResponse(course);
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setTitle(courseRequest.getTitle());
        course.setDescription(courseRequest.getDescription());
        courseRepository.save(course);

        return mapToCourseResponse(course);
    }

    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        return mapToCourseResponse(course);
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(this::mapToCourseResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    private CourseResponse mapToCourseResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getId());
        courseResponse.setTitle(course.getTitle());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setLessons(
                course.getLessons().stream()
                        .map(lesson -> {
                            return new LessonResponse(lesson.getId(), lesson.getTitle(), lesson.getContent(), course.getId());
                        }).collect(Collectors.toList())
        );
        return courseResponse;
    }
}
