import java.io.File;                    // Import the File class
import java.io.FileNotFoundException;   // Import this class to handle errors
import java.util.*;                     //Import all the utilities classes

public class simulator {

    public static void main(String[] args) throws Exception {
    
        parser scan = new parser();
        scan.parsing(new File("test1.txt"));
        ArrayList<String> Instruction = scan.Instructions;

        Register reg = new Register();

        System.out.println(parser.Labels);
        System.out.println(Instruction.size());

        while(parser.PC < Instruction.size())
        {
            if(Instruction.get(parser.PC).charAt(Instruction.get(parser.PC).length()-1) == ':'){
                parser.PC++;
                System.out.println("Program Counter: "+parser.PC);
                System.out.println();
                continue;
            }
            // Printing each instruction
            System.out.println(Instruction.get(parser.PC));

            // Fetching register type
            int Itype = reg.FetchInstruction(Instruction.get(parser.PC));  

            // Fetching all registers
            ArrayList<Integer> r = reg.FetchRegister(Instruction.get(parser.PC));

            // Evaluating the instruction
            reg.Evaluate(r, Itype);

            // printing the value of registers
            reg.print();

            System.out.println();
        }








































        
        // for(int i=0; i<Instruction.size(); i++)
        // {
        //     if(Instruction.get(i).charAt(Instruction.get(i).length()-1) == ':'){
        //         continue;
        //     }
        //     // Printing each instruction
        //     System.out.println(Instruction.get(i));

        //     // Fetching register type
        //     int Itype = reg.FetchInstruction(Instruction.get(i));  

        //     // Fetching all registers
        //     ArrayList<Integer> r = reg.FetchRegister(Instruction.get(i));

        //     // Evaluating the instruction
        //     reg.Evaluate(r, Itype);

        //     // printing the value of registers
        //     reg.print();

        //     System.out.println();
        // }
    }
}
// Declaring Register array
    //static int []R = new int[32];

    // //Instructions that are supported
    // /*
    //     1. add x0, x1, x2
    //     2. sub x0, x1, x2
    //     3. addi x0, x1, imm
    //     4. subi x0, x1, imm
    //     5. lw x0, 0(x3)
    //     6. sw x0, 0(x3)
    //     7. li x0, val
    //     8. bne x0, x1, label
    //     9. beq x0, x1, label
    //     10. j label
    // */
    // public static int FetchInstruction(String s)
    // {
    //     int i = s.indexOf(' ');
    //     String ins = s.substring(0, i);

    //     if(ins.equals("add")){
    //         return 1;
    //     }
    //     if(ins.equals("sub")){
    //         return 2;
    //     }
    //     if(ins.equals("addi")){
    //         return 3;
    //     }
    //     if(ins.equals("subi")){
    //         return 4;
    //     }
    //     if(ins.equals("lw")){
    //         return 5;
    //     }
    //     if(ins.equals("sw")){
    //         return 6;
    //     }
    //     if(ins.equals("li")){
    //         return 7;
    //     }
    //     if(ins.equals("bne")){
    //         return 8;
    //     }
    //     if(ins.equals("beq")){
    //         return 9;
    //     }
    //     if(ins.equals("j")){
    //         return 10;
    //     }

    //     return -1;
    // }
  
    // public static ArrayList<Integer> FetchRegister(String s)
    // {
    //     ArrayList<Integer> r = new ArrayList<Integer>();

    //     // Determining the type of instruction 
    //     int Itype = simulator.FetchInstruction(s);

    //     // Removing first word from instruction
    //     // so that instruction will now contain register and immi value
    //     int j = s.indexOf(' ');
    //     s = s.substring(j+1);
    //     System.out.println(s);
    
    //     // Depending on the type of instruction ArrayList contains data
    //     //e.g for add x0,x1,x2
    //     // arraylist will have value 0,1,2 i.e., all are regiater numbers
    //     //e.g for li x0, 10  i.e, 1st no. is register no. 2nd is imm value
    //     // array will have value 0, 10
    //     if(Itype <= 2)
    //     {
    //         String []reg = new String[3];
    //         s = s.replaceAll("\\s", "");
    //         reg = s.split(",");
    //         for(int i=0; i<3; i++)
    //         {
    //             int n = Integer.parseInt(reg[i].substring(1));
    //             r.add(n);
    //         }
    //     }
    //     else if(Itype==3 || Itype==4)
    //     {
    //         String []reg = new String[3];
    //         s = s.replaceAll("\\s", "");
    //         reg = s.split(",");
    //         for(int i=0; i<3; i++)
    //         {
    //             if(i!=2)
    //             {
    //                 int n = Integer.parseInt(reg[i].substring(1));
    //                 r.add(n);
    //             }
    //             else
    //             {
    //                 int n = Integer.parseInt(reg[i]);
    //                 r.add(n);
    //             }
    //         }
    //     }
    //     else if(Itype==7)
    //     {
    //         String []reg = new String[2];
    //         s = s.replaceAll("\\s", "");
    //         reg = s.split(",");
    //         int n = Integer.parseInt(reg[0].substring(1));
    //         r.add(n);
    //         n = Integer.parseInt(reg[1]);
    //         r.add(n);
    //     }
    //     return r;
    // }

    // // Evaluating the instruction
    // public static void Evaluate(ArrayList<Integer> r,int Itype)
    // {
    //     if(Itype==1)
    //     {
    //         R[r.get(0)] = R[r.get(1)] + R[r.get(2)];
    //     }
    //     else if(Itype==2)
    //     {
    //         R[r.get(0)] = R[r.get(1)] - R[r.get(2)];
    //     }
    //     else if(Itype==3)
    //     {
    //         int n = r.get(2);
    //         R[r.get(0)] = R[r.get(1)] + n;
    //     }
    //     else if(Itype==4)
    //     {
    //         int n = r.get(2);
    //         R[r.get(0)] = R[r.get(1)] - n;
    //     }
    //     else if(Itype==7)
    //     {
    //         R[r.get(0)] = r.get(1);
    //     }
    // }

    // // Fuction to print values of registers 
    // public static void print()
    // {
    //     for(int i=0; i<32; i++)
    //     {
    //         if(R[i]!=0)
    //         {
    //             System.out.println("R["+i+"] = " + R[i]);
    //         }
    //     }
    // }
