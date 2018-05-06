package company.com.stepikapi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

        private List<Course> items = new ArrayList<>();

        private CourseViewHolder.OnItemClickListener listener;
        private static AppDatabase appDatabase;

        public CourseAdapter(CourseViewHolder.OnItemClickListener listener, AppDatabase appDatabase) {
            this.listener = listener;
            CourseAdapter.appDatabase = appDatabase;
        }

        @NonNull
        @Override
        public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View layout = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item, parent, false);
            return new CourseViewHolder(layout, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public void setData(List<Course> newItems) {
            items = newItems;
            notifyDataSetChanged();
        }

        public List<Course> getData() {
            return items;
        }

        public void remove(Course item) {
            int position = items.indexOf(item);
            items.remove(position);
            notifyItemRemoved(position);
        }

        public void add(Course item) {
            items.add(item);
            notifyItemInserted(items.size() - 1);
        }

        static class CourseViewHolder extends RecyclerView.ViewHolder {

            Context context;
            OnItemClickListener listener;
            TextView courseName;
            ImageView courseLogo;
            Button favourite;

            CourseViewHolder(View itemView, OnItemClickListener listener) {
                super(itemView);

                Log.v("viewHolder", "created");
                context = itemView.getContext();

                this.listener = listener;

                courseName = itemView.findViewById(R.id.title);
                courseLogo = itemView.findViewById(R.id.cover);
                favourite = itemView.findViewById(R.id.button_favourite);
            }

            void setFave(boolean isFavourite) {
                if (isFavourite)
                    favourite.setBackground(ContextCompat.getDrawable(context, R.drawable.star_on));
                else
                    favourite.setBackground(ContextCompat.getDrawable(context, R.drawable.star_off));
            }

            void bind(Course courseInfo) {
                Log.v("bind", "started");
                if (appDatabase.getCourseDao().isIn(courseInfo.getCourseTitle()) != null) {
                    courseInfo.setFave(true);
                    favourite.setBackground(ContextCompat.getDrawable(context, R.drawable.star_on));
                }

                if (listener != null)
                    itemView.setOnClickListener(view -> listener.onClick(courseInfo));
                courseName.setText(courseInfo.getCourseTitle());

                favourite.setOnClickListener(v -> {
                    if (courseInfo.isFave()) {
                        courseInfo.setFave(false);
                        appDatabase.getCourseDao().delete(courseInfo);
                    }
                    else {
                        courseInfo.setFave(true);
                        appDatabase.getCourseDao().insert(courseInfo);
                    }
                    setFave(courseInfo.isFave());
                    //appDatabase.getCourseDao().updateCurrencyFave(courseInfo.getId(), courseInfo.isFave());
                    Log.v("fave", "added");
                });

                setFave(courseInfo.isFave());

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .fallback(android.R.color.holo_orange_light)
                        .error(R.mipmap.ic_launcher_round);

                byte[] image = appDatabase.getCourseDao().getCover(courseInfo.getId());
                if (image != null) {
                    courseLogo.setImageDrawable(BitmapTranslator.bitmapToDrawable(BitmapTranslator.getImage(image), context));
                    Log.v("bind", "load from db");
                } else {
                    if (courseInfo.getCover() != null)
                        courseLogo.setImageDrawable(BitmapTranslator.bitmapToDrawable(BitmapTranslator.getImage(courseInfo.getCover()), context));
                    else
                        Glide.with(itemView)
                            .load(courseInfo.getUrlCover())
                            .apply(options)
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    courseInfo.setCover(BitmapTranslator.getBitmapAsByteArray(BitmapTranslator.drawableToBitmap(resource)));
                                    Log.v("bind", "load success");
                                    appDatabase.getCourseDao().updateCurrencyImage(courseInfo.getId(), courseInfo.getCover());
                                    return false;
                                }
                            })
                            .into(courseLogo);
                }
            }


            public interface OnItemClickListener {
                void onClick(Course currency);
            }
        }
}
