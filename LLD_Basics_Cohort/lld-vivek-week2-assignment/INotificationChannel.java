import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface INotificationChannel {
    public void send(User user,String message);
}

class SmsNotificationChannel implements INotificationChannel{
    @Override
    public void send(User user, String message) {
        //send an sms through user mobile number and message
        System.out.println("[SMS] to " + user.getName() + " (" + user.getMobile() + "): " + message);
    }
}

class EmailNotificationChannel implements INotificationChannel{

    @Override
    public void send(User user, String message) {
        // use email from user and message to send an email notification
        //to -> user.getEmail()  msg - > message
        System.out.println("[Email] to " + user.getEmail() + ": " + message);
    }
}

interface IEnrollmentRepository {

    Enrollment findById(int id);
    Enrollment findByStudentAndCourse(Course course,Student student);
    void save(Enrollment enrollment);

}

class InMemoryEnrollmentRepository implements IEnrollmentRepository{

    private Map<Integer,Enrollment> enrollmentMap = new HashMap<>();
    @Override
    public Enrollment findById(int id) {
        return enrollmentMap.get(id);
    }

    @Override
    public Enrollment findByStudentAndCourse(Course course, Student student) {
        for(Enrollment e : enrollmentMap.values()){
            if(student.getId()==e.getStudent().getId() && e.getCourse().getId()==course.getId()){
                return e;
            }
        }
        return null;
    }

    @Override
    public void save(Enrollment enrollment) {
        enrollmentMap.put(enrollmentMap.size()+1, enrollment);
    }
}

interface ICourseRepository {
    public Course findById(int id);
    public List<Course> findByInstructor(int id);
    public void save(Course course);
}

class InMemoryCourseRepository implements ICourseRepository{

    private Map<Integer,Course> courseMap = new HashMap<>();

    @Override
    public Course findById(int id) {
        return courseMap.get(id);
    }

    @Override
    public List<Course> findByInstructor(int id) {
        List<Course> courses = new ArrayList<>();
        for(Course course : courseMap.values()){
            courses.add(course);
        }
        return courses;
    }

    @Override
    public void save(Course course) {
        courseMap.put(courseMap.size() + 1, course);
    }
}
