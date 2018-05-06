package company.com.stepikapi;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    public static void start(Activity activity, Course course) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        intent.putExtra("course", course);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        course = (Course) getIntent().getSerializableExtra("course");
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
}
