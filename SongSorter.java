import java.util.*;
import java.io.*;
import java.net.*;
import org.json.*; // requires the JSON-java library for parsing json data

public class SongSorter {
   private String url;

   // Constructs a new SongSorter from the given url
   // Throws IllegalArgumentException if the url is not a raw pastebin
   //    (i.e. https://pastebin.com/raw/...)
   public SongSorter(String url) {
      if (!url.contains("https://pastebin.com/raw/")) {
         throw new IllegalArgumentException("URL must be a raw pastebin link");
      }
      this.url = url;
   }

   public static void main(String[] args) throws IOException {
      Scanner input = new Scanner(System.in);
      String url = promptUrl(input);
      SongSorter ss = new SongSorter(url);
      
      List<String> sorted = ss.getSortedNames();
      
      for (String name : sorted) {
         System.out.println(name);
      }
   }
   
   // Prompts for the URL of the raw pastebin with the names.
   // Throws IO exception if error in reading user input
   private static String promptUrl(Scanner input) throws IOException {
      System.out.println("Enter the URL of the raw pastebin:");
      return input.nextLine();
   }
   
   // Returns the data from the URL in json format
   private String getPastebinJson() throws IOException {
      // Retrieves data from the given url
      URL pastebin = new URL(this.url);
      URLConnection connection = pastebin.openConnection();
      BufferedReader input = 
            new BufferedReader(new InputStreamReader(connection.getInputStream())); 
      
      StringBuilder sb = new StringBuilder();
      String inputLine;
      // Converts the http response into a string 
      while ((inputLine = input.readLine()) != null) {
         sb.append(inputLine);
      }
      input.close();
      
      return sb.toString(); 
   }
   
   // Returns an alphabetically sorted list of entries in the pastebin
   // Pastebin should be json formatted: {"videos":[ {"name": "a-ha - Take On Me", ... } ]
   public List<String> getSortedNames() throws IOException {
      String json = getPastebinJson();
      JSONObject object = new JSONObject(json);
      JSONArray array = object.getJSONArray("videos");
      
      List<String> result = new ArrayList<String>();
      
      for (int i = 0; i < array.length(); i++) {
         JSONObject currObj = array.getJSONObject(i); 
         result.add(currObj.getString("name")); 
      }
      Collections.sort(result, new SortIgnoreCase());
      return result;
   }
   
   // This comparator is used to sort Strings regardless of capitalization
   private class SortIgnoreCase implements Comparator<String> {
      public int compare(String s1, String s2) {
         return s1.toLowerCase().compareTo(s2.toLowerCase());
      }
   }
}