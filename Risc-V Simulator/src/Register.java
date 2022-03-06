import java.util.ArrayList;
import java.util.HashMap;

// Since, we have not created GUI for this phase
/*
    Since, we have not created GUI for this phase
    Currently we are printing only final state of registers 
    and Memory which are non-zero
*/ 
// The Below comments contain description about registers name:
/*
Only the given below names are supported in our program
    x0 -> Hard-Wired Zero
    x1 -> Return address
    x2 -> Stack Pointer
    x3 -> Global Pointer
    x4 -> Thread Pointer
    x5 -> Temporary/Alternate Link Register
    x6-x7 -> Temporary Register
    x8 -> Saved Register
    x9 -> Saved Register
    x10-x11 -> Function Argument
    x12-x17 -> Function Argument Registers
    x18-x27 -> Saved Registers
    x28-x31 -> Temporary Registers
*/
public class Register {

    public int []R = new int[32];
    public int []MEM = new int[1024];
    public HashMap<String,Integer> memory = new HashMap<>();
    public int memoryPointer=0;

    //e.g., Array3: .byte 45, 65, -201, 57
    public void breakMemoryInstruction(String a){
        a=a.trim();
        a=a.replaceAll("\\s+"," ");
        System.out.println(a);
        //ArrayList<Integer> c=new ArrayList<>();
        String[] b=a.split(",");
        //System.out.println("b-length: "+b.length);
        for(int i=0;i<b.length;i++){
            b[i]=b[i].trim();

            //System.out.println(b[i]+" ");
        }
        //System.out.println("Nextline");
        System.out.println();
        String[] c=b[0].split(" ");
        // System.out.println(c.length+"\n");
        // for(int i=0; i<c.length; i++)
        // {
        //     System.out.println(c[i]);
        // }
        int k=(c[0].length())-1;
        if(c[0].charAt(k)==':'&&c[1].equals(".byte")){
            memory.put(c[0].substring(0,k),memoryPointer);
            MEM[memoryPointer]=Integer.parseInt(c[2]);
            memoryPointer+=4;
            for(int i=1;i<b.length;i++){
                MEM[memoryPointer]=Integer.parseInt(b[i]);
                memoryPointer+=4;
            }
        }

    }
    
    public void addToMemory(ArrayList<String> a){
        if(a==null){
            System.err.print("Syntax error !");
        }

        for(int k=1;k<a.size()&&!a.get(k).equals("_start:");k++){
            breakMemoryInstruction(a.get(k));
        }
    }

     //Instructions that are supported
    /*
        1. add x0, x1, x2
        2. sub x0, x1, x2
        3. addi x0, x1, imm
        4. subi x0, x1, imm
        5. lw x0, 0(x3)
        6. sw x0, 0(x3)
        7. li x0, imm
        8. bne x0, x1, label
        9. beq x0, x1, label
        10. j label
        11. ble x1, x2, label
        12. jr ra
        13. la x0, address
    */
    public int FetchInstruction(String s)
    {
        int i = s.indexOf(' ');
        String ins = s.substring(0, i);

        if(ins.equals("add")){
            return 1;
        }
        if(ins.equals("sub")){
            return 2;
        }
        if(ins.equals("addi")){
            return 3;
        }
        if(ins.equals("subi")){
            return 4;
        }
        if(ins.equals("lw")){
            return 5;
        }
        if(ins.equals("sw")){
            return 6;
        }
        if(ins.equals("li")){
            return 7;
        }
        if(ins.equals("bne")){
            return 8;
        }
        if(ins.equals("beq")){
            return 9;
        }
        if(ins.equals("j")){
            return 10;
        }
        if(ins.equals("ble")){
            return 11;
        }
        if(ins.equals("jr")){
            return 12;
        }
        if(ins.equals("la")){
            return 13;
        }

        return -1;
    }
  
