package bbrz.builder;

public class Course {

    private final String description;
    private final int hours;
    private final CourseType type;

    private Course(String description, int hours, CourseType type) {
        this.description = description;
        this.hours = hours;
        this.type = type;
    }

    public static Builder courseBuilder() {
        return new Builder();
    }

    @Override
    public String toString() {
        return "Course" +
                "description=" + description + "\nhours=" + hours + "\ntype=" + type + "\n";
    }

    public static class Builder {
        private String description;
        private int hours;
        private CourseType type;

        private Builder() {}

        public Builder description(String description){
            this.description=description;
            return this;
        }

        public Builder hours(int hours) {
            this.hours = hours;
            return this;
        }

        public Builder type(CourseType type) {
            this.type = type;
            return this;
        }

        public Course build() {
            return new Course(this.description, this.hours, this.type);
        }

    }
}
