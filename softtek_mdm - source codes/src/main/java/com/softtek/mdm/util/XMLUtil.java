package com.softtek.mdm.util;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.util.NonLazyElement;

import com.softtek.mdm.status.DirectionStatus;

/**
 * 处理XML包括结点的工具类
 * 
 * @author color.wu
 */
public class XMLUtil {
	
	/**
	 * 日志记录器
	 */
	private static Logger logger = Logger.getLogger(XMLUtil.class);

	/**
	 * 获取默认的XML的Document对象
	 * 
	 * @param filePath
	 *            文件路径
	 * @return Document对象
	 */
	public static Document getDefault(String filePath) {
		SAXReader reader = new SAXReader();
		reader.setEntityResolver(new IgnoreDTDEntityResolver());
		try {
			Document document = reader.read(new File(filePath));
			return document;
		} catch (DocumentException e) {
			logger.error("error:SAXReader is invoke failed! reason is \n:" + e.getMessage());
			return null;
		}
	}

	/**
	 * 根据文件路径，获取XML的String对象
	 * 
	 * @param filePath
	 *            文件路径
	 * @return String 对象
	 */
	public static String getDefaultXML(String filePath) {
		Document document = getDefault(filePath);
		return null != document ? document.asXML() : null;
	}

	/**
	 * 创建节点不包含内容. e.g. <tag/>
	 * 
	 * @param tag
	 *            标签名称
	 * @return Element Element对象
	 */
	public static Element createElement(String tag) {
		return createElement(tag, null);
	}

	/**
	 * 创建节点包含内容. e.g. <tag>this is tag</tag>
	 * 
	 * @param tag
	 *            标签名称
	 * @param content
	 *            标签的中的文本
	 * @return Element Element对象
	 */
	public static Element createElement(String tag, String content) {
		Element element = new NonLazyElement(tag);
		if(!StringUtils.isEmpty(content)){
			element.setText(content);
		}
		return element;
	}

	/**
	 * 获取当前Element元素（包含多极子元素）的XML字符串
	 * 
	 * @param element
	 *            某个元素
	 * @return Element的XML字符串
	 */
	public static String getElementXML(Element element) {
		return null != element ? element.asXML() : null;
	}

	/**
	 * 将element添加到root元素下面
	 * 
	 * @param root
	 * @param element
	 * @return 添加之后的element
	 */
	public static Element addElement(Element root, Element element) {
		if (null != root && null != element) {
			root.add(element);
		} else {
			logger.error("error:root or element is null");
		}
		return root;
	}

	/**
	 * 向root中添加元素标签为tag的element
	 * 
	 * @param root
	 *            需要被添加的root
	 * @param tag
	 *            标签为tag的元素
	 * @return
	 */
	public static Element addElement(Element root, String tag) {
		if (null != root && !StringUtils.isEmpty(tag)) {
			Element element = createElement(tag);
			root.add(element);
		} else {
			logger.error("error:root or element is null or tag is null");
		}
		return root;
	}

	/**
	 * 向root中添加元素标签为tag,并且内容为content的element
	 * 
	 * @param root
	 *            需要被添加的root
	 * @param tag
	 *            标签为tag的元素
	 * @param content
	 *            标签为tag的元素中的内容
	 * @return
	 */
	public static Element addElement(Element root, String tag, String content) {
		if (null != root && !StringUtils.isEmpty(tag)) {
			Element element = createElement(tag, StringUtils.trimToEmpty(content));
			root.add(element);
		} else {
			logger.error("error:root or element is null or tag is null");
		}
		return root;
	}

	/**
	 * 向文档中添加节点
	 * @param doc 文档结构
	 * @param element 元素节点
	 * @return 文档对象
	 */
	public static Document addElement(Document doc, Element element) {
		if (null != doc && null != element) {
			if(null!=doc.getRootElement()){
				doc.getRootElement().add(element);
			}else{
				logger.error("error:doc has no root node!");
			}
		}else{
			logger.error("error:doc is null or element is null");
		}
		return doc;
	}
	
