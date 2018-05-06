package company.com.stepikapi.view.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import company.com.stepikapi.model.Entity.Course;
import company.com.stepikapi.R;
import company.com.stepikapi.view.Utils.BitmapTranslator;

public class DetailsActivity extends AppCompatActivity {
    private TextView title;
    private TextView author;
    private TextView type;
    private ImageView imageView;
    private Course course;
    private static final String COURSE_TAG = "course";

    public static void start(Activity activity, Course course) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(COURSE_TAG, (Parcelable) course);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (course == null && getIntent() != null)
            course = getIntent().getParcelableExtra(COURSE_TAG);

        if (course != null) {
            init();
            setCourse(course);
        }
    }

    /**
     * initialize view
     */
    private void init() {
        author = findViewById(R.id.course_author);
        type = findViewById(R.id.course_type);
        title = findViewById(R.id.title);
        imageView = findViewById(R.id.course_logo);
    }

    /**
     * Set info about course on activity
     */
    private void setCourse(Course course) {
        type.setText(course.getTargetType());
        title.setText(course.getCourseTitle());
        author.setText(course.getCourseOwner()+"");
        imageView.setImageDrawable(BitmapTranslator.bitmapToDrawable(BitmapTranslator.getImage(course.getCover()), this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(COURSE_TAG, course);
        Log.d(COURSE_TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        course = savedInstanceState.getParcelable(COURSE_TAG);
        Log.d(COURSE_TAG, "onRestoreInstanceState");
    }

}
