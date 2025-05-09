package net.java.lms_backend.dto;


import net.java.lms_backend.entity.Lesson;

public class LessonDTO {
    private String title;

    private String content;

   

    public LessonDTO(Lesson savedLesson) {
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}