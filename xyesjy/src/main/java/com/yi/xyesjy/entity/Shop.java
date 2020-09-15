package com.yi.xyesjy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author byh
 * @since 2020-08-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer gId;

    private Integer uId;

    private Integer state;

    private String creatTime;

    private String completeTime;

    @TableField("Shipping_address")
    private String shippingAddress;

    @TableField("Transaction_price")
    private String transactionPrice;


}
