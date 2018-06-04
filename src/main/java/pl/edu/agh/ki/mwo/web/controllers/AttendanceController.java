package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.Attendance;
import pl.edu.agh.ki.mwo.model.Course;
import pl.edu.agh.ki.mwo.model.Group;
import pl.edu.agh.ki.mwo.model.Student;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;
@Controller
public class AttendanceController {
	
	@RequestMapping(value="/Attendances")
    public String listAttendances(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("attendances", DatabaseConnector.getInstance().getAttendances());
    	
        return "attendancesList";    
    }
	
	
    
    @RequestMapping(value="/AddAttendance")
    public String addAttendance(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	model.addAttribute("attendances", DatabaseConnector.getInstance().getAttendances());
        return "attendanceForm";    
    }
    
    
    
    @RequestMapping(value="/ModifyAttendance")
   public String modifyAttendance(@RequestParam(value="attendanceId", required=false) String attendanceId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Attendance at = DatabaseConnector.getInstance().getAttendance(attendanceId);
    	model.addAttribute("studentId", at.getStudentId());
    	model.addAttribute("classDate", at.getClassDate());
    	model.addAttribute("attendance", at.getAttendance());
    	model.addAttribute("courseId", at.getCourseId());
 

    	
        return "attendanceModForm";
    }
    
    @RequestMapping(value="/SaveAttendance", method=RequestMethod.POST)
    public String saveAttendance(@RequestParam(value="id", required=false) String id,
    		@RequestParam(value="studentId", required=false) String studentId,
    		@RequestParam(value="classDate", required=false) String classDate,
    		@RequestParam(value="attendance", required=false) String attendance,
    		@RequestParam(value="courseId", required=false) String courseId,
    		
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	
    	Attendance at = DatabaseConnector.getInstance().getAttendance(id);
    
    	at.setStudentId(Double.parseDouble(studentId));
    	at.setClassDate(classDate);
    	at.setAttendance(Double.parseDouble(attendance));
    	at.setCourseId(Double.parseDouble(courseId));
    	   
    	DatabaseConnector.getInstance().addAttendance(at, id);
       	model.addAttribute("students", DatabaseConnector.getInstance().getStudents());
    	model.addAttribute("message", "Nowa uczen zapisany");
         	
    	return "attendanceList";
    }
    
    

    @RequestMapping(value="/CreateAttendance", method=RequestMethod.POST)
    public String createCourse(@RequestParam(value="id", required=false) String id,
    		@RequestParam(value="studentId", required=false) String studentId,
    		@RequestParam(value="classDate", required=false) String classDate,
    		@RequestParam(value="attendance", required=false) String attendance,
    		@RequestParam(value="courseId", required=false) String courseId,
    		
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Attendance at = new Attendance();
    	at.setStudentId(Double.parseDouble(studentId));
    	at.setClassDate(classDate);
    	at.setAttendance(Double.parseDouble(attendance));
    	at.setCourseId(Double.parseDouble(courseId));
    	
    	   
    	DatabaseConnector.getInstance().addAttendance(at, id);
       	model.addAttribute("attendances", DatabaseConnector.getInstance().getAttendances());
    	model.addAttribute("message", "Nowy obecność zostal dodany");
         	
    	return "attendanceList";
    }
    

    
    
    
    @RequestMapping(value="/DeleteAttendance", method=RequestMethod.POST)
    public String deleteAttendacne(@RequestParam(value="attendanceId", required=false) String id,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteAttendance(id);  	
       	model.addAttribute("attendances", DatabaseConnector.getInstance().getAttendances());
    	model.addAttribute("message", "Obecność została usunięta");
         	
    	return "attendanceList";
    }

}
