package company.com.stepikapi.model.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Course implements Serializable, Parcelable{
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

    protected Course(Parcel in) {
        _id = in.readLong();
        id = in.readLong();
        score = in.readDouble();
        targetType = in.readString();
        course = in.readInt();
        courseOwner = in.readLong();
        courseTitle = in.readString();
        cover = in.createByteArray();
        urlCover = in.readString();
        isFave = in.readByte() != 0;
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(_id);
        dest.writeLong(id);
        dest.writeDouble(score);
        dest.writeString(targetType);
        dest.writeInt(course);
        dest.writeLong(courseOwner);
        dest.writeString(courseTitle);
        dest.writeByteArray(cover);
        dest.writeString(urlCover);
        dest.writeByte((byte) (isFave ? 1 : 0));
    }
}
