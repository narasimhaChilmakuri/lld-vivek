import java.time.temporal.JulianFields;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EnrollmentService {

    INotificationChannel notificationChannel;
    ICourseRepository courseRepository;
    IEnrollmentRepository enrollmentRepository;

    public EnrollmentService(INotificationChannel notificationChannel,ICourseRepository courseRepository, IEnrollmentRepository enrollmentRepository){
        this.notificationChannel = notificationChannel;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    Enrollment enroll(Student student,Course course){
        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(course,student);
        if(enrollment!=null){
            notificationChannel.send(student,"Already Enrolled in the course " + course.getTitle());
            return enrollment;
        }
        enrollment = new Enrollment(0,student,course);
        enrollmentRepository.save(enrollment);
        notificationChannel.send(student,student.getName() + " enrolled in " + course.getTitle());
        student.addEnrollment(enrollment);
        return enrollment;
    }

    public void completeLesson(Enrollment enrollment,Lesson lesson){

        if(enrollment==null || lesson==null) {
            return;
        }
        boolean completed = enrollment.markLessonCompleted(lesson);
        if(!completed){
            notificationChannel.send(enrollment.getStudent(),"Lesson " + lesson.getTitle() + "was already completed or something went wrong");
            return;
        }

        enrollmentRepository.save(enrollment);
        notificationChannel.send(enrollment.getStudent(),enrollment.getStudent().getName() + " have completed " + lesson.getTitle() + "lesson");

        if (enrollment.getStatus() == EnrollmentStatus.COMPLETED) {
            notificationChannel.send(enrollment.getStudent(), "Congratulations! You have completed the course: " + enrollment.getCourse().getTitle());
        }
    }

}

class Enrollment {

    private int id;
    private Date enrollmentDate;
    private EnrollmentStatus status;
    private float progressPercent;

    private Student student;
    private Course course;

    private Set<Integer> completedLessonIds = new HashSet<>();

    //constructors
    public Enrollment() {
    }

    public Enrollment(int id, Student student,Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
        progressPercent = 0;
        enrollmentDate = new Date();
        status = EnrollmentStatus.IN_PROGRESS;
    }

    public boolean markLessonCompleted(Lesson lesson){
        if(lesson==null){
            return false;
        }
        if(completedLessonIds.contains(lesson.getId())){
            return false;
        }

        completedLessonIds.add(lesson.getId());
        computeProgress();
        return true;
    }

    private void computeProgress(){
        List<Lesson> allLessons = course.getAllLessons();
        int total = allLessons.size();

        if(total==0){
            progressPercent = 100;
            status = EnrollmentStatus.COMPLETED;
            return;
        }

        float pct = (completedLessonIds.size() * 100f )/total;
        this.progressPercent = Math.round(pct *100f)/100f;
        if(completedLessonIds.size()>=total){
            status = EnrollmentStatus.COMPLETED;
        }

    }


    public float getProgressPercent(){
        return progressPercent;
    }
    public Student getStudent(){
        return student;
    }
    public Course getCourse(){
        return course;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getEnrollmentDate() {
        return enrollmentDate;
    }
    public EnrollmentStatus getStatus() {
        return status;
    }
    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }
    public void setProgressPercent(float progressPercent) {
        this.progressPercent = progressPercent;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    public Set<Integer> getCompletedLessonIds() {
        return completedLessonIds;
    }

}


enum EnrollmentStatus {
    IN_PROGRESS,
    COMPLETED,
    CANCELED
}
