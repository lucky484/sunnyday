package com.softtek.mdm.bean;

/**
 * 系统词库model
 * @author jane.hui
 *
 */
public class WordsBean {

    /**
     * 键
     */
    private Integer id;
    
    /**
     * 值
     */
    private String name;
    
    /**
     * 网站分类
     */
    private Integer type;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}