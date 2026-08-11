package utils;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class DataDriven {

    public static TestData jsonReader() {

        String filePath = "testData/testData.json";

        try (FileReader reader = new FileReader(filePath)) {

            Gson gson = new Gson();

            return gson.fromJson(reader, TestData.class);

        } catch (IOException e) {

            throw new RuntimeException("Unable to read testData.json", e);
        }
    }

    public static class TestData {

        public UserData validUser;
        public UserData invalidUser;
    }

    public static class UserData {

        public String username;
        public String password;
    }
}