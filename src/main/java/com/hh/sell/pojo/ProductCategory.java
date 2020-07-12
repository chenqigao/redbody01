package com.hh.sell.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


//如果我们更新某个字段时,想更新所有的字段,就可以加上@DynamicUpdate.
@DynamicUpdate
//设置懒加载为false，否则会报错
@Proxy(lazy = false)
//表明该实体类是与数据库中的表映射的类
@Entity
//getter,setter,toString()方法
@Data
//无参构造函数
@NoArgsConstructor
public class ProductCategory {

    //主键
    @Id
    //主键自增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    //类目名字
    private String categoryName;

    //类目编号
    private Integer categoryType;

    public ProductCategory(String categoryName,Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
