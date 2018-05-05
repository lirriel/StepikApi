package company.com.stepikapi;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Course implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private long _id;
    @SerializedName("id")
    @Expose
    @ColumnInfo
    private long id;
    @SerializedName("score")
    @Expose
    @ColumnInfo
    private double score;
    @SerializedName("targetType")
    @Expose
    @ColumnInfo
    private String targetType;
    @SerializedName("course")
    @Expose
    @ColumnInfo
    private int course;
    @SerializedName("courseOwner")
    @Expose
    @ColumnInfo
    private long courseOwner;
    @SerializedName("courseTitle")
    @Expose
    @ColumnInfo
    private String courseTitle;
    @SerializedName("cover")
    @Expose
    @ColumnInfo
    private byte[] cover;
    @SerializedName("urlCover")
    @Expose
    @ColumnInfo
    private String urlCover;
    @SerializedName("isFave")
    @Expose
    @ColumnInfo
    private boolean isFave;

    public Course() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public long getCourseOwner() {
        return courseOwner;
    }

    public void setCourseOwner(long courseOwner) {
        this.courseOwner = courseOwner;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public String getUrlCover() {
        return urlCover;
    }

    public void setUrlCover(String urlCover) {
        this.urlCover = urlCover;
    }

    public void setFave(boolean f) {
        isFave = f;
    }

    public boolean isFave() {
        return isFave;
    }
}
