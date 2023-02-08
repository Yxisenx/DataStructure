package cn.onecolour.entity;

import lombok.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author yang
 * @date 2023/2/8
 * @description
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Org implements Serializable {
    /**
     * id
     */
    private Long id;
    /**
     * 组织机构名称
     */
    private String name;
    /**
     * 组织机构code
     */
    private String code;
    /**
     * 父级id
     */
    private Long parentId;
    /**
     * 组织机构对应路径
     */
    private String path;
    /**
     * 组织机构类型
     */
    private String organizationType;
    /**
     * 组织机构类型名称
     */
    private String organizationTypeText;
    /**
     * 是否只读
     */
    private Integer readOnly;
    /**
     * 负责人
     */
    private String manager;
    private Long managerId;
    /**
     * 在当前节点及以下且拥有对应岗位
     */
    private Boolean managerInCurrentSubs = Boolean.TRUE;
    /**
     * 具有管辖当前及所有子节点权限
     */
    private Boolean administerAndSubs = Boolean.TRUE;

    /**
     * true: 负责人被修改过
     */
    private Boolean managerModified = Boolean.FALSE;

    /**
     * 回款入账地
     */
    private String placeOfPayment;
    /**
     * 分公司
     */
    private Long branchOfficeId;
    /**
     * 分公司名称
     */
    private String branchOfficeName;
    /**
     * 主营业务CODE
     */
    private String mainBusiness;
    /**
     * 主营业务Text
     */
    private String mainBusinessText;
    private String major;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 最后一次修改时间
     */
    private Date gmtModified;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 详细地址编码
     */
    private String addressCode;
    /**
     * 分公司编码
     */
    private String branchOfficeCode;

    /**
     * 当前组织人数
     */
    private Integer userNum;
    /**
     * 同一父节点下的排序.
     */
    private Integer orderId;
    /**
     * version
     */
    private Integer version;

    /**
     * 子节点, 请求树型结构时返回此项
     */
    private List<Org> children = Collections.emptyList();
}
