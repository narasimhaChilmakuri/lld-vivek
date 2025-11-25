import java.util.ArrayList;
import java.util.List;

public class Course {

    private int id;
    private String title;
    private String description;
    private List<Module> moduleList = new ArrayList<>();

    //constructors
    public Course() {
    }

    public Course(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public void addModule(Module module){
        moduleList.add(module);
    }

    public List<Module> getModules(){
        return moduleList;
    }

    public List<Lesson> getAllLessons(){
        List<Lesson> allLessons = new ArrayList<>();
        for(Module m : moduleList){
            allLessons.addAll(m.getLessons());
        }
        return allLessons;
    }

}

class Module {
    private int id;
    private String title;
    private List<Lesson> lessonList = new ArrayList<>();

    public void addLesson(Lesson lesson){
        lessonList.add(lesson);
    }

    public List<Lesson> getLessons(){
        return lessonList;
    }

    //constructors
    public Module() {
    }
    public Module(int id, String title) {
        this.id = id;
        this.title = title;
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}

class Lesson {

    private int id;
    private String title;
    private String contentUrl;
    private int durationMinutes;

    //constructors
    public Lesson(){

    }

    public Lesson(int id,String title,String contentUrl,int durationMinutes){
        this.id = id;
        this.title = title;
        this.contentUrl = contentUrl;
        this.durationMinutes = durationMinutes;
    }

    //getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContentUrl() {
        return contentUrl;
    }
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}


