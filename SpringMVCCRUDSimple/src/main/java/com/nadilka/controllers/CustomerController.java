package com.nadilka.controllers;   
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;  
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nadilka.dao.CustomerDao;
import com.nadilka.model.Category;
import com.nadilka.model.customer;
import com.nadilka.model.inventory;
import com.nadilka.model.login;  
@Controller  
public class CustomerController {  
    @Autowired  
    CustomerDao customerdao;
      
 //Customer
   
    
    @RequestMapping("/registrationForm")  
    public String showRegistrationForm(Model m){  
    	m.addAttribute("command", new customer());
    	return "registrationForm"; 
    }  
    
    @RequestMapping(value="/registration",method = RequestMethod.POST)    
    public String registration(@ModelAttribute("customer") customer cus) throws ParseException{ 
    	customerdao.saveCustomer(cus);    
    	System.out.println(cus.getDateOfBirth());
        return "mobileApp";  
    }  
    
    
    @RequestMapping("/viewCustomer")    
    public String viewemp(Model m){ 
        List<customer> list=customerdao.getCustomer();    
        m.addAttribute("list",list);  
        return "viewCustomer";    
    }  
    
    //login
    @RequestMapping("/loginForm")  
    public String showLoginForm(Model m){  
    	m.addAttribute("command", new login());
    	return "loginForm"; 
    }  
    
    @RequestMapping(value="/login",method = RequestMethod.GET)    
    public String login(@RequestParam String username, @RequestParam String password,HttpSession session){    
    	if (username.equals("admin@gmail.com") && password.equals("admin")) {
            session.setAttribute("username", username);
             return "redirect:/viewInventory";
        }
    	else {
    		 return "redirect:/loginForm";
    	}
      
    }  
    
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("username");
		return "redirect:/loginForm";
	}
    
    
    
// Inventory 
    @RequestMapping("/inventoryform")  
    public String showform(Model m,HttpSession session){  
    	
    	if(session.getAttribute("username") == null) {
    		return "redirect:/loginForm";
    	}
    	else {
    		m.addAttribute("command", new inventory());
        	return "inventoryform";
    	}
    	 
    }  
    
    @RequestMapping(value="/save",method = RequestMethod.POST)    
    public String save(@ModelAttribute("emp") inventory inty){    
    	customerdao.save(inty);    
        return "redirect:/viewInventory"; 
    }    
    
    
    @RequestMapping("/viewInventory")    
    public String viewInventory(Model m,HttpSession session){ 

    	if(session.getAttribute("username") == null) {
    		return "redirect:/loginForm";
    		
    		
    	}
    	else {
    		 List<inventory> list=customerdao.getInventory();    
    	        m.addAttribute("list",list);  
    	        return "viewInventory"; 
    		
    	}
          
    }    
   
    @RequestMapping(value="/editinventory/{id}")  
    public String edit(@PathVariable int id, Model m,HttpSession session){  
    	if(session.getAttribute("username") == null) {
    		
    		return "redirect:/loginForm";
    	}
    	else {
    		 inventory iny=customerdao.getProductById(id); 
    	        m.addAttribute("command",iny);
    	        return "inventoryeditform"; 
    	}
        
    }  
   
    @RequestMapping(value="/editsave",method = RequestMethod.POST)  
    public String editsave(@ModelAttribute("inventory") inventory iny,HttpSession session){  
    	if(session.getAttribute("username") == null) {
    		
    		return "redirect:/loginForm";
    	}
    	else {
    		customerdao.updateInventory(iny);  
            return "redirect:/viewInventory";  
    	}
    	
    }  
    
    @RequestMapping(value="/deleteinventory/{id}",method = RequestMethod.GET)  
    public String delete(@PathVariable int id,HttpSession session){ 
    	
    	if(session.getAttribute("username") == null) {
    		
    		return "redirect:/loginForm";
    	}
    	else {
    		customerdao.deleteInventory(id);  
            return "redirect:/viewInventory";  
    	}
    	
    }   
    
    
    
    
    // Category
    
    
    @RequestMapping("/categoryForm")  
    public String showformCayegory(Model m,HttpSession session){  
    	if(session.getAttribute("username") == null) {
    		
    		return "redirect:/loginForm";
    	}
    	else {
    		m.addAttribute("command", new Category());
        	return "categoryForm";
    	}
    	 
    }  
    
    @RequestMapping(value="/Categorysave",method = RequestMethod.POST)    
    public String save(@ModelAttribute("emp") Category caty,HttpSession session){    
    	if(session.getAttribute("username") == null) {
    		
    		return "redirect:/loginForm";
    	}
    	else {
    		customerdao.save(caty);    
            return "redirect:/viewCategory";
    	}
    	
    }    
  
    @RequestMapping("/viewCategory")    
    public String viewCategory(Model m,HttpSession session){ 
     if(session.getAttribute("username") == null) {
    		
    		return "redirect:/loginForm";
     }
     else {
    	 List<Category> list=customerdao.getCategory();    
         m.addAttribute("list",list);  
         return "viewCategory"; 
     }
          
    }    
 
    @RequestMapping(value="/editcat/{id}")  
    public String editcat(@PathVariable int id, Model m,HttpSession session){  
        if(session.getAttribute("username") == null) {
       		
       		return "redirect:/loginForm";
        }
        else {
        	 Category caty=customerdao.getCategoryById(id);  
             m.addAttribute("command",caty);
             return "editcat";  
        }
       
    }  
  
    @RequestMapping(value="/editsaveCategory",method = RequestMethod.POST)  
    public String editsaveCategory(@ModelAttribute("Category") Category caty,HttpSession session){  
        if(session.getAttribute("username") == null) {
       		
       		return "redirect:/loginForm";
        }
        else {
        	customerdao.updateCategory(caty);  
            return "redirect:/viewCategory";
        }
    	  
    }  

    @RequestMapping(value="/deletecategory/{id}",method = RequestMethod.GET)  
    public String deleteCategory(@PathVariable int id,HttpSession session){  
        if(session.getAttribute("username") == null) {
       		
       		return "redirect:/loginForm";
        }else {
        	customerdao.deleteCategory(id);  
            return "redirect:/viewCategory";  
        }
    	
    }   
  
}  