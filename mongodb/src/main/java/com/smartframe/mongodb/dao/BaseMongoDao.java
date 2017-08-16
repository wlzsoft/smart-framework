package com.smartframe.mongodb.dao;

import java.lang.reflect.Field;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;
import com.smartframe.entity.Page;
import com.smartframe.mongodb.ReflectionUtils;

@Repository
public class BaseMongoDao {
	/** 
     * spring mongodb　集成操作类　 
     */  
    @Resource  
    protected  MongoTemplate mongoTemplate;  
  
    /** 
     * 注入mongodbTemplate 
     *  
     * @param mongoTemplate 
     */  
    protected  void setMongoTemplate(MongoTemplate mongoTemplate) {  
        this.mongoTemplate = mongoTemplate;  
    }  
    
	/** 
     * 插入 
     */
    public <T> T save(T entity) {  
        mongoTemplate.insert(entity);  
        return entity;  
    }  
  
    
    /** 
     * 根据ID查询 
     */ 
    public <T>T findById(String id) {  
        return mongoTemplate.findById(id, this.getEntityClass());  
    }  
  
    
    /** 
     * 通过ID获取记录,并且指定了集合名(表的意思) 
     */ 
    public <T>T findById(String id, String collectionName) {  
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);  
    }  
  
    
    /** 
     * 获得所有该类型记录 
     */ 
    public <T> List<T> findAll() {  
        return mongoTemplate.findAll(this.getEntityClass());  
    }  
  
    /** 
     * 获得所有该类型记录,并且指定了集合名(表的意思) 
     */ 
    public <T> List<T> findAll(String collectionName) {  
        return mongoTemplate.findAll(this.getEntityClass(), collectionName);  
    }  
  
    /** 
     * 根据条件查询 
     */ 
    public <T> List<T> find(Query query) {  
        return mongoTemplate.find(query, this.getEntityClass());  
    }  
  
    /** 
     * 根据条件查询一个 
     */ 
    public <T> T findOne(Query query) {  
        return mongoTemplate.findOne(query, this.getEntityClass());  
    }  
  
    /** 
     * 分页查询 
     */  
    public <T> Page<T> findPage(Page<T> page, Query query) {  
        //如果没有条件 则所有全部  
        query=query==null?new Query(Criteria.where("_id").exists(true)):query;  
        long count = this.count(query);  
        // 总数  
        page.setTotalCount((int) count);  
        int currentPage = page.getCurrentPage();  
        int pageSize = page.getPageSize();  
        query.skip((currentPage - 1) * pageSize).limit(pageSize);  
        List<T> rows = this.find(query);  
        page.build(rows);  
        return page;  
    }  
    
    /** 
     * 根据条件 获得总数 
     */  
    public long count(Query query) {  
        return mongoTemplate.count(query, this.getEntityClass());  
    }  
  
    
    /** 
     * 根据条件 更新 
     */ 
    public WriteResult update(Query query, Update update) {  
        if (update==null) {  
            return null;  
        }  
        return mongoTemplate.updateMulti(query, update, this.getEntityClass());  
    }  
  
    /** 
     * 更新符合条件并sort之后的第一个文档 并返回更新后的文档 
     */ 
    public <T> T updateOne(Query query, Update update) {  
        if (update==null) {  
            return null;  
        }  
        return mongoTemplate.findAndModify(query, update, this.getEntityClass());  
    }  
  
    /** 
     * 根据传入实体ID更新 
     */ 
    public <T> WriteResult update(T entity) {  
        Field[] fields = this.getEntityClass().getDeclaredFields();  
        if (fields == null || fields.length <= 0) {  
            return null;  
        }  
        Field idField = null;  
        // 查找ID的field  
        for (Field field : fields) {  
            if (field.getName() != null  
                    && "id".equals(field.getName().toLowerCase())) {  
                idField = field;  
                break;  
            }  
        }  
        if (idField == null) {  
            return null;  
        }  
        idField.setAccessible(true);  
        String id=null;  
        try {  
            id = (String) idField.get(entity);  
        } catch (IllegalArgumentException e) {  
            e.printStackTrace();  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }  
        if (id == null || "".equals(id.trim()))  
            return null;  
        // 根据ID更新  
        Query query = new Query(Criteria.where("_id").is(id));  
  
        Update update = ReflectionUtils.getUpdateObj(entity);  
        if (update == null) {  
            return null;  
        }  
        return mongoTemplate.updateFirst(query, update, getEntityClass());  
    }  
  
    /** 
     * 根据条件 删除 
     *  
     * @param query 
     */ 
    public void remove(Query query) {  
        mongoTemplate.remove(query, this.getEntityClass());  
    }  
    /** 
     * 获得泛型类 
     */  
    private <T> Class<T> getEntityClass() {  

        return ReflectionUtils.getSuperClassGenricType(getClass());  
    } 

}
