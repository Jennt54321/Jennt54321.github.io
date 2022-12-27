
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException{


        // TODO Auto-generated method stub
        
        String filename = "使用者已修課程_IB.csv";
        File reader =new File(filename);
        Scanner input=new Scanner(reader);
        String[] deptScanInput = input.nextLine().split(" ");
        String[] deptScanInput2 = deptScanInput[1].split(",");
        String deptInput=deptScanInput2[0];
        Department myDept=deptSelector(deptInput);

        while(input.hasNextLine()){
            String [] userInfo = input.next().split(",");
            String name = userInfo[0];
            double credits = Double.parseDouble(userInfo[1]);
            String category = userInfo[2];
            String subcategory = userInfo[3];
            if(subcategory.equals("none")){
                Course newCourse=new Course(name,credits,category);
                myDept.addCourse(newCourse);
            }else{
                Course newCourse=new Course(name,credits,category,subcategory);
                myDept.addCourse(newCourse);
            }

        }

        myDept.summarize();
        input.close();
    }

    public static Department deptSelector(String dept){
        switch(dept){
            case "BA":
                Department ba=new BA();;
                return ba;
            case "IB":
                Department ib=new IB();
                //呼叫Department裡面的method，把系上要求的必修課程以csv的方式匯入
                ib.addRequire("國貿系_必修.csv");
                IB ib2 = (IB)ib;
                //呼叫ACCT裡面的method，把系上要求的群修課程匯入
                ib2.addPartiallyRequired();
                return ib;
            case "ACCT":
                Department acct=new ACCT();
                acct.addRequire("會計系_必修.csv");
                ACCT acct2 = (ACCT)acct;
                //呼叫ACCT裡面的method，把系上要求的群修課程匯入
                acct2.addPartiallyRequired();
                return acct;
            default:
                Department myDept=new Department();
                return myDept;
        }
    }




}
