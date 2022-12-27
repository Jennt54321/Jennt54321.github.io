import java.util.ArrayList;

public class BA extends Department  {
    final double REQUIREDCREDITS=54;
    final double TOTALCREDITS=128;
    final double SELECTIVENEED=TOTALCREDITS-REQUIREDCREDITS-GENERALCREDITSNEEDED;
    double required=0;
    double selective=0;

    public void requiredJudgement(ArrayList<Course> courses){
        for(Course course:courses){
            required+=course.getCredits();
        }

        if(required==REQUIREDCREDITS){
            requirements.set(2, true);
            System.out.println("required credits pass");
        }else{
            requirements.set(2, false);
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

    public void summarize(){

        PERequirement();
        generalRequirement(generalCourses);
        requiredJudgement(requireds);
        selectiveJudgement(selectives);

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
                    }
                    break;
                }
            }
        }
    }

}






