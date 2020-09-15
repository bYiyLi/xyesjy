package com.yi.xyesjy.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yi.xyesjy.entity.Goods;
import com.yi.xyesjy.entity.MyShopIng;
import com.yi.xyesjy.entity.Shop;
import com.yi.xyesjy.entity.User;
import com.yi.xyesjy.service.IGoodsService;
import com.yi.xyesjy.service.IShopService;
import com.yi.xyesjy.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author byh
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    IShopService iShopService;
    @Autowired
    IUserService iUserService;
    @Autowired
    IGoodsService iGoodsService;
    @PostMapping("/buyGoods")
    public String buyGoods(String gId,String uId){
        if (gId==null||gId.isEmpty()){
            return "-1";
        }
        if (uId==null||uId.isEmpty()){
            return "-1";
        }
        Shop shop = new Shop();
        shop.setGId(Integer.parseInt(gId));
        shop.setUId(Integer.parseInt(uId));
        shop.setCreatTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Goods goods = iGoodsService.getById(gId);
        if (goods==null){
            return "-1";
        }
        goods.setState(2);
        if (!iGoodsService.updateById(goods)){
            return "-1";
        }
        shop.setState(2);
        shop.setTransactionPrice(goods.getPrice());
        User user = iUserService.getById(uId);
        if (user==null){
            goods.setState(1);
            if (!iGoodsService.updateById(goods)){
                return "-1";
            }
            return "-1";
        }
        shop.setShippingAddress(user.getSchool()+user.getCollege()+user.getDepartment()+user.getMajor());
        if (iShopService.save(shop)) {
            return "OK";
        }
        return "-1";
    }
    @PostMapping("/delShoping")
    public String delShoping(String delId){
        System.out.println("ShopDel:"+delId);
        Goods goods = iGoodsService.getById(delId);
        if (goods==null){
            return "-1";
        }
        goods.setState(1);
        iGoodsService.updateById(goods);
        QueryWrapper<Shop> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("g_id",delId);
        if (iShopService.remove(queryWrapper)) {
            return "OK";
        }
        return "-1";
    }
    @GetMapping("/getMyShoping")
    public String getMyShoping(String uId,String page,String state){
        QueryWrapper<Shop> qucount=new QueryWrapper<>();
        System.out.println(state);
        qucount.eq("state",Integer.parseInt(state));
        qucount.eq("u_id",uId);
        int n=iShopService.count(qucount)/3;
        IPage<Shop> userPage;
        if (page == null || page.isEmpty()){
            userPage= new Page<>(0, 3);//参数一是当前页，参数二是每页个数
        }else if (Integer.parseInt(page)<0){
            userPage = new Page<>(0, 3);//参数一是当前页，参数二是每页个数
        }else if (n<Integer.parseInt(page)){
            userPage = new Page<>(n, 3);//参数一是当前页，参数二是每页个数
        }else {
            userPage = new Page<>(Integer.parseInt(page), 3);//参数一是当前页，参数二是每页个数
        }
        QueryWrapper<Shop> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("creat_time");
        queryWrapper.eq("u_id",uId);
        queryWrapper.eq("state",Integer.parseInt(state));
        IPage<Shop> pageG =  iShopService.getBaseMapper().selectPage(userPage, queryWrapper);
        List<MyShopIng> myShopIngs=new ArrayList<>();
        pageG.getRecords().forEach(i->{
            MyShopIng myShopIng = new MyShopIng();
            Goods goods = iGoodsService.getById(i.getGId());
            myShopIng.setGoods(goods);
            User b = iUserService.getById(i.getUId());
            myShopIng.setB(b);
            User a = iUserService.getById(goods.getUId());
            myShopIng.setA(a);
            myShopIngs.add(myShopIng);
        });
        System.out.println(pageG.getSize());
        String s = new Gson().toJson(myShopIngs).toString();
        System.out.println(s);
        return s;
    }
    @GetMapping("/getUserShopedGoodsList")
    @ResponseBody
    public String getUserShopedGoodsList(String page,String uId,String state){
        QueryWrapper<Goods> qucount=new QueryWrapper<>();
        System.out.println(state);
        qucount.eq("state",Integer.parseInt(state));
        qucount.eq("u_id",uId);
        int n=iGoodsService.count(qucount)/2;
        IPage<Goods> userPage;
        if (page == null || page.isEmpty()){
            userPage= new Page<>(0, 2);//参数一是当前页，参数二是每页个数
        }else if (Integer.parseInt(page)<0){
            userPage = new Page<>(0, 2);//参数一是当前页，参数二是每页个数
        }else if (n<Integer.parseInt(page)){
            userPage = new Page<>(n, 2);//参数一是当前页，参数二是每页个数
        }else {
            userPage = new Page<>(Integer.parseInt(page), 2);//参数一是当前页，参数二是每页个数
        }
        QueryWrapper<Goods> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("creat_time");
        queryWrapper.eq("u_id",uId);
        queryWrapper.eq("state",Integer.parseInt(state));
        IPage<Goods> pageG =  iGoodsService.getBaseMapper().selectPage(userPage, queryWrapper);

        List<MyShopIng> myShopIngs=new ArrayList<>();
        pageG.getRecords().forEach(i->{
            MyShopIng myShopIng = new MyShopIng();
            myShopIng.setGoods(i);
            User b = iUserService.getById(i.getUId());
            myShopIng.setB(b);
            User a = iUserService.getById(i.getUId());
            myShopIng.setA(a);
            myShopIngs.add(myShopIng);
        });
        System.out.println(pageG.getSize());
        String s = new Gson().toJson(myShopIngs).toString();
        System.out.println("============="+s);
        return s;
    }
    
    @GetMapping("/getUserBuyedGoodsList")
    @ResponseBody
    public String getUserBuyedGoodsList(String page,String uId,String state){
        QueryWrapper<Shop> qucount=new QueryWrapper<>();
        System.out.println("a");
        qucount.eq("state",Integer.parseInt(state));
        qucount.eq("u_id",uId);
        int n=iShopService.count(qucount)/3;
        IPage<Shop> shopPage;
        if (page == null || page.isEmpty()){
            shopPage= new Page<>(0, 3);//参数一是当前页，参数二是每页个数
        }else if (Integer.parseInt(page)<0){
            shopPage = new Page<>(0, 3);//参数一是当前页，参数二是每页个数
        }else if (n<Integer.parseInt(page)){
            shopPage = new Page<>(n, 3);//参数一是当前页，参数二是每页个数
        }else {
            shopPage = new Page<>(Integer.parseInt(page), 3);//参数一是当前页，参数二是每页个数
        }
        QueryWrapper<Shop> queryWrapper =  new QueryWrapper<>();
        queryWrapper.orderByDesc("creat_time");
        queryWrapper.eq("u_id",uId);
        queryWrapper.eq("state",Integer.parseInt(state));
        IPage<Shop> pageG =  iShopService.getBaseMapper().selectPage(shopPage, queryWrapper);

        List<MyShopIng> myShopIngs=new ArrayList<>();
        pageG.getRecords().forEach(i->{
            MyShopIng myShopIng = new MyShopIng();
            Goods g = iGoodsService.getById(i.getGId());
            myShopIng.setGoods(g);
            User b = iUserService.getById(i.getUId());
            myShopIng.setB(b);
            User a = iUserService.getById(i.getUId());
            myShopIng.setA(a);
            myShopIngs.add(myShopIng);
        });
        System.out.println(pageG.getSize());
        String s = new Gson().toJson(myShopIngs).toString();
        System.out.println("------------"+s);
        return s;
    }

    @PostMapping("/soldOK")
    @ResponseBody
    public String SoldOK(String id){
        QueryWrapper<Shop> qucount=new QueryWrapper<>();
        qucount.eq("g_id",id);
        Shop shop= iShopService.getOne(qucount);
        shop.setState(4);
        Goods goods = iGoodsService.getById(id);
        goods.setState(4);
        if (iShopService.update(shop,qucount)) {
            if (iGoodsService.updateById(goods)){
                System.out.println("SoldOK");
                return "成功确认售出";
            }
        }
        return "-1";
    }
}
