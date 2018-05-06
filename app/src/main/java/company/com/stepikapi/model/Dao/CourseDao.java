package company.com.stepikapi.model.Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import company.com.stepikapi.model.Entity.Course;

@Dao
public interface CourseDao {
    @Insert
    void insertAll(Course... courses);

    @Query("DELETE FROM course")
    void deleteAll();

    @Delete
    void delete(Course course);

    @Insert
    void insert(Course course);

    @Query("SELECT * FROM course")
    List<Course> getAllCourse();

    @Query("SELECT COUNT(*) from course")
    int countCourse();

    @Query("SELECT * FROM course WHERE id=:id")
    Course getCourseByID(long id);

    @Query("SELECT cover FROM course WHERE id=:id")
    byte[] getCover(long id);

    @Query("select * FROM course WHERE courseTitle=:title")
    Course isIn(String title);

    @Query("UPDATE course SET cover=:image  WHERE id=:id")
    int updateCurrencyImage(long id, byte[] image);
}
