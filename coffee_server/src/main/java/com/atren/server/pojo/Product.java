package com.atren.server.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ren
 * @since 2022-02-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_product")
@ApiModel(value="Product对象", description="")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    @TableField("product_name")
    private String productName;

    @TableField("category_id")
    private Integer categoryId;

    @TableField("product_title")
    private String productTitle;

    @TableField("product_intro")
    private String productIntro;

    @TableField("product_picture")
    private String productPicture;

    @TableField("product_price")
    private Double productPrice;

    @TableField("product_num")
    private Integer productNum;

    @TableField("product_sales")
    private Integer productSales;

    @TableField(exist = false)
    private Category category;

    //用于接收购物车传来的商品数量
    @TableField(exist = false)
    private Integer num;

    //用于接收购物车传来的商品价格
    @TableField(exist = false)
    private double price;

}
