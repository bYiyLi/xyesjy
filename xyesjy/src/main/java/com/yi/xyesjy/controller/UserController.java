package com.yi.xyesjy.controller;
import com.google.gson.Gson;
import com.yi.xyesjy.entity.User;
import com.yi.xyesjy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author byh
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService iUserService;
    @GetMapping("/getAll")
    @ResponseBody
    public String getAllUser(){
        System.out.println("getAllUser");
        return new Gson().toJson(iUserService.list());
    }
    @PostMapping("/SignIn")
    @ResponseBody
    public String SignIn( @RequestParam String user, @RequestParam String pwd){
        System.out.println(user);
        System.out.println(pwd);
        if (!user.isEmpty()){
            User u = iUserService.getById(user);
            System.out.println(new Gson().toJson(u));
            if (u!=null&&u.getPassword().equals(pwd)) {
                return new Gson().toJson(u);
            }
        }
        return "-1";
    }
    @PostMapping("/register")
    @ResponseBody
    public String Register( String user,String name,
                           String pwd,String school,
                            String college,String department,
                            String major,String telephone){


        System.out.println(user);
        System.out.println(name);
        System.out.println(pwd);
        System.out.println(school);
        System.out.println(college);
        System.out.println(department);
        System.out.println(major);
        System.out.println(telephone);

        User u = new User();
        try {
            u.setId(Integer.parseInt(user));
            u.setUsername(name);
            u.setPassword(pwd);
            u.setSchool(school);
            u.setCollege(college);
            u.setDepartment(department);
            u.setMajor(major);
            u.setTelephone(telephone);
            u.setCreatTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            u.setIssue(0);
            u.setPurchase(0);
            System.out.println(new Gson().toJson(u));
            iUserService.save(u);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "-1";
        }
        return new Gson().toJson(u);
    }

    @PostMapping("/update")
    @ResponseBody
    public String Update(   String id,String name,
                            String pwd,String school,
                            String college,String department,
                            String major,String telephone){
        System.out.println(id);
        System.out.println(name);
        System.out.println(pwd);
        System.out.println(school);
        System.out.println(college);
        System.out.println(department);
        System.out.println(major);
        System.out.println(telephone);
        try {
            User old = iUserService.getById(id);
            if (name!=null&&!name.isEmpty()){
                old.setUsername(name);
            }
            if (name!=null&&!name.isEmpty()){
                old.setUsername(name);
            }
            if (pwd!=null&&!pwd.isEmpty()){
                old.setPassword(pwd);
            }
            if (school!=null&&!school.isEmpty()){
                old.setSchool(school);
            }
            if (college!=null&&!college.isEmpty()){
                old.setCollege(college);
            }
            if (department!=null&&!department.isEmpty()){
                old.setDepartment(department);
            }
            if (major!=null&&!major.isEmpty()){
                old.setMajor(major);
            }
            if (telephone!=null&&!telephone.isEmpty()){
                old.setTelephone(telephone);
            }
            if (iUserService.updateById(old)) {
                return "OK";
            }else {
                return "-1";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "-1";
        }
    }
}
