package company.com.stepikapi.view.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.List;

import company.com.stepikapi.model.Database.AppDatabase;
import company.com.stepikapi.viewmodel.adapter.CourseAdapter;
import company.com.stepikapi.model.Entity.Course;
import company.com.stepikapi.R;

public class ItemFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "1";
    private static final String LIST = "list";
    private OnListFragmentInteractionListener mListener;
    private int mColumnCount = 1;
    private CourseAdapter courseAdapter;
    private List<Course> courses;

    public ItemFragment() {}

    @SuppressWarnings("unused")
    public static ItemFragment newInstance(int columnCount, List<Course> courses) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        // send list of courses to fragment
        args.putSerializable(LIST, (Serializable) courses);
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        Log.v("item", "started");

        // check extra
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            Bundle bundle = getArguments();
            // get list of courses
            if (bundle != null) {
                courses = bundle.getParcelableArrayList(LIST);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        if (view instanceof RecyclerView) {
            courseAdapter = new CourseAdapter(course -> DetailsActivity.start(getActivity(), course),
                    AppDatabase.getAppDatabase(getContext().getApplicationContext()));

            Context context = view.getContext();

            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1)
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            else
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));

            // item decorator for recyclerView
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(dividerItemDecoration);

            // set adapter & data
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
