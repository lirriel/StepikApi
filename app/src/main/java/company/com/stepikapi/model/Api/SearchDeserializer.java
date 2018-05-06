package company.com.stepikapi.model.Api;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

import company.com.stepikapi.model.Entity.Course;
import company.com.stepikapi.model.Entity.Search;

public class SearchDeserializer implements JsonDeserializer<Search> {

    @Override
    public Search deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context
    ) throws JsonParseException {
        Search search = new Search();

        if (json.isJsonObject()) {
            JsonArray results = json.getAsJsonObject().getAsJsonArray("search-results");

            for (JsonElement element: results) {
                JsonObject courseJson = element.getAsJsonObject();
                Course course = new Course();
                course.setCourse(courseJson.get("course").getAsInt());
                course.setCourseOwner(courseJson.get("course").getAsLong());
                course.setCourseTitle(courseJson.get("course_title").getAsString());
                course.setId(courseJson.get("id").getAsLong());
                course.setScore(courseJson.get("score").getAsDouble());
                course.setTargetType(courseJson.get("target_type").getAsString());
                course.setUrlCover(courseJson.get("course_cover").getAsString());

                search.add(course);
            }
        }

        return search;
    }

}
