import java.io.File;                    // Import the File class
import java.io.FileNotFoundException;   // Import this class to handle errors
import java.util.*;                     //Import all the utilities classes

public class simulator {

    public static void main(String[] args) throws Exception {

        parser scan = new parser();
        scan.parsing(new File("./src/test.s"));
        ArrayList<String> Instruction = scan.Instructions;
        ArrayList<String> upperInstruction = scan.upperInstructions;

        Register reg = new Register();
        reg.addToMemory(upperInstruction);
        reg.print();

        //System.out.println(parser.Labels);
        //System.out.println(Instruction.size());

        while(parser.PC < Instruction.size() && parser.PC>=0)
        {
            if(Instruction.get(parser.PC).charAt(Instruction.get(parser.PC).length()-1) == ':'){
                parser.PC++;
                //System.out.println("Program Counter: "+parser.PC);
                //System.out.println();
                continue;
            }
            // Printing each instruction
            System.out.println(Instruction.get(parser.PC));

            // Fetching register type
            int Itype = reg.FetchInstruction(Instruction.get(parser.PC));

            // Fetching all registers
            ArrayList<Integer>r = reg.FetchRegister(Instruction.get(parser.PC));

            if(r.size() == 0){
                System.err.println("Program is terminated.");
                break;
            }

            // Evaluating the instruction
            reg.Evaluate(r, Itype);
            // if(parser.PC <= 1){
            reg.R[0]=0;
            reg.print();
            // }

            if(parser.PC == -1){
                break;
            }
            // printing the value of registers
            //reg.print();

            //System.out.println();
        }
        System.out.println();
        System.out.println("Memory and Registers representation at the end: ");
        reg.print();
    }
}

