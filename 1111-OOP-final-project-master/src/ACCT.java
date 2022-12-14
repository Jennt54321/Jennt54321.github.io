
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ACCT extends Department {
    final double REQUIREDCREDITS=68.0;
    final double PARTIALLYREQUIREDCREDITS=6.0;
    final double TOTALCREDITS=142.0;
    final double SELECTIVENEED=TOTALCREDITS-PARTIALLYREQUIREDCREDITS-REQUIREDCREDITS-GENERALCREDITSNEEDED;

    //儲存系上規定之群修A
    private ArrayList<Course> deptPartiallyRequired1=new ArrayList<Course>();
    //儲存系上規定之群修B
    private ArrayList<Course> deptPartiallyRequired2=new ArrayList<Course>();
    //儲存使用者修過的群A
    private ArrayList<Course> partiallyRequired1=new ArrayList<Course>();
    //儲存使用者修過的群B
    private ArrayList<Course> partiallyRequired2=new ArrayList<Course>();
    double required=0;
    //儲存群A修習的總學分數
    double partiallyRequiredCredits1=0;
    //儲存群B修習的總學分數
    double partiallyRequiredCredits2=0;
    double selective=0;

    public void requiredJudgement(ArrayList<Course> courses){

        //儲存重複課程的index
        ArrayList<Integer> storeIndex = new ArrayList<Integer>();
        for(Course userCourse:courses){
            String userCourseName = userCourse.getName();
            for (Course deptCourse: deptRequired){
                String deptCourseName = deptCourse.getName();
                if (deptCourseName.equals(userCourseName)){
                    required+=deptCourse.getCredits();
                    storeIndex.add(deptRequired.indexOf(deptCourse));
                }
            }
        }
        for (int index: storeIndex){
            deptRequired.remove(index);
        }

        if(required==REQUIREDCREDITS){
            requirements.set(2, true);
            System.out.println("required credits pass");
        }else{
            requirements.set(2, false);
            System.out.println("Following course are needed: ");
            for (Course deptCourse: deptRequired){
                System.out.print(deptCourse.getName()+" ");
            }
            System.out.printf("total required credits:%.2f\n",required);
        }


    }

    public void selectiveJudgement(ArrayList<Course> courses){
        for(Course course:courses){
            selective+=course.getCredits();
        }

        if(selective>=SELECTIVENEED){
            requirements.set(3,true);
            System.out.println("selective credits pass");
        }else{
            requirements.set(3,false);
            System.out.printf("total selective credits:%.2f\n",selective);

        }
    }

    //傳入系所規定的群修課程
    public void addPartiallyRequired() {
		
		try {
			String[] courseInfo;
			double credit;
            File file = new File("會計系_群A.csv");
			Scanner readFile = new Scanner(file);
            
            //讀csv檔，並把科系要求的課程加進群修A的arrayList
			while (readFile.hasNext()) {

                courseInfo = readFile.next().split(",");
				credit = Double.parseDouble(courseInfo[1]);
				Course course = new Course(courseInfo[0],credit);
				deptPartiallyRequired1.add(course);	
			}
			readFile.close();
			//印出群A裡面的所有課程
			//for (Course course: deptPartiallyRequired1) {
				//System.out.print(course.getName()+" "+course.getCredits());
				//System.out.println();
			//}

            
            file = new File("會計系_群B.csv");
			readFile = new Scanner(file);
            
            //讀csv檔，並把科系要求的課程加進群修B的arrayList
			while (readFile.hasNext()) {

                courseInfo = readFile.next().split(",");
				credit = Double.parseDouble(courseInfo[1]);
				Course course = new Course(courseInfo[0],credit);
				deptPartiallyRequired2.add(course);	
			}
			readFile.close();
			//印出群B裡面的所有課程
			//for (Course course: deptPartiallyRequired2) {
				//System.out.print(course.getName()+" "+course.getCredits());
				//System.out.println();
			//}


		}catch(FileNotFoundException e){
			System.out.print("File Not Found");
		}
	}

        

    public void partiallyRequiredJudgement(ArrayList<Course> courses){

        //將使用者修過的群修分別存入進群A/群B
        for (Course course: courses){
            if (course.getSubcategory().equals("A")){
                partiallyRequired1.add(course);
            } else if (course.getCategory().equals("B")){
                partiallyRequired2.add(course);
            }
        }

        //計算群A使用者修習的課程數
        for (Course userCourse: partiallyRequired1){
            for (Course deptCourse: deptPartiallyRequired1){
                if (userCourse.getName().equals(deptCourse.getName()))
                    partiallyRequiredCredits1+=deptCourse.getCredits();
            }
        }

        //計算群B使用者修習的學分數
        for (Course userCourse: partiallyRequired2){
            for (Course deptCourse: deptPartiallyRequired2){
                if (userCourse.getName().equals(deptCourse.getName()))
                    partiallyRequiredCredits2+=deptCourse.getCredits();
            }
        }

        //判斷使用者群修的學分修習狀況
        if (partiallyRequiredCredits1 >=3 && partiallyRequiredCredits2 >=3){
            requirements.set(4, true);
            System.out.println("partiallyRequired credits pass");
        } else if (partiallyRequiredCredits1 < 3){
            System.out.println("尚未修群A");
        } else if (partiallyRequiredCredits2 < 3){
            System.out.println("尚未修群B");
        }
    }

    public void generalRequirement(ArrayList<Course> generalCourses){
        super.generalRequirement(generalCourses);
    }

    public void PERequirement(){
        super.PERequirement();
    }

    public void summarize(){

        PERequirement();
        generalRequirement(generalCourses);
        requiredJudgement(requireds);
        selectiveJudgement(selectives);
        partiallyRequiredJudgement(partiallyRequireds);

        int counter=0;
        super.summarize();
        if(counter==6){
            System.out.println("meet all the requirement needed for graduation");
        }else{
            System.out.println("-".repeat(60));
            for(boolean passed :requirements){
                if(!passed){
                    switch(requirements.indexOf(false)){
                        case 0:
                            System.out.printf("%.2f general course credits are needed\n",GENERALCREDITSNEEDED-generalCredits);
                        case 1:
                            System.out.printf("%.2f PE courses are needed\n",PECREDITSNEEDED-PE.size());
                        case 2:
                            System.out.printf("%.2f required credits are needed\n",REQUIREDCREDITS-required);
                        case 3:
                            System.out.printf("%.2f selective credits are needed\n",SELECTIVENEED-selective);
                        case 4:
                            System.out.printf("%.2f partially required credits are needed\n",PARTIALLYREQUIREDCREDITS-partiallyRequiredCredits2-partiallyRequiredCredits1);
                    }
                    break;
                }
            }
        }
    }
}
