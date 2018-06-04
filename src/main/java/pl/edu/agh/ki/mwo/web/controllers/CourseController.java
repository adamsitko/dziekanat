package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Course;
import pl.edu.agh.ki.mwo.model.Group;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;
@Controller
public class CourseController {
	
	@RequestMapping(value="/Courses")
    public String listStudents(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("courses", DatabaseConnector.getInstance().getCourses());
    	
        return "coursesList";    
    }
	
	
    
    @RequestMapping(value="/AddCourse")
    public String addCourse(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("courses", DatabaseConnector.getInstance().getCourses());
        return "coursesForm";    
    }
    
    
    
    @RequestMapping(value="/ModifyCourse")
   public String modifyCourse(@RequestParam(value="courseId", required=false) String courseId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Course course = DatabaseConnector.getInstance().getCourse(courseId);
    	model.addAttribute("courseName", course.getName());
    	model.addAttribute("courseTeacher", course.getTeacher());
 

    	
        return "courseModForm";
    }
    
    @RequestMapping(value="/SaveCourse", method=RequestMethod.POST)
    public String saveCourse(@RequestParam(value="courseName", required=false) String courseName,
    		@RequestParam(value="courseTeacher", required=false) String teacher,
    		@RequestParam(value="courseId", required=false) String courseId,
    		
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	
    	Course course = DatabaseConnector.getInstance().getCourse(courseId);
    
    	course.setName(courseName);
    	course.setTeacher(teacher);
    	   
    	DatabaseConnector.getInstance().addCourse(course, courseId);
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Nowa uczen zapisany");
         	
    	return "studentList";
    }
    
    

    @RequestMapping(value="/CreateCourse", method=RequestMethod.POST)
    public String createCourse(@RequestParam(value="courseName", required=false) String courseName,
    		@RequestParam(value="teacher", required=false) String teacher,
    		@RequestParam(value="courseId", required=false) String courseId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Course course = new Course();
    	course.setName(courseName);
    	course.setTeacher(teacher);
    	
    	   
    	DatabaseConnector.getInstance().addCourse(course, courseId);
       	model.addAttribute("courses", DatabaseConnector.getInstance().getCourses());
    	model.addAttribute("message", "Nowy kurs zostal dodany");
         	
    	return "courseList";
    }
    

    
    
    
    @RequestMapping(value="/DeleteCourse", method=RequestMethod.POST)
    public String deleteCourse(@RequestParam(value="courseId", required=false) String courseId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteCourse(courseId);  	
       	model.addAttribute("students", DatabaseConnector.getInstance().getCourses());
    	model.addAttribute("message", "Kurs został usunięty");
         	
    	return "courseList";
    }

}
