package utils;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;

public class DataDriven {

    public static TestData jsonReader() {

        String filePath = "testData/testData.json";

        try (FileReader reader = new FileReader(filePath)) {

            Gson gson = new Gson();

            TestData data = gson.fromJson(reader, TestData.class);

            System.out.println("Username: " + data.validUser.username);
            System.out.println("Password: " + data.validUser.password);
            System.out.println("Cart Products: " + data.cartProducts.length);

            return data;

        } catch (IOException e) {

            throw new RuntimeException("Unable to read testData.json", e);
        }
    }

    public static class TestData {

        public UserData validUser;
        public UserData invalidUser;
        public String[] cartProducts;
    }

    public static class UserData {

        public String username;
        public String password;
    }
}