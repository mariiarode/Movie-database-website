package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import database.MVdb;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.List;

@WebServlet(name = "suggestionsFrontend", value = "/suggestionsFrontend")
public class suggestionsFrontend extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");
        Reader input = new InputStreamReader(request.getInputStream());
        JSONObject json;
        JSONParser jsonParser = new JSONParser();

        try {
            json = (JSONObject) jsonParser.parse(input);
        } catch (ParseException e) {
            json = new JSONObject();
        }
        String query = ((String) json.get("value")).toUpperCase(); // Convert query to uppercase for case-insensitive matching
        PrintWriter out = response.getWriter();

        JSONArray suggestions = new JSONArray();

        try {
            MVdb mvdb = new MVdb();
            List<String> movieTitles = mvdb.searchMovieTitlesStartingWith(query); // Implement a method in MVdb to search for movie titles based on the query
            for (String title : movieTitles) {
                suggestions.add(title);
            }
            mvdb.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        json.put("suggestions", suggestions);

        out.println(json.toJSONString());
        out.close();
    }
}
