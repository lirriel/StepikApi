package company.com.stepikapi.model.Entity;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private List<Course> courseList;

    public Search() {
        courseList = new ArrayList<>();
    }

    public void add(Course course) {
        courseList.add(course);
    }

    public List<Course> getCourseList() {
        return courseList;
    }

}
