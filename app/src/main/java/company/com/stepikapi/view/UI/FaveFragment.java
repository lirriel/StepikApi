package company.com.stepikapi.view.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import company.com.stepikapi.model.Database.AppDatabase;
import company.com.stepikapi.model.Api.AppDelegate;
import company.com.stepikapi.viewmodel.adapter.CourseAdapter;
import company.com.stepikapi.model.Entity.Course;
import company.com.stepikapi.R;

public class FaveFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "1";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private CourseAdapter courseAdapter;
    private AppDelegate appDelegate;
    private List<Course> courses;

    public FaveFragment() {}

    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDelegate = AppDelegate.from(getContext());
        setRetainInstance(true);

        Log.v("item", "started");
        if (courses == null)
            courses = AppDatabase.getAppDatabase(getContext().getApplicationContext()).getCourseDao().getAllCourse();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            // init course adapter
            courseAdapter = new CourseAdapter(course -> DetailsActivity.start(getActivity(), course),
                    AppDatabase.getAppDatabase(getContext().getApplicationContext()));

            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            if (mColumnCount <= 1)
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            else
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));

            // set adapter data
            recyclerView.setAdapter(courseAdapter);
            if (courses != null && courses.size() > 0)
                courseAdapter.setData(courses);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int item);
    }
}
