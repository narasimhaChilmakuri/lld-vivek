import java.util.*;


public class Main {
    public static void main(String[] args) {
        // Repositories
        ICourseRepository courseRepo = new InMemoryCourseRepository();
        IEnrollmentRepository enrollmentRepo = new InMemoryEnrollmentRepository();

        // Notification channel (prints to console)
        INotificationChannel email = new EmailNotificationChannel();

        // Service
        EnrollmentService enrollmentService = new EnrollmentService(email, courseRepo, enrollmentRepo);

        // Create instructor
        Instructor instructor = new Instructor(1, "Vivek Gupta", "algozenith@gmail.com","999999999");

        // Create course with 1 module and 3 lessons
        Course course = new Course(100, "LLD", "a complete low level course");
        Module module = new Module(10, "Basics");
        Lesson l1 = new Lesson(1001, "SOLID Principles", "solid principles url", 10);
        Lesson l2 = new Lesson(1002, "UML Diagrams", "Uml Diagrams Url", 15);
        Lesson l3 = new Lesson(1003, "Design Patterns", "Design Patterns Url", 20);
        module.addLesson(l1);
        module.addLesson(l2);
        module.addLesson(l3);
        course.addModule(module);

        // Save course
        courseRepo.save(course);
        instructor.addCourse(course);

        // Create student
        Student student = new Student(200, "DiscreteBody", "discreteBody@gmail.com","8888888888");

        // Enroll
        Enrollment enrollment = enrollmentService.enroll(student, course);

        // Simulate completing lessons one by one
        enrollmentService.completeLesson(enrollment, l1);
        enrollmentService.completeLesson(enrollment, l2);
        enrollmentService.completeLesson(enrollment, l3);

        // Print final enrollment status
        System.out.println("Final enrollment status: " + enrollment.getStatus());
        System.out.println("Final progress: " + enrollment.getProgressPercent() + "%");

        // Print student's enrollments
        System.out.println("Student enrollments for " + student.getName() + ":");
        for (Enrollment e : student.getEnrollments()) {
            System.out.println(" - Course: " + e.getCourse().getTitle() + ", Status: " + e.getStatus() + ", Progress: " + e.getProgressPercent() + "%");
        }
    }
}



