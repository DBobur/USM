package uz.pro.usm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pro.usm.domain.entity.course.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {
}
