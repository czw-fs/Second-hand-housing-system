package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Admin;
import com.example.service.AdminService;
import com.example.service.RoleService;
import com.example.util.QiniuUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * @author: fs
 * @date: 2022/12/28 17:22
 * @Description: everything is ok
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{
    @Reference
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Reference
    private RoleService roleService;
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        Map<String,Object> filters = getFilters(request);
        map.put("filters" , filters);
        PageInfo<Admin> page = adminService.findPage(filters);
        map.put("page",page);
        return "admin/index";
    }
    //去添加用户页面
    @RequestMapping("/create")
    public String goAddPage(){
        return "admin/create";
    }

    //保存用户
    @RequestMapping("/save")
    public String save(Admin admin){
        //对admin中的密码进行加密
        String password = admin.getPassword();
        String encode = passwordEncoder.encode(password);
        admin.setPassword(encode);

        adminService.insert(admin);
        return "common/successPage";
    }

    //删除用户
    @RequestMapping("/delete/{adminId}")
    public String delete(@PathVariable("adminId")Long adminId){
        adminService.delete(adminId);
        return "redirect:/admin";
    }

    //去更新的页面
    @RequestMapping("/edit/{admin}")
    public String goEditPage(@PathVariable("admin")Long adminId,Map map){
        Admin admin = adminService.getById(adminId);
        map.put("admin",admin);
        return "admin/edit";
    }

    //更新页面
    @RequestMapping("/update")
    public String update(Admin admin){
        adminService.update(admin);
        return "common/successPage";
    }

    //去上传头像的页面
    @RequestMapping("/uploadShow/{id}")
    public String goUploadPage(@PathVariable("id")Long id,Map map){
        //将用户的id放到request域中
        map.put("adminId",id);
        return "admin/upload";
    }

    //上传头像
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable("id")Long id, MultipartFile file){

        try {
            byte[] bytes = file.getBytes();
            String newFileName = UUID.randomUUID().toString();
            QiniuUtils.upload2Qiniu(bytes,newFileName);

            Admin admin = adminService.getById(id);
            admin.setHeadUrl("http://rnshg0gyu.hn-bkt.clouddn.com/" + newFileName);

            adminService.update(admin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "common/successPage";
    }

    //去分配角色的页面
    @RequestMapping("/assignShow/{adminId}")
    public String goAssignShowPage(@PathVariable("adminId")Long adminId, ModelMap modelMap){
        modelMap.addAttribute("adminId",adminId);

        Map<String, Object> rolesByAdminId = roleService.findRolesByAdminId(adminId);
        modelMap.addAllAttributes(rolesByAdminId);
        return "admin/assignShow";
    }

    //分配角色
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){
        roleService.assignRole(adminId,roleIds);
        return "common/successPage";

    }
}