    public ArrayList<Integer> FetchRegister(String s)
    {
        ArrayList<Integer> r = new ArrayList<Integer>();

        // Empty array list for handling errors
        ArrayList<Integer> r0 = new ArrayList<Integer>();
        // Determining the type of instruction 
        int Itype = FetchInstruction(s);

        // Handling error 
        // If wrong instruction is typed by user then it will display 
        // the appropriate message
        if(Itype == -1){
            System.err.println("Syntex error at Line : "+(parser.PC+1));
            System.err.println("This instruction is not supported. ");
            return r0; 
        }
        
        // Removing first word from instruction
        // so that instruction will now contain register and immi value
        int j = s.indexOf(' ');
        s = s.substring(j+1);
        //System.out.println(s);
    
        // Depending on the type of instruction ArrayList contains data
        //e.g for add x0,x1,x2
        // arraylist will have value 0,1,2 i.e., all are regiater numbers
        //e.g for li x0, 10  i.e, 1st no. is register no. 2nd is imm value
        // array will have value 0, 10
        if(Itype <= 2)
        {
            String []reg = new String[3];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");
            for(int i=0; i<3; i++)
            {   
                // Handling error for wrong register name
                if(reg[i].charAt(0)!='x'){
                    System.err.println("Syntex error at Line : "+(parser.PC+1));
                    System.err.println("Wrong register name.");
                    return r0;
                }
                int n = Integer.parseInt(reg[i].substring(1));
                if((n<=9 && reg[i].length()>2)||n>31){
                    System.err.println("Syntex error at Line : "+(parser.PC+1));
                    System.err.println("Wrong register name.");
                    return r0;
                }
                r.add(n);
            }
        }
        else if(Itype==3 || Itype==4)
        {
            String []reg = new String[3];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");
            for(int i=0; i<3; i++)
            {
                if(i!=2)
                {
                    // Handling error for wrong register name
                    if(reg[i].charAt(0)!='x'){
                        System.err.println("Syntex error at Line : "+parser.PC+1);
                        System.err.println("Wrong register name.");
                        return r0;
                    }
                    int n = Integer.parseInt(reg[i].substring(1));
                    if((n<=9 && reg[i].length()>2)||n>31){
                        System.err.println("Syntex error at Line : "+(parser.PC+1));
                        System.err.println("Wrong register name.");
                        return r0;
                    }
                    r.add(n);
                }
                else
                {
                    try {
                        int n = Integer.parseInt(reg[i]);
                        r.add(n);
                    } catch (NumberFormatException e) {
                        System.err.println("Syntex error at line: "+ (parser.PC+1));
                        System.err.println("Incorrect immediate value");
                        return r0;
                    }
                }
            }
        }
        else if(Itype==5 || Itype==6)
        {
            String []reg = new String[3];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");
            
            if(reg[0].charAt(0)!='x'){
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Wrong register name.");
                return r0;
            }

            int n = Integer.parseInt(reg[0].substring(1));
            if((n<=9 && reg[0].length()>2)||n>31){
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Wrong register name.");
                return r0;
            }
            //System.out.println(n);
            r.add(n);
            int i;
            String r1 = "";
            String r2 = "";
            for(i=0; i<reg[1].length(); i++)
            {
                if(reg[1].charAt(i) == '(' ){
                    break;
                }
                r1 = r1 + reg[1].charAt(i);
            }
            
            try {
                n = Integer.parseInt(r1);
                r.add(n);
            } catch (Exception e) {
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Invalid offset value.");
            }

            for(int k=i+1; k<reg[1].length(); k++)
            {
                if(reg[1].charAt(k) == ')' ){
                    break;
                }
                r2 = r2 + reg[1].charAt(k);
            }

            if(r2.charAt(0)!='x'){
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Wrong register name.");
                return r0;
            }
            n = Integer.parseInt(r2.substring(1));
            r.add(n);
        }
        else if(Itype==7)
        {
            String []reg = new String[2];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");

            // Handling error for wrong register name
            if(reg[0].charAt(0)!='x'){
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Wrong register name.");
                return r0;
            }
            int n = Integer.parseInt(reg[0].substring(1));
            if((n<=9 && reg[0].length()>2)|| n>31){
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Wrong register name.");
                return r0;
            }
            r.add(n);

            try {
                n = Integer.parseInt(reg[1]);
                r.add(n);
            } catch (NumberFormatException e) {
                System.err.println("Syntex error at line: "+ (parser.PC+1));
                System.err.println("Incorrect immediate value");
                return r0;
            }
        }
        else if(Itype==8 || Itype==9 || Itype==11)
        {
            String []reg = new String[3];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");
            for(int i=0; i<2; i++)
            {
                // Handling error for wrong register name
                if(reg[i].charAt(0)!='x'){
                    System.err.println("Syntex error at Line : "+(parser.PC+1));
                    System.err.println("Wrong register name.");
                    return r0;
                }
                int n = Integer.parseInt(reg[i].substring(1));
                if((n<=9 && reg[i].length()>2)|| n>31){
                    System.err.println("Syntex error at Line : "+(parser.PC+1));
                    System.err.println("Wrong register name.");
                    return r0;
                }
                r.add(n);
            }
            try {
                int n = parser.Labels.get(reg[2]);
                r.add(n);
            } catch (Exception e) {
                System.err.println("Invalid Label at line : " +(parser.PC+1));
                return r0;
            }
        }
        else if(Itype==10)
        {
            s = s.replaceAll("\\s", "");
            try {
                int n = parser.Labels.get(s);
                r.add(n);
            } catch (Exception e) {
                System.err.println("Invalid Label at line : " +(parser.PC+1));
                return r0;
            }
        }
        else if(Itype==12)
        {
            s = s.replaceAll("\\s", "");
            if(s.equals("ra")){
                parser.PC = -1;
            }
            else{
                System.err.println("Invalid Instruction at line : " +(parser.PC+1));
                return r0;
            }
        }
        else if(Itype==13){
            s = s.replaceAll("\\s", "");
            String[] reg = s.split(",");
            if(reg[0].charAt(0)!='x'){
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Wrong register name.");
                return r0;
            }
            int n = Integer.parseInt(reg[0].substring(1));
            if((n<=9 && reg[0].length()>2)|| n>31){
                System.err.println("Syntex error at Line : "+(parser.PC+1));
                System.err.println("Wrong register name.");
                return r0;
            }
            r.add(n);
            // n = memory.get(reg[1]);
            // r.add(n);
            try {
                n = memory.get(reg[1]);
                r.add(n);
            } catch (Exception e) {
                System.err.println("Invalid Label at line : " +(parser.PC+1));
                return r0;
            }
        }

        return r;
    }

