package uz.pro.usm.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pro.usm.domain.entity.course.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
}
