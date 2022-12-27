import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PartiallyRequireds {
	
	String fileName;
	private ArrayList<Course> partiallyRequire = new ArrayList<Course>(); //群修
	
	public PartiallyRequireds() {
		
	}
	
	public PartiallyRequireds(String fileName) {
		this.fileName = fileName;
	}
	
	public void addCourse() {
		File file = new File(fileName);
		
		try {
			String[] courseInfo;
			double credit;
			Scanner readFile = new Scanner(file);
			while (readFile.hasNext()) {
				courseInfo = readFile.next().split(",");
				credit = Double.parseDouble(courseInfo[1]);
				Course course = new Course(courseInfo[0],credit);
				partiallyRequire.add(course);
				
			}
			readFile.close();
			
			//印出群修arrayList裡面的所有課程
			//for (Course course: partiallyRequire) {
				//System.out.print(course.getName()+" "+course.getCredits());
				//System.out.println();
			//}

		}catch(FileNotFoundException e){
			System.out.print("File Not Found");
		}
	}

}
