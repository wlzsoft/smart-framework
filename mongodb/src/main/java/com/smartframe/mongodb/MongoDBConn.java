package com.smartframe.mongodb;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.smartframe.basics.util.PropertieUtils;


public class MongoDBConn {
	private static final Logger logger = LoggerFactory.getLogger(MongoDBConn.class);
	private static MongoClient  mongoClient =null;
	private static MongoDatabase  mongoDatabase = null;
	public static String dbName;
	public static String host;
	public static String url;
	
	private static PropertieUtils config = new PropertieUtils("properties/mongo.properties");
	
	static{
		   dbName = config.getProperty("mongodb.dbName");
		   host = config.getProperty("mongodb.host");
		   url = config.getProperty("mongodb.serverlist");
	}
	
	public final static MongoDatabase getMongoConn(){
		//取得数据库对象
		 MongoClientOptions.Builder build = new MongoClientOptions.Builder();
		 MongoClientOptions myOptions = build.build();
			try {
				// 数据库连接实例
				if(host!=null){
					mongoClient=new MongoClient(host, myOptions);
				}else{
					MongoClientURI connectionString = new MongoClientURI(url,build);
					mongoClient = new MongoClient(connectionString);
				}
				mongoDatabase = mongoClient.getDatabase(dbName);
				return mongoDatabase;
			} catch (MongoException e) {
				logger.error(e.getMessage());
			}
			return null;
	}
	
	public static MongoCollection<Document> getMongoColletion(String colletionName){
		MongoCollection<Document> collection =null;
		if(mongoDatabase!=null){
			 collection =  mongoDatabase.getCollection(colletionName);
		}else{
			collection = getMongoConn().getCollection(colletionName);
		}
		
		return collection ;
		
	}
	
	public static GridFSBucket getMongoGridFS(String gridFSName){
		GridFSBucket  gridFSBucket = null;
		if(mongoDatabase!=null){
			//创建数据库对象中GridFS集合
			  gridFSBucket= GridFSBuckets.create(mongoDatabase,gridFSName);
		}else{
			gridFSBucket = GridFSBuckets.create(getMongoConn(),gridFSName);
		}
		return gridFSBucket ;
		
	}
	
}
