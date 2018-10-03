package it.objectmethod.webpage.reading;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ReadFromWebPage {

	 public static String readFromWebPage(String indirizzo) throws MalformedURLException, IOException {

	        BufferedReader br = null;
	        StringBuilder sb = new StringBuilder();

	        try {

	            URL url = new URL(indirizzo);
	            URLConnection connectionUrl = url.openConnection();
	            br = new BufferedReader(new InputStreamReader(connectionUrl.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = br.readLine()) != null) {

	                sb.append(line);
	            }
	            br.close();
	        } finally {

	            if (br != null) {
	                br.close();
	            }
	        }
	        return sb.toString();
	    }
	 public static String readFromWebCabCodePage(String indirizzo) throws MalformedURLException, IOException {

	        BufferedReader br = null;
	        StringBuilder sb = new StringBuilder();

	        try {

	            URL url = new URL(indirizzo);
	            URLConnection connectionUrl = url.openConnection();
	            br = new BufferedReader(new InputStreamReader(connectionUrl.getInputStream(),"utf-8"));
	            String line;
	            while ((line = br.readLine()) != null) {

	                sb.append(line);
	            }
	            br.close();
	        } finally {

	            if (br != null) {
	                br.close();
	            }
	        }
	        return sb.toString();
	    }
}

