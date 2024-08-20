package uz.pro.usm.service.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pro.usm.domain.dto.request.course.LessonRequest;
import uz.pro.usm.domain.dto.response.course.LessonResponse;
import uz.pro.usm.domain.entity.course.Course;
import uz.pro.usm.domain.entity.course.Lesson;
import uz.pro.usm.repository.course.CourseRepository;
import uz.pro.usm.repository.course.LessonRepository;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public LessonResponse createLesson(LessonRequest lessonRequest) {
        Course course = courseRepository.findById(lessonRequest.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lesson lesson = new Lesson();
        lesson.setTitle(lessonRequest.getTitle());
        lesson.setContent(lessonRequest.getContent());
        lesson.setCourse(course);

        lesson = lessonRepository.save(lesson);

        return new LessonResponse(lesson.getId(), lesson.getTitle(), lesson.getContent(), course.getId());
    }

    @Transactional
    public LessonResponse updateLesson(Long id, LessonRequest lessonRequest) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lesson.setTitle(lessonRequest.getTitle());
        lesson.setContent(lessonRequest.getContent());

        lessonRepository.save(lesson);

        return new LessonResponse(lesson.getId(), lesson.getTitle(), lesson.getContent(), lesson.getCourse().getId());
    }

    public LessonResponse getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        return new LessonResponse(lesson.getId(), lesson.getTitle(), lesson.getContent(), lesson.getCourse().getId());
    }

    @Transactional
    public void deleteLesson(Long id) {
        lessonRepository.deleteById(id);
    }
}

