package com.atren.server.controller;


import com.atren.server.pojo.Product;
import com.atren.server.pojo.RespBean;
import com.atren.server.pojo.RespPageBean;
import com.atren.server.service.IProductService;
import com.fasterxml.jackson.core.JsonParser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;

    @ApiOperation(value = "获取所有商品(分页)为管理员提供")
    @GetMapping("/")
    public RespBean getAllProductsForManager(@RequestParam(defaultValue = "1") Integer currentPage,
                                           @RequestParam(defaultValue = "10") Integer pageSize
                                            ){
        RespPageBean productByPage = productService.getProductByPage(currentPage, pageSize);
        return RespBean.success("获取成功！",productByPage);
    }

    @ApiOperation(value = "添加商品")
    @PostMapping("/")
    public RespBean addProduct(@RequestBody Product product){
        if (productService.save(product)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }

    @ApiOperation(value = "修改商品")
    @PutMapping("/")
    public RespBean updateProduct(@RequestBody Product product){
        if (productService.updateById(product)){
            return RespBean.success("修改成功！");
        }
        return RespBean.error("修改失败！");
    }

    @ApiOperation(value = "删除商品")
    @DeleteMapping("/{id}")
    public RespBean deleteProduct(@PathVariable Integer id){
        if (productService.removeById(id)){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "通过商品Id获取商品的详细信息")
    @GetMapping("/getDetails")
    public RespBean getDetailsById(@RequestParam String productID){
        Product product = productService.getById(productID);
        if (product != null){
            return RespBean.success("获取成功！",product);
        }
        return RespBean.error("获取失败！");
    }

    @ApiOperation(value = "获取所有商品(分页)为store提供")
    @GetMapping("/getAllProductForStore")
    public RespBean getAllProductsForStore(@RequestParam Integer currentPage,
                                           @RequestParam Integer pageSize
                                            ){
        RespPageBean productByPage = productService.getProductByPage(currentPage, pageSize);
        return RespBean.success("获取成功！",productByPage);
    }

    @ApiOperation(value = "根据搜索条件，分页获取商品信息")
    @GetMapping("/getProductBySearch")
    public RespBean getProductBySearch(@RequestParam Integer currentPage,
                                       @RequestParam Integer pageSize,
                                       @RequestParam String search){
        RespPageBean productList = productService.getProductBySearch(search,currentPage,pageSize);
        if (productList.getTotal()>=0){
            return RespBean.success("获取成功！",productList);
        }
        return RespBean.error("获取失败！");
    }

    @ApiOperation(value = "根据分类id,分页获取商品信息")
    @GetMapping("/getProductByCategory")
    public RespBean getProductByCategory(@RequestParam Integer currentPage,
                                         @RequestParam Integer pageSize,
                                         @RequestParam Integer categoryId){
        RespPageBean productList = productService.getProductByCategory(categoryId,currentPage,pageSize);
        if (productList.getTotal()>=1){
            return RespBean.success("获取成功！",productList);
        }
        return RespBean.error("获取失败！");
    }

    @ApiOperation(value = "文件上传")
    @ApiImplicitParams({@ApiImplicitParam(name = "file",
            value = "文件对象",
            dataType = "MultipartFile",
            required = true)})
    @PostMapping("/fileUpload")
    public RespBean fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("开始上传");
        //图片保存文件夹
        String path = "E:\\Project\\graduation\\coffee_back\\coffee_server\\src\\main\\resources\\static\\images\\product\\";
        if (file.isEmpty()){
            return RespBean.error("上传失败！");
        }

        // 获取附件原名(有的浏览器如chrome获取到的是最基本的含 后缀的文件名,如myImage.png)
        // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如C:\\Users\\images\\myImage.png)
        String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = fileName.lastIndexOf("\\");
        if (index > 0) {
            fileName = fileName.substring(index + 1);
        }
        // 判断单个文件大于1M
        long fileSize = file.getSize();
        if (fileSize > 1024 * 1024) {
            System.out.println("文件大小为(单位字节):" + fileSize);
            System.out.println("该文件大于1M");
        }
        // 当文件有后缀名时
        if (fileName.indexOf(".") >= 0) {
            fileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
        }
        // 当文件无后缀名时(如C盘下的hosts文件就没有后缀名)
        if (fileName.indexOf(".") < 0) {
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileName + (int) (Math.random() * 100000);
        }
        System.out.println("fileName:" + fileName);

        // 根据文件的全路径名字(含路径、后缀),new一个File对象dest
        File dest = new File(path + fileName);
        // 如果该文件的上级文件夹不存在，则创建该文件的上级文件夹及其祖辈级文件夹;
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("文件的全路径名字(含路径、后缀)>>>>>>>" + path + fileName);

        String savePath = "/dev-api/images/product/" + fileName;
        return RespBean.success("上传成功",savePath);

    }


    @ApiOperation(value = "根据商品分类名称获取首页展示的商品信息")
    @GetMapping("/getPromoInfo")
    public RespBean getProductInfoByCategoryName(@RequestParam String categoryName){
        List<Product> productList =  productService.getProductInfoByCategoryNameAndPage(categoryName,0,7);
        if (!productList.isEmpty()){
            return RespBean.success("获取成功！",productList);
        }

        return RespBean.error("获取失败！");
    }

    /**
     *
     * 这个代码不好，我没有想到接收处理前端传来的数组
     * @param categoryName1
     * @param categoryName2
     * @return
     */
    @ApiOperation(value = "获取热门咖啡用具商品的信息")
    @GetMapping("/getHotInfo/{categoryName1}/{categoryName2}")
    public RespBean getHotProductInfoByCategoryName(@PathVariable("categoryName1")String categoryName1,
                                                    @PathVariable("categoryName2")String categoryName2){
        List<Product> productList1 =  productService.getProductInfoByCategoryNameAndPage(categoryName1,0,4);
        List<Product> productList2 =  productService.getProductInfoByCategoryNameAndPage(categoryName2,0,3);
        List<Product> productList = new ArrayList<>();
        productList.addAll(productList1);
        productList.addAll(productList2);
        if (!productList.isEmpty()){
            return RespBean.success("获取成功！",productList);
        }

        return RespBean.error("获取失败！");
    }

    @ApiOperation(value = "批量删除商品")
    @DeleteMapping("/deleteByIds")
    public RespBean deleteByIds(@RequestParam String products){
        System.out.println(products);
        return RespBean.error("删除失败！");
    }
}
