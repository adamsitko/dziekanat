package pl.edu.agh.ki.mwo.web.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.agh.ki.mwo.model.DegreeCourse;
import pl.edu.agh.ki.mwo.model.Group;
import pl.edu.agh.ki.mwo.persistence.DatabaseConnector;

@Controller
public class GroupController {

    @RequestMapping(value="/Groups")
    public String listStudents(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";

    	model.addAttribute("groups", DatabaseConnector.getInstance().getGroups());
    	
        return "groupsList";    
    }
    
    @RequestMapping(value="/AddGroup")
    public String displayAddGroupForm(Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
        return "groupForm";    
    }

    @RequestMapping(value="/CreateGroup", method=RequestMethod.POST)
    public String createGroup(@RequestParam(value="groupProfile", required=false) String profile,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	Group gr = new Group();
    	gr.setProfile(profile);
    	
    	DatabaseConnector.getInstance().addGroup(gr);    	
       	model.addAttribute("groups", DatabaseConnector.getInstance().getGroups());
    	model.addAttribute("message", "Nowa grupa została dodana");
         	
    	return "groupsList";
    }
    
    @RequestMapping(value="/DeleteGroup", method=RequestMethod.POST)
    public String deleteGroup(@RequestParam(value="groupId", required=false) String groupId,
    		Model model, HttpSession session) {    	
    	if (session.getAttribute("userLogin") == null)
    		return "redirect:/Login";
    	
    	DatabaseConnector.getInstance().deleteGroup(groupId);    	
       	model.addAttribute("groups", DatabaseConnector.getInstance().getGroups());
    	model.addAttribute("message", "Grupa została usunięta");
         	
    	return "groupsList";
    }


}