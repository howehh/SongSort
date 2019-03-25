import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.io.*;

// This is a graphical interface for sorting the entries in a raw pastebin
// of song names
public class SongSortGUI {

   public static void main(String[] args) throws IOException {      
      JFrame frame = new JFrame("Song Sorter");
      frame.setSize(400, 500);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // Default pastebin url - can be changed, but for my use this is usually the url
      JTextField urlField = new JTextField("https://pastebin.com/raw/cKP5MA5K");
      
      JTextArea results = new JTextArea(20, 30);
      JScrollPane scroll = new JScrollPane(results);
      
      // Button for sorting the entries in the pastebin
      JButton sortButton = new JButton("Sort Names");
      sortButton.addActionListener(e -> {
         try {
            SongSorter ss = new SongSorter(urlField.getText().trim());
            List<String> sorted = ss.getSortedNames();
            
            StringBuilder sb = new StringBuilder();
            for (String string : sorted) {
               sb.append(string + "\n");
            }
            results.setText(sb.toString());
         } catch (IOException exception) {
            exception.printStackTrace();
            System.exit(1);
         }
      }); 
            
      // Panel for the URL field and Sort button
      JPanel panel1 = new JPanel();
      panel1.setLayout(new GridLayout(2, 1));
      panel1.add(urlField);
      panel1.add(sortButton);
      
      // Panel for the results of the sorting
      JPanel panel2 = new JPanel();
      panel2.add(scroll);
      
      // Panel for adding everything together
      JPanel panel = new JPanel();
      panel.add(panel1);
      panel.add(panel2);
      
      frame.add(panel);
      frame.setVisible(true);
   }
}