package com.magma.main.DataAccess;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class DbConnection {

    private static MongoClient DBClient;

    private static MongoDatabase connect() {
        try {
            String connectionString = "mongodb+srv://waseet_scraper:WaS33t-sCraPper@cluster0.it99a.mongodb.net/waseet_data?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(connectionString);
            DBClient = mongoClient;
            MongoDatabase db = mongoClient.getDatabase("waseet_data");
            return db;

        } catch (Exception e) {
            System.out.println("Connection error  ***********************************************");
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.out.println("Connection error  ***********************************************");
            return null;
        }

    }



    public static MongoDatabase getDBConnection() {
        return connect();
    }

    public static void getDBConnectionClose() {
        DBClient.close();
    }

}
