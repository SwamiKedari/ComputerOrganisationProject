import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class parser {

    public ArrayList<String> Instructions = new ArrayList<String>();
    public static HashMap <String,Integer> Labels = new HashMap<>();
    public ArrayList<String> upperInstructions = new ArrayList<>();
    public static int PC = 0;

    public void parsing(File file) throws FileNotFoundException
    {
        int count = 0;
        // Reading file
        Scanner Readfile = new Scanner(file);
        while(Readfile.hasNext()){
            String s=Readfile.nextLine();
            if(s.length()==0||s.charAt(0)=='#'){
                continue;
            }
            if(!s.equals("_start:")){
                System.err.println("Syntax error !");
                System.exit(0);
            }
            else{
                break;
            }
        }

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



            if(s.equals(".data")){
                upperInstructions.add(s);
                //System.out.println(s);
                while(Readfile.hasNextLine()&&!s.equals(".text"))
                {
                    //System.out.println(s);
                    s=Readfile.nextLine();
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
                    upperInstructions.add(s);
                }
                if(s.equals(".text")){
                    //System.out.println(s);
                    upperInstructions.add(s);
                }
                /*for(String a : upperInstructions){
                    System.out.println(a);
                }*/
                break;
            }
            else{
                System.err.println("Syntax error !");
                System.exit(0);
            }
        }
        if(!Readfile.hasNext()){
            System.err.println("Syntax error !");
            System.exit(0);
        }

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


