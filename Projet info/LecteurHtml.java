import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;





public class LecteurHtml{

    public static String LireHtml(String stringURL) throws IOException{
        BufferedReader lecteur = null;
        URL url = new URL(stringURL);
        URLConnection connection = url.openConnection();
        lecteur = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String s=null;
        String ligne = null;
        while ((ligne = lecteur.readLine()) != null) {
            s=s+ligne+"\n";
        }
        return s;
    }
}
