package com.example.json_to_pojo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	public static void main(String[] args) throws IOException {
		String id;
		
		BufferedReader idReader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("### The Library Genesis API ###");
		System.out.print("Input Book's ID : ");
		id = idReader.readLine();
        
        URL url = new URL("http://libgen.io/json.php?ids=" + id + "&fields=Title,Author,Year,Publisher,City,Pages,Topic,Language,CoverUrl,Issn");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        
        try(BufferedReader jsonReader  = new BufferedReader(new InputStreamReader(connection.getInputStream()))){
	        Gson gson = new GsonBuilder().create();
	        Book[] book = gson.fromJson(jsonReader, Book[].class);
	        System.out.println(book[0]);
        }
        catch(Exception ex) {
        	System.out.println(ex.toString());
        }
    }
}

