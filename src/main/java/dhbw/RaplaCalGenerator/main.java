package dhbw.RaplaCalGenerator;
import java.io.FileNotFoundException;
import java.io.IOException;
        
/**
 *
 * @author Robin Schlenker
 */
public class main {
    
    public static void main(String [ ] args) throws FileNotFoundException, IOException
    {
      Interfacer ui = new Interfacer();
      ui.initUI();
    }
}

