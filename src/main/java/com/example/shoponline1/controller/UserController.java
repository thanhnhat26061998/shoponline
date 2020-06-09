package com.example.shoponline1.controller;

import com.example.shoponline1.dao.IRoleDao;
import com.example.shoponline1.dao.IUserDao;
import com.example.shoponline1.dto.EditProfile;
import com.example.shoponline1.dto.OrderDetailInfo;
import com.example.shoponline1.dto.OrderInfo;
import com.example.shoponline1.dto.Register;
import com.example.shoponline1.entity.OrderDetail;
import com.example.shoponline1.entity.Role;
import com.example.shoponline1.entity.User;
import com.example.shoponline1.service.OrderServiceImpl;
import com.example.shoponline1.validation.EditProfileValidation;
import com.example.shoponline1.validation.RegisterValidation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IRoleDao roleDao;

    @Autowired
    RegisterValidation registerValidation;

    @Autowired
    EditProfileValidation editProfileValidation;

    @Autowired
    OrderServiceImpl orderServiceImpl;

    @Autowired
    JavaMailSender emailSender;

    @RequestMapping("/login")
    public String login() {
        return "/business/user/login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        if (session.getAttribute("user") != null) {
            session.removeAttribute("user");
        }
        return "redirect:/home";
    }

    @RequestMapping("/register")
    public String register(Model model) {
        Register register = new Register();
        model.addAttribute("register", register);

        return "/business/user/register";
    }

    @RequestMapping("/registers")
    public String registers(@ModelAttribute("register") Register register,
            Model model, BindingResult bindingResult) {
        registerValidation.validate(register, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/business/user/register";
        }
        User user = new User();
        user.setEmail(register.getEmail());
        user.setName(register.getName());
        user.setAddress(register.getAddress());
        user.setPhone(register.getPhone());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleDao.findByName("ROLE_MEMBER"));
        user.setRoles(roles);
        userDao.save(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(register.getEmail());
        message.setText("Đăng ký thành công!");
        this.emailSender.send(message);

        return "redirect:/login";
    }

    @RequestMapping("/editprofile")
    public String editProfile(Model model, HttpSession session) {
        EditProfile register = new EditProfile();
        User user = (User) session.getAttribute("user");
        register.setId(user.getId());
        register.setEmail(user.getEmail());
        register.setName(user.getName());
        register.setAddress(user.getAddress());
        register.setPhone(user.getPhone());
        register.setPassword(user.getPassword());
        register.setCfpassword(user.getPassword());

        model.addAttribute("register", register);
        session.removeAttribute("user");

        return "/business/user/editProfile";
    }

    @RequestMapping("/editprofiles")
    public String editprofiles(@ModelAttribute("register") EditProfile register,
            Model model, BindingResult bindingResult, HttpSession session) {
        editProfileValidation.validate(register, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/business/user/editProfile";
        }
        User user = new User();
        user.setId(register.getId());
        user.setEmail(register.getEmail());
        user.setName(register.getName());
        user.setAddress(register.getAddress());
        user.setPhone(register.getPhone());
        user.setPassword(passwordEncoder.encode(register.getPassword()));
        HashSet<Role> roles = new HashSet<>();
        roles.add(roleDao.findByName("ROLE_MEMBER"));
        user.setRoles(roles);
        userDao.save(user);

        session.setAttribute("user", user);

        return "redirect:/home";
    }

 /*   @RequestMapping("/seehistory")
    public String seeHistory(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        
        
        //OrderInfo orderInfo = orderServiceImpl.getOrderInfo(user.getId());
        List<OrderInfo> orderInfoList = orderServiceImpl.getAllOrderInfo(user.getId());
        for (OrderInfo orderInfo : orderInfoList) {
            System.out.println("Info: " + orderInfo.getId() + orderInfo.getOrderDate() + orderInfo.getOrderDetailInfos());
        }
        
        
        
        //orderInfo.setOrderDetailInfos(detailInfos);
        model.addAttribute("orderDetail", orderInfoList);
        //model.addAttribute("orderDetail", orderInfo);

        return "business/user/history";
    }*/
    @RequestMapping("/403")
	public String erros() {
		return "/system/403";
	}

}