	/**
	 * 向docunent中添加tag标签
	 * @param doc
	 * @param tag 标签名称
	 * @return 返回 Document对象
	 */
	public static Document addElement(Document doc,String tag){
		if (null != doc && !StringUtils.isEmpty(tag)) {
			if(null!=doc.getRootElement()){
				doc.getRootElement().add(createElement(tag));
			}else{
				logger.error("error:doc has no root node!");
			}
		}else{
			logger.error("error:doc is null or tag is null or empty");
		}
		return doc;
	}
	
	/**
	 * 向document中添加tag标签并且标签内容是content
	 * @param doc 被添加的文档对象
	 * @param tag 标签名称
	 * @param content 标签内容
	 * @return Document对象
	 */
	public static Document addElement(Document doc,String tag,String content){
		if (null != doc && !StringUtils.isEmpty(tag)) {
			if(null!=doc.getRootElement()){
				doc.getRootElement().add(createElement(tag,StringUtils.trimToEmpty(content)));
			} else {
				logger.error("error:doc has no root node!");
			}
		} else {
			logger.error("error:doc is null or tag is null or empty");
		}
		return doc;
	}
	
	/**
	 * 根据标签名字，查询是否存在该标签元素
	 * @param doc
	 * @param tag
	 * @return
	 */
	public static boolean isExists(Document doc,String tag){
		if(null!=doc&&!StringUtils.isEmpty(tag)){
			Element root=doc.getRootElement();
			if(null!=root&&null!=root.element(tag)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	/**
	 * 通过标签名称在文档中查询匹配的第一个元素
	 * @param doc
	 * @param tag 标签名称
	 * @return 匹配的第一个元素，如果没有则返回null
	 */
	public static Element findElement(Document doc,String tag){
		if(null!=doc&&!StringUtils.isEmpty(tag)){
			Element root=doc.getRootElement();
			if(null!=root){
				return root.element(tag);
			}else{
				logger.error("error : root is null");
			}
		}
		return null;
	}
	
	/**
	 * 从当前元素中，查询名称为tag的标签的第一个匹配元素
	 * @param element
	 * @param tag 标签名称
	 * @return 匹配的元素，如果没有则返回null
	 */
	public static Element findElement(Element element,String tag){
		if(null!=element&&!StringUtils.isEmpty(tag)){
			return element.element(tag);
		}else{
			logger.error("error : element is null or tag is empty");
			return null;
		}
	}
	
	/**
	 * 查询element中所有tag标签的元素
	 * @param element
	 * @param tag 标签名称
	 * @return 返回结果集合，如果没有匹配的返回null
	 */
	public static List<Element> findALLMatchElement(Element element,String tag){
		if(null!=element){
			@SuppressWarnings("unchecked")
			List<Element> matches=element.elements(tag);
			return (null!=matches&&matches.size()>0)?matches:null;
		}
		return null;
	}
	
	/**
	 * 查询该节点的下一个节点元素
	 * @param rootElement:根节点
	 * @param content:查询内容
	 * @param tag:要修正的标签
	 * @return 返回下一个节点元素，如果没有匹配返回null
	 */
	public static List<Element> findNextElement(Element rootElement,String content,String tag){
		if(null!=rootElement){
			@SuppressWarnings("unchecked")
			List<Element> matches = rootElement.elements();
			String result = Constant.NO;
			if(matches.size()>0){
				for (Element element : matches) {
					if(Constant.YES.equals(result)){
						element = createElement(tag);
						break;
					}
					if(element.getName().equals(content)){
						result = Constant.YES;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 修改节点element内容
	 * @param element 需要修改的元素
	 * @param tag 新标签名称，如果为null或者是空字符串则不会修改原标签名称
	 * @param content 标签文本内容，如果为null或则空字符串则不会修改过原文本内容
	 * @return
	 */
	public static Element modifyElement(Element element,String tag,String content){
		if(null!=element){
			if(!StringUtils.isEmpty(tag)){
				element.setName(tag);
			}
			if(!StringUtils.isEmpty(content)){
				element.setText(content);
			}
		}
		return element;
	}
	
	/**
	 * 删除sourceEle元素下的直接孩子元素targetEle
	 * @param sourceEle 原元素
	 * @param targetEle 需要删除的目标元素
	 * @return true 删除成功，false 删除失败
	 */
	public static boolean deleteElement(Element sourceEle,Element targetEle){
		if(null!=sourceEle&&null!=targetEle){
			return sourceEle.remove(targetEle);
		}
		return false;
	}
	
	/**
	 * 删除sourceEle元素下面直接的标签名称为tag的孩子节点
	 * @param sourceEle 原元素
	 * @param tag 需要删除的名称为tag的目标元素
	 * @return true 删除成功，false 删除失败
	 */
	public static boolean deleteElement(Element sourceEle,String tag){
		if(null!=sourceEle&&!StringUtils.isEmpty(tag)){
			Element element=findElement(sourceEle, tag);
			if(null!=element){
				return sourceEle.remove(element);
			}
		}
		return false;
	}
	
	/**
	 * 将一段符合xml标准的string转换成xml文档中的element对象
	 * @param content 符合xml标准的string
	 * @return element对象
	 */
	public static Element parse(String content){
		SAXReader bulider=new SAXReader();
		Document document=null;
		Element element=null;
		try{
			document=bulider.read(new StringReader(content));
		}catch(DocumentException ex){
			logger.error("parse string to xml element generate error,reason is :"+ex.getMessage());
		}
		if(null!=document){
			element=document.getRootElement();
		}
		return element;
	}
	
	/**
	 * 将XML转换为MAP格式，标签名称为key值，标签内容为value值
	 * @param element
	 * @return map对象
	 */
	public static Map<String, String> parse(Element element){
		if(null!=element){
			Map<String, String> tags=new Hashtable<>();
			tags.put(element.getName(), element.getText());
			@SuppressWarnings("unchecked")
			List<Element> list=element.elements();
			if(null!=list&&list.size()>0){
				for (Element e : list) {
					tags.put(e.getName(), e.getText());
				}
			}
			return tags;
		} else{
			logger.error("error:the input parameter named element is null");
			return null;
		}
	}
	
	/**
	 * 创建文档头部内容
	 * @return
	 */
	public static Document createDocument(){
    	Document listDoc = DocumentHelper.createDocument();     
    	listDoc.setXMLEncoding("utf-8");  
    	DocumentFactory documentFactory=new DocumentFactory();  
    	DocumentType documentType=documentFactory.createDocType(Constant.ElementType.PLIST, Constant.ElementType.APPLE_DTD, Constant.ElementType.PROPERTY_LIST);  
    	listDoc.setDocType(documentType);  
    	return listDoc;
	}
	
	/**
	 * 获取当前节点的前面的第index个兄弟节点
	 * @param element
	 * @param index 必须大于等于1
	 * @return
	 */
	public static Element precedingSibling(Element element,int index){
		@SuppressWarnings("rawtypes")
		List elems=element.selectNodes("./preceding-sibling::*");
		if(null==elems||elems.size()<index||index<1){
			return null;
		}else{
			return (Element) elems.get(elems.size()-index);
		}
	}
	
	
	/**
	 * 获取当前节点的前1个兄弟节点
	 * @param element
	 * @param index
	 * @return
	 */
	public static Element precedingSibling(Element element){
		@SuppressWarnings("rawtypes")
		List elems=element.selectNodes("./preceding-sibling::*");
		if(null==elems||elems.size()==0){
			return null;
		}else{
			return (Element) elems.get(elems.size()-1);
		}
	}
	
	/**
	 * 获取当前节点的后面的第index个兄弟节点
	 * @param element
	 * @param index 必须大于等于1
	 * @return
	 */
	public static Element followingSibling(Element element,int index){
		@SuppressWarnings("rawtypes")
		List elems=element.selectNodes("./following-sibling::*");
		if(null==elems||elems.size()<index||index<1){
			return null;
		}else{
			return (Element) elems.get(index-1);
		}
	}
	
	
	/**
	 * 获取当前节点的后1个兄弟节点
	 * @param element
	 * @param index
	 * @return
	 */
	public static Element followingSibling(Element element){
		@SuppressWarnings("rawtypes")
		List elems=element.selectNodes("./following-sibling::*");
		if(null==elems||elems.size()==0){
			return null;
		}else{
			return (Element) elems.get(0);
		}
	}
	
	/**
	 * 校验表达式，如果没有指定索引默认是第一个
	 * @param expression
	 * @return
	 */
	private static String verifyExpression(String expression){
		if(StringUtils.isEmpty(expression)||expression.endsWith("/")){
			logger.error(String.format("error:the expression %s was input wrong!", expression));
			return null;
		}
		if(!expression.startsWith("/")&&!expression.contains("/")){
			return expression.trim();
		}
		String[] exps=expression.split("/");
		StringBuffer buffer=new StringBuffer();
		if(exps.length>1){
			for (int i=0;i<exps.length;i++) {
				//如果以/开头的表达式，则第一个是空字符串
				if(!"".equals(exps[i])){
					if(i<exps.length-1){
						exps[i]=exps[i].trim().contains("[")&&exps[i].trim().contains("]")?exps[i].trim():String.format("%s[1]",exps[i].trim());
					}
					buffer.append(String.format("/%s", exps[i]));
				}
			}
		}
		return buffer.toString();
	}
	
	/**
	 * 在元素element中搜寻<key>标签值为key的direct方向的元素
	 * @param element 原element
	 * @param key key标签的内容
	 * 类似于 /plist/dict/array/dict/PayloadDisplayName
	 * 或者/plist[1]/dict[1]/array[1]/dict[1]/PayloadDisplayName
	 * 或者 plist[1]/dict[1]/array[1]/dict[1]/PayloadDisplayName
	 * 表示的从根节点plist开始，查找下面的第一个dict节点下面的第一个array节点下面的第一个dict节点下面key值为PayloadDisplayName的节点
	 * @param direct key所查找到的节点的  current标签本身节点，pre表示前一个节点，next表示后一个节点
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Element findElement(Element element,String key,DirectionStatus direct){
		if(element==null){
			return null;
		}
		
		direct=null==direct?DirectionStatus.NEXT:direct;
		key=verifyExpression(key);
		if(null!=key){
			List<Element> elems=new ArrayList<>();
			if(!key.contains("/")){
				List<Element> elemList=getAllElement(element,elems);
				if(CollectionUtils.isNotEmpty(elemList)){
					for (Element e : elemList) {
						if(e.getText()!=null&&e.getText().toLowerCase().equals(key.toLowerCase())){
							elems.add(e);
							break;
						}
					}
				}
			}else{
				//获取最后的key标签的关键字内容，例如PayloadDisplayName
				String prefix=key.substring(0, key.lastIndexOf('/'));
				elems=element.selectNodes(String.format("%s/key", prefix));
			}
			String keyVal=key.substring(key.lastIndexOf('/')+1);
			if(CollectionUtils.isNotEmpty(elems)){
				Element targetElement=null;
				for (Element e : elems) {
					if(StringUtils.isNotEmpty(e.getText().trim())&&keyVal.toLowerCase().equals(e.getText().trim().toLowerCase())){
						targetElement=e;
						break;
					}
				}
				if(targetElement==null){
					return targetElement;
				}
				if(DirectionStatus.NEXT.equals(direct)){
					targetElement=XMLUtil.followingSibling(targetElement);
				}else if(DirectionStatus.PRE.equals(direct)){
					targetElement=XMLUtil.precedingSibling(targetElement);
				}
				return targetElement;
			}
			logger.info("no element match the expression!");
			return null;
		}
		return null;
	}
	
	/**
	 * 获取当前节点下的所有节点，包含子节点
	 * @param element
	 * @return
	 */
	private static List<Element> getAllElement(Element element,List<Element> elements){
		if(element!=null){
			@SuppressWarnings("unchecked")
			List<Element> temp=element.elements();
			if(CollectionUtils.isNotEmpty(temp)){
				for (Element e : temp) {
					getAllElement(e,elements);
				}
			}else{
				elements.add(element);
			}
			return elements;
		}else{
			return null;
		}
	}
	
}
