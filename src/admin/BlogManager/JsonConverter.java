package admin.BlogManager;

import admin.BlogManager.blogData.Comment;
import admin.BlogManager.blogData.Post;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class JsonConverter {
    private ResultSet resultSet;

    public JsonConverter() {
    }

    public void exportToJson(String jsonName, ResultSet rs) throws SQLException {
        this.resultSet = rs;
        ObjectMapper objectMapper = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
        objectMapper.setDateFormat(df);

        File f = new File("./json-files/" + jsonName + ".json");

        try {
            objectMapper.writeValue(f, getEntitiesFromResultSet(resultSet));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void importFromJson(File f) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        if (f.getName().equals("post.json")){
            List<Post> postList = objectMapper.readValue(f, new TypeReference<>() {            });
            postList.forEach(x -> System.out.println(x.toString()));
        }else{
            List<Comment> commentList = objectMapper.readValue(f, new TypeReference<>() {            });

            commentList.forEach(x -> System.out.println(x.toString()));
        }
    }

//    ====

    protected ArrayList<Map<String, Object>> getEntitiesFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Map<String, Object>> entities = new ArrayList<>();
        while (this.resultSet.next()) {
            entities.add(getEntityFromResultSet(this.resultSet));
        }
        return entities;
    }

    protected Map<String, Object> getEntityFromResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        Map<String, Object> resultsMap = new HashMap<>();
        for (int i = 1; i <= columnCount; ++i) {
            String columnName = metaData.getColumnName(i).toLowerCase();
            Object object = resultSet.getObject(i);
            resultsMap.put(columnName, object);
        }
        return resultsMap;
    }
}
