package com.hh.sell.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author hh
 * 商品实体类
 *
 * 商品实体类和商品类目类通过商品类目编号来关联
 */

@Data
@Entity
@Proxy(lazy = false)
@NoArgsConstructor
public class ProductInfo {

    @Id
    private String productId;

    //商品名称
    private String productName;

    //商品价格
    private BigDecimal productPrice;

    //商品库存
    private Integer productStock;

    //商品描述
    private String productDescription;

    //商品小图
    private String productIcon;

    //商品状态,0正常,1下架
    private Integer productStatus;

    //商品类目编号
    private Integer categoryType;
}
