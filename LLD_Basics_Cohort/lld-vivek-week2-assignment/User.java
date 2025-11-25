import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String name;
    private String email;
    private String mobile;

    public User(int id, String name, String email,String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public int getId(){return id;}
    public String getEmail(){
        return email;
    }
    public String getName(){
        return name;
    }
    public String getMobile(){return mobile}

    public boolean login(String password){
        return true;
    }

}

class Instructor extends User {

    Instructor(int id,String name, String email,String mobile){
        super(id,name,email,mobile);
    }

    private List<Course> courseList = new ArrayList<>();

    public List<Course> getCourses(){
        return courseList;
    }

    public void addCourse(Course course){
        courseList.add(course);
    }

}


class Student extends User {

    Student(int id, String name, String email,String mobile) {
        super(id, name, email,mobile);
    }
    private List<Enrollment> enrollmentList = new ArrayList<>();

    public List<Enrollment> getEnrollments(){
        return enrollmentList;
    }


    public void addEnrollment(Enrollment enrollment) {
        enrollmentList.add(enrollment);
    }
}
