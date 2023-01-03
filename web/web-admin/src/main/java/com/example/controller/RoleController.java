package com.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.example.entify.Role;
import com.example.service.PermissionService;
import com.example.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: fs
 * @date: 2022/12/27 15:42
 * @Description: everything is ok
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    private static final String SUCCESS_PAGE="common/successPage";

    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

/*    @RequestMapping
    public String index(Map map){
        List<Role> roleList = roleService.findAll();
        map.put("list",roleList);

        return "role/index";
    }*/

    //分页及条件查询的方法
    @PreAuthorize("hasAuthority('role.show')")
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        //获取请求参数
        Map<String,Object> filters = getFilters(request);

        PageInfo<Role> pageInfo = roleService.findPage(filters);

        //将filters放到request域中
        map.put("filters", filters);
        //将PageInfo放到request域中
        map.put("page", pageInfo);
        return "role/index";
    }



    @RequestMapping("/create")
    public String goAddPage(){
        return "role/create";
    }

    //添加角色
    @RequestMapping("/save")
    public String save(Role role){
        //调用roleService中的添加方法
        roleService.insert(role);
        //return "redirect:/role";
        return SUCCESS_PAGE;
    }

    //删除角色
    @PreAuthorize("hasAuthority('role.delete')")//此时只有Delete(使用该方法的角色在重权限表中查出来的字段要和单引号中的内容一模一样才能执行该方法)权限的人才能删
    @RequestMapping("/delete/{roleId}")
    public String delete(@PathVariable("roleId")Long roleId){
        roleService.delete(roleId);
        //重定向到查询所以有角色的方法
        return "redirect:/role";
    }

    //去修改页面的方法
    @RequestMapping("/edit/{roleId}")
    public String goEditPage(@PathVariable("roleId")Long roleId,Map map){
        Role role = roleService.getById(roleId);

        //将查询到的角色放到request域中
        map.put("role",role);
        //去修改页面
        return "role/edit";
    }

    //更新角色
    @RequestMapping("/update")
    public String update(Role role){
        roleService.update(role);
        return SUCCESS_PAGE;
    }

    //去分配权限的页面
    @RequestMapping("/assignShow/{roleId}")
    public String assignShow(@PathVariable("roleId")Long roleId,Map map){
        map.put("roleId",roleId);

        List<Map<String,Object>> zNodes = permissionService.findPermissionsByRoleId(roleId);
        map.put("zNodes",zNodes);
        return "role/assignShow";
    }

    //分配权限
    @RequestMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId")Long roleId,@RequestParam("permissionIds")Long[] permissionIds){
        permissionService.assignPermission(roleId,permissionIds);
        return "common/successPage";
    }

}
