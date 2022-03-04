import java.util.ArrayList;
import java.util.HashMap;

public class Register {

    public int []R = new int[32];
    public int []MEM = new int[1024];

     //Instructions that are supported
    /*
        1. add x0, x1, x2
        2. sub x0, x1, x2
        3. addi x0, x1, imm
        4. subi x0, x1, imm
        5. lw x0, 0(x3)
        6. sw x0, 0(x3)
        7. li x0, val
        8. bne x0, x1, label
        9. beq x0, x1, label
        10. j label
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

        return -1;
    }
  
    public ArrayList<Integer> FetchRegister(String s)
    {
        ArrayList<Integer> r = new ArrayList<Integer>();

        // Determining the type of instruction 
        int Itype = FetchInstruction(s);
        
        // Removing first word from instruction
        // so that instruction will now contain register and immi value
        int j = s.indexOf(' ');
        s = s.substring(j+1);
        System.out.println(s);
    
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
                int n = Integer.parseInt(reg[i].substring(1));
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
                    int n = Integer.parseInt(reg[i].substring(1));
                    r.add(n);
                }
                else
                {
                    int n = Integer.parseInt(reg[i]);
                    r.add(n);
                }
            }
        }
        else if(Itype==5 || Itype==6)
        {
            String []reg = new String[3];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");
            int n = Integer.parseInt(reg[0].substring(1));
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
            n = Integer.parseInt(r1);
            //System.out.println(n);
            r.add(n);
            for(int k=i+1; k<reg[1].length(); k++)
            {
                if(reg[1].charAt(k) == ')' ){
                    break;
                }
                r2 = r2 + reg[1].charAt(k);
            }
            n = Integer.parseInt(r2.substring(1));
            //System.out.println(n);
            r.add(n);
        }
        else if(Itype==7)
        {
            String []reg = new String[2];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");
            int n = Integer.parseInt(reg[0].substring(1));
            r.add(n);
            n = Integer.parseInt(reg[1]);
            r.add(n);
        }
        else if(Itype==8 || Itype==9)
        {
            String []reg = new String[3];
            s = s.replaceAll("\\s", "");
            reg = s.split(",");
            for(int i=0; i<2; i++)
            {
                int n = Integer.parseInt(reg[i].substring(1));
                r.add(n);
            }
            int n = parser.Labels.get(reg[2]);
            r.add(n);
        }
        else if(Itype==10)
        {
            s = s.replaceAll("\\s", "");
            int n = parser.Labels.get(s);
            r.add(n);
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
            R[r.get(0)] = MEM[r.get(1)+R[r.get(2)]];
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
        }
        else if(Itype==6)
        {
            MEM[r.get(1)+R[r.get(2)]] = R[r.get(0)];
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
        }
        else if(Itype==7)
        {
            R[r.get(0)] = r.get(1);
            parser.PC++;
            System.out.println("Program Counter : " + parser.PC);
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
    }

    // Fuction to print values of registers 
    public void print()
    {
        for(int i=0; i<32; i++)
        {
            if(R[i]!=0)
            {
                System.out.println("R["+i+"] = " + R[i]);
            }
        }
        for(int i=0; i<1024; i++)
        {
            if(MEM[i]!=0)
            {
                System.out.println("MEM["+i+"] = " + MEM[i]);
            }
        }
    }
    
}