    // Evaluating the instruction
    public void Evaluate(ArrayList<Integer> r,int Itype)
    {
        if(Itype==1)
        {
            R[r.get(0)] = R[r.get(1)] + R[r.get(2)];
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
        }
        else if(Itype==2)
        {
            R[r.get(0)] = R[r.get(1)] - R[r.get(2)];
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
        }
        else if(Itype==3)
        {
            int n = r.get(2);
            R[r.get(0)] = R[r.get(1)] + n;
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
        }
        else if(Itype==4)
        {
            int n = r.get(2);
            R[r.get(0)] = R[r.get(1)] - n;
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
        }
        else if(Itype==5)
        {
            try {
                R[r.get(0)] = MEM[r.get(1)+R[r.get(2)]];
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
            } 
            catch (Exception e) {
                System.err.println("IndexOutOfBoundException at line: "+ (parser.PC+1));
                parser.PC = -1;
            }
        }
        else if(Itype==6)
        {
            try {
                MEM[r.get(1)+R[r.get(2)]] = R[r.get(0)];
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
            } 
            catch (IndexOutOfBoundsException e) {
                System.err.println("IndexOutOfBoundException at line: "+ (parser.PC+1));
                parser.PC = -1;
            }
        }
        else if(Itype==7)
        {
            try {
                R[r.get(0)] = r.get(1);
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
            } 
            catch (IndexOutOfBoundsException e) {
                System.err.println("IndexOutOfBoundException");
                System.err.println("Syntex error at line: "+ (parser.PC+1));
                parser.PC = -1;
            }
        }
        else if(Itype==8)
        {
            if(R[r.get(0)] != R[r.get(1)]){
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
                parser.PC = r.get(2)-1;
            }
            else{
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
            }
        }
        else if(Itype==9)
        {
            if(R[r.get(0)] == R[r.get(1)]){
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
                parser.PC = r.get(2)-1;
            }
            else{
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
            }
        }
        else if(Itype==10)
        {
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
            parser.PC = r.get(0)-1;
        }
        else if(Itype==11)
        {
            if(R[r.get(0)] <= R[r.get(1)]){
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
                parser.PC = r.get(2)-1;
            }
            else{
                parser.PC++;
                System.out.println("Program Counter : " + parser.PC);
            }
        }
        else if(Itype==13)
        {
            R[r.get(0)] = r.get(1);
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);   
        }
    }

    // Fuction to print values of registers 
    public void print()
    {
        System.out.println("Registers:");
        for(int i=0; i<32; i++)
        {
            if(R[i]!=0)
            {
                System.out.print("R["+i+"] = " + R[i]+", ");
            }
        }
        System.out.println("\nMemory:");
        for(int i=0; i<1024; i++)
        {
            if((MEM[i]!=0))
            {
                System.out.print("MEM["+i+"] = " + MEM[i]+", ");
            }
        }
        System.out.println("\n-----------------------------------------------------------------------------------------------------------------------");
    }
    
}
