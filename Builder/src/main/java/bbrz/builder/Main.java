package bbrz.builder;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Course helloWorld = Course.courseBuilder()
                .description("Hello World")
                .type(CourseType.BEGINNER)
                .build();

        Course quicksort = Course.courseBuilder()
                .description("Learn recursive programing")
                .type(CourseType.EXPERT)
                .hours(34)
                .build();

        Course firstTryInSnake = Course.courseBuilder()
                .description("Its the Snack game")
                .type(CourseType.INTERMEDIATE)
                .hours(22)
                .build();

        Course patterns = Course.courseBuilder()
                .description("Learn your first patterns")
                .type(CourseType.INTERMEDIATE)
                .hours(17)
                .build();

        List<Course> courses = new ArrayList<>();
        courses.add(helloWorld);
        courses.add(firstTryInSnake);
        courses.add(patterns);
        courses.add(quicksort);

        for (Course course : courses) {
            System.out.println(course);
        }
    }
}