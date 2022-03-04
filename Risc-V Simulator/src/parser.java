import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class parser {

    public ArrayList<String> Instructions = new ArrayList<String>();
    public static HashMap <String,Integer> Labels = new HashMap<>();
    public static int PC = 0;
    
    public void parsing(File file) throws FileNotFoundException
    {
        int count = 0;
        // Reading file
        Scanner Readfile = new Scanner(file);

        // Reading each line of a file
        while(Readfile.hasNextLine())
        {
            String s = Readfile.nextLine();
            s = s.trim();       // Removing all leading and trailing spaces

            // Removing comment from instruction line
            for(int i=0; i<s.length(); i++)
            {
                if(s.charAt(i) == '#'){
                    s = s.substring(0,i);
                    break;
                }
            }
            // Neglecting all empty lines and comments
            if(s.length()==0 || s.charAt(0)=='#'){
                continue;
            }
            else{
                count++;
                if(s.charAt(s.length()-1) == ':')
                {
                    Labels.put(s.substring(0,s.length()-1),count+1);
                }
                // Storing all Tnstructions in arraylist 
                // Otherthan empty lines and comments
                Instructions.add(s);
            }
        }
    }
}

