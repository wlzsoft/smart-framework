package com.smartframe.mongodb.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@Repository
public class MongoFileDao {

	@Resource
	protected GridFsTemplate gridFsTemplate;

	/**
	 * 注入mongodbTemplate
	 * 
	 * @param mongoTemplate
	 */
	protected void setMongoTemplate(GridFsTemplate gridFsTemplate) {
		this.gridFsTemplate = gridFsTemplate;
	}
	
	

	 public String store(InputStream inputStream, String fileName,  String contentType, DBObject metaData) {
	  return this.gridFsTemplate
	    .store(inputStream, fileName, contentType, metaData).getId()
	    .toString();
	 }

	/**
	 * 根据ID查询
	 */
	public GridFSDBFile getById(String id) {
		return this.gridFsTemplate.findOne(new Query(Criteria.where("_id").is(
				id)));
	}

	public GridFSDBFile getByFilename(String fileName) {
		return gridFsTemplate.findOne(new Query(Criteria.where("filename").is(
				fileName)));
	}

	public GridFSDBFile retrive(String fileName) {
		return gridFsTemplate.findOne(new Query(Criteria.where("filename").is(
				fileName)));
	}


	public List<GridFSDBFile> findAll() {
		return gridFsTemplate.find(null);
	}
	
	
	
	 public static void main(String[] args) {
		  InputStream inputStream = null;
		  try {
		   ApplicationContext context = new ClassPathXmlApplicationContext("properties/spring-.xml","");
		   MongoFileDao fileStorageDao = (MongoFileDao) context.getBean("mongoFileDao");
		   
		   InputStream streamToUploadFrom = new FileInputStream(new File("E:/my.png"));

		   DBObject metaData = new BasicDBObject();
		   metaData.put("brand", "Audi");
		   metaData.put("model", "Audi A3");
		   metaData.put("description","Audi german automobile manufacturer that designs, engineers, and distributes automobiles");

		   String id = fileStorageDao.store(streamToUploadFrom,"audi.jpg", "image/jpeg", metaData);

		   System.out.println("Find By Id----------------------");
		   GridFSDBFile byId = fileStorageDao.getById(id);
		   System.out.println("File Name:- " + byId.getFilename());
		   System.out.println("Content Type:- " + byId.getContentType());
		   
		   
		   
		   System.out.println("Find By Filename----------------------");
		   GridFSDBFile byFileName = fileStorageDao.getByFilename("audi.jpg");
		   System.out.println("File Name:- " + byFileName.getFilename());
		   System.out.println("Content Type:- " + byFileName.getContentType());
		   
		   
		   System.out.println("List All Files----------------------");
		   for (GridFSDBFile file : fileStorageDao.findAll()) {
		    System.out.println("File Name:- " + file.getFilename());
		    System.out.println("Content Type:- " + file.getContentType());
		    System.out.println("Meta Data Brand:- " + file.getMetaData().get("brand"));
		    System.out.println("Meta Data Model:- " + file.getMetaData().get("model"));
		    System.out.println("Meta Data Description:- " + file.getMetaData().get("description"));
		   }
		   
		   GridFSDBFile retrive = fileStorageDao.retrive("audi.jpg");
		   retrive.writeTo("c:\\newaudi.jpg");
		   
		  } catch (BeansException e) {
		   System.out.println("BeansException:-" + e.getMessage());
		  } catch (IOException e) {
		   System.out.println("IOException:-" + e.getMessage());
		  } finally {
		   if (inputStream != null) {
		    try {
		     inputStream.close();
		    } catch (IOException e) {
		     System.out.println("IOException Finally:-" + e.getMessage());
		    }
		   }
		  }

		 }
	
	
	
	
	
	
	
	
	
	
	

}
