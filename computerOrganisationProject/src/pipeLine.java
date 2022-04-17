import java.util.*;

public class pipeLine {

    public HashMap<Integer,String> stage=new HashMap<>();
    public pipeLine(){
        stage.put(1,"IF|");
        stage.put(2,"ID|");
        stage.put(3,"EX|");
        stage.put(4,"ME|");
        stage.put(5,"WB|");
    }

    public static boolean[] stall =new boolean[1000];

    public void printPipelineWOF(ArrayList<ArrayList<Integer>> a)
    {
        System.out.println("IF|ID|EX|ME|WB|");
        int pWB1 = 5;
        int pWB2=0;
        int p1=-1;
        for (int i=1; i<a.size(); i++)
        {
            int clk = i+1;
            int count = 1;
            boolean d1 = checkInstructions(a,i-1,i);
            boolean d2 = checkInstructions(a,i-2,i);
            for (int j=1; j<=i; j++)
            {
                System.out.print("   ");
            }
            while(count<=5)
            {
                if(d1 && count==3)
                {
                    if(clk>=(pWB1+1)){
                        System.out.print(stage.get(count));
                        count++;
                        clk++;
                    }
                    else{
                        System.out.print("ST|");
                        stall[clk]=true;
                        clk++;
                    }
                }
                else if(d2 && count==3)
                {
                    if(clk>=(pWB2+1)){
                        System.out.print(stage.get(count));
                        count++;
                        clk++;
                    }
                    else{
                        System.out.print("ST|");
                        stall[clk]=true;
                        clk++;
                    }
                }
                else
                {
                    if((a.get(i-1).get(0)==8 || a.get(i-1).get(0)==9||a.get(i-1).get(0)==11) && !stall[clk] && (clk>p1) && count==1)
                    {
                        clk++;
                        p1 = clk;
                        System.out.print("ST|"+stage.get(count));
                        clk++;
                        count++;
                    }
                    else if(!stall[clk] && (clk>p1))
                    {
                        if(count==1){
                            p1 = clk;
                        }
                        System.out.print(stage.get(count));
                        clk++;
                        count++;
                    }
                    else
                    {
                        System.out.print("ST|");
                        clk++;
                    }
                }

            }
            System.out.println();
        }
    }

    public void printPipelineWF(ArrayList<ArrayList<Integer>> a)
    {
        System.out.println("IF|ID|EX|ME|WB|");
        int p4 = 4;
        int p1 = 0;
        for (int i=1; i<a.size(); i++)
        {
            int clk = i+1;
            int count = 1;
            boolean d=false;
            if(a.get(i-1).get(0)==5){
                d = true;
            }
            for (int j=1; j<=i; j++)
            {
                System.out.print("   ");
            }
            while(count<=5)
            {
                if(d && count==3)
                {
                    if(clk>=(p4+1)){
                        System.out.print(stage.get(count));
                        clk++;
                        count++;
                    }
                    else{
                        System.out.print("ST|");
                        stall[clk]=true;
                        clk++;
                    }
                }
                else {
                    if ((a.get(i - 1).get(0) == 8 || a.get(i - 1).get(0) == 9 || a.get(i - 1).get(0) == 11) && !stall[clk] && (clk > p1) && count == 1) {
                        clk++;
                        p1 = clk;
                        System.out.print("ST|" + stage.get(count));
                        clk++;
                        count++;
                    } else if (!stall[clk] && (clk > p1)) {
                        if (count == 1) {
                            p1 = clk;
                        }
                        if (count == 4) {
                            p4 = clk;
                        }
                        System.out.print(stage.get(count));
                        clk++;
                        count++;
                    } else {
                        System.out.print("ST|");
                        clk++;
                    }
                }
            }
            System.out.println();
        }
    }
    public boolean checkInstructions(ArrayList<ArrayList<Integer>> a, int i,int j)
    {
        if(i==-1 || i==10 || i==7 || i==12){
            return false;
        }
        ArrayList<Integer> dI =a.get(i);
        ArrayList<Integer> sI=a.get(j);
        int i1=dI.get(0);
        int i2=sI.get(0);

        if(i1>=1 && i1<=7 && i2>=1 && i2<=2)
        {
            int d1=dI.get(1);
            int s1=sI.get(2);
            int s2=sI.get(3);
            if(d1==s1 || d1==s2) {
                return true;
            }
        }
        else if(i1>=1 && i1<=7 && i2>=3 && i2<=4)
        {
            int d1=dI.get(1);
            int s1=sI.get(2);
            if(d1==s1){
                return true;
            }
        }
        else if(i1>=1 && i1<=7 && i2>=5 && i2<=6)
        {
            int d1=dI.get(1);
            int s1=sI.get(3);
            if(d1==s1) {
                return true;
            }
        }
        return false;
    }
}
