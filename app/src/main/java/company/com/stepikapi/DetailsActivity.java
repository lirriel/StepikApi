package company.com.stepikapi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static android.provider.DocumentsContract.EXTRA_INFO;

public class DetailsActivity extends AppCompatActivity {
    private DetailsFragment.OnFragmentInteractionListener mListener;
    private TextView title;
    private TextView author;
    private TextView type;
    private ImageView imageView;
    private Course course;
    private static final String COURSE_TAG = "course";

    public static void start(Activity activity, Course course) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra(COURSE_TAG, course);
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
        if (course != null)
            course = (Course) getIntent().getSerializableExtra(COURSE_TAG);
        init();
        setCourse(course);
    }

    private void init() {
        author = findViewById(R.id.course_author);
        type = findViewById(R.id.course_type);
        title = findViewById(R.id.title);
        imageView = findViewById(R.id.course_logo);
    }

    private void setCourse(Course course) {
        type.setText(course.getTargetType());
        title.setText(course.getCourseTitle());
        author.setText(course.getCourseOwner()+"");
        imageView.setImageDrawable(BitmapTranslator.bitmapToDrawable(BitmapTranslator.getImage(course.getCover()), this));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(COURSE_TAG, course);
        Log.d(COURSE_TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        course = (Course) savedInstanceState.getSerializable(COURSE_TAG);
        Log.d(COURSE_TAG, "onRestoreInstanceState");
    }

}
