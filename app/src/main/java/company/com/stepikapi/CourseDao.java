package company.com.stepikapi;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import company.com.stepikapi.Course;

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

    @Query("UPDATE course SET isFave=:fave  WHERE id=:id")
    int updateCurrencyFave(long id, boolean fave);

    @Query("UPDATE course SET cover=:image  WHERE id=:id")
    int updateCurrencyImage(long id, byte[] image);
}
