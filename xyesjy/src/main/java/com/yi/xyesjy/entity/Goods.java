package com.yi.xyesjy.entity;

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
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer uId;

    private Integer state;

    private String name;

    private String creatTime;

    private String price;

    private String synopsis;

    private String type;

    private String image1;

    private String image2;

    private String image3;

    private String image4;

    private String image5;


}
