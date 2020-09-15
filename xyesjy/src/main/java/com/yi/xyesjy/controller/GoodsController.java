package com.yi.xyesjy.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yi.xyesjy.entity.Goods;
import com.yi.xyesjy.mapper.GoodsMapper;
import com.yi.xyesjy.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author byh
 * @since 2020-08-23
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    IGoodsService iGoodsService;

    @GetMapping("/getAllGoodsType")
    public String getAllGoodsType(){
        BaseMapper<Goods> baseMapper = iGoodsService.getBaseMapper();
        if (baseMapper instanceof GoodsMapper) {
            List<String> allType = ((GoodsMapper) baseMapper).getAllType();
            System.out.println(new Gson().toJson(allType));
            return new Gson().toJson(allType);
        }
        return "-1";
    }

    @PostMapping("/delGoods")
    @ResponseBody
    public String delGoods(String id){
        System.out.println("Del-------------------------------"+id);
        boolean b = iGoodsService.removeById(id);
        if (b){
            return "OK";
        }else {
            return "-1";
        }
    }

    @GetMapping("/getGoodsList")
    @ResponseBody
    public String getGoodsList(String type){
        HashMap<String,Object> par = new HashMap<>();
        par.put("state",1);
        if (type!=null&&!type.isEmpty()){
            par.put("type",type);
        }
        Collection<Goods> g = iGoodsService.listByMap(par);
        System.out.println(new Gson().toJson(g));
        return new Gson().toJson(g).toString();
    }

    @GetMapping("/getUserCreatGoodsList")
    @ResponseBody
    public String getUserCreatGoodsList(String page,String uId,String state){
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
        System.out.println(pageG.getSize());
        String s = new Gson().toJson(pageG.getRecords()).toString();
        System.out.println(s);
        return s;
    }


    @PostMapping("/addGoods")
    @ResponseBody
    public String addGoods(String type, String synopsis,
                           String price, String uId,
                           String name, MultipartFile image5,
                           MultipartFile  image4, MultipartFile  image3,
                           MultipartFile  image2, MultipartFile  image1
                           ){
        System.out.println("addGoods");
        System.out.println(type);
        System.out.println(synopsis);
        System.out.println(price);
        System.out.println(uId);
        System.out.println(name);
        System.out.println(image1);
        System.out.println(image2);
        System.out.println(image3);
        System.out.println(image4);
        System.out.println(image5);
        try {
            Goods goods = new Goods();
            goods.setCreatTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            goods.setId(Integer.parseInt(uId+new Random().nextInt(10000000)));
            goods.setName(name);
            goods.setPrice(price);
            goods.setState(1);
            goods.setType(type);
            goods.setSynopsis(synopsis);
            goods.setUId(Integer.parseInt(uId));
            if (image1!=null){
                goods.setImage1(saveImage(image1));
            }
            if (image2!=null){
                goods.setImage2(saveImage(image2));
            }
            if (image3!=null){
                goods.setImage3(saveImage(image3));
            }
            if (image4!=null){
                goods.setImage4(saveImage(image4));
            }
            if (image5!=null){
                goods.setImage5(saveImage(image5));
            }
            if (iGoodsService.save(goods)) {
                return "200";
            }else {
                return "-1";
            }
        }catch (Exception e){
            return "-1";
        }
    }
    @PostMapping("/updateGoods")
    @ResponseBody
    public String updateGoods(String type, String synopsis,
                           String price, String id,
                           String name, MultipartFile image5,
                           MultipartFile  image4, MultipartFile  image3,
                           MultipartFile  image2, MultipartFile  image1
    ){
        System.out.println("updateGoods");
        System.out.println(type);
        System.out.println(synopsis);
        System.out.println(price);
        System.out.println(id);
        System.out.println(name);
        System.out.println(image1);
        System.out.println(image2);
        System.out.println(image3);
        System.out.println(image4);
        System.out.println(image5);
        try {
            Goods goods = new Goods();
            goods.setId(Integer.parseInt(id));
            goods.setName(name);
            goods.setPrice(price);
            goods.setState(1);
            goods.setType(type);
            goods.setSynopsis(synopsis);
            if (image1!=null){
                goods.setImage1(saveImage(image1));
            }
            if (image2!=null){
                goods.setImage2(saveImage(image2));
            }
            if (image3!=null){
                goods.setImage3(saveImage(image3));
            }
            if (image4!=null){
                goods.setImage4(saveImage(image4));
            }
            if (image5!=null){
                goods.setImage5(saveImage(image5));
            }
            if (iGoodsService.updateById(goods)) {
                System.out.println("OK");
                return "200";
            }else {
                System.out.println("No");
                return "-1";
            }
        }catch (Exception e){
            return "-1";
        }
    }
    private String saveImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String name = UUID.randomUUID()+originalFilename;
        String path = ClassUtils.getDefaultClassLoader().getResource("static/images").getPath();
        File wf=new File(path+ File.separatorChar+name);
        OutputStream outputStream=new FileOutputStream(wf);
        outputStream.write(file.getBytes());
        return "images/"+name;
    }
}
