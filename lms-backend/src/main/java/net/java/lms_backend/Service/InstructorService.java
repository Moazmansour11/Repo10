package net.java.lms_backend.Service;

import net.java.lms_backend.Repositrory.CourseRepository;
import net.java.lms_backend.Repositrory.LessonRepositery;
import net.java.lms_backend.dto.LessonDTO;
import net.java.lms_backend.entity.Course;
import net.java.lms_backend.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//3mlt add le feature el instructer y add lesson
@Service
public class InstructorService {

    private final CourseRepository courseRepo;
    private final LessonRepositery lessonRepo;

    @Autowired
    public InstructorService(CourseRepository courseRepo, LessonRepositery lessonRepo) {
        this.courseRepo = courseRepo;
        this.lessonRepo = lessonRepo;
    }

    @Transactional
    public LessonDTO addLessonToCourse(Long courseId, LessonDTO lessonDTO) {

        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
        Lesson lesson = new Lesson();
        lesson.setTitle(lessonDTO.getTitle());
        lesson.setContent(lessonDTO.getContent());
        lesson.setCourse(course);

        Lesson savedLesson = lessonRepo.save(lesson);

        return new LessonDTO(savedLesson);
    }
}
