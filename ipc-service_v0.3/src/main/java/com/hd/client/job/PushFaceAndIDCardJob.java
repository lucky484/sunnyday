package com.hd.client.job;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.hd.client.model.FaceInfoModel;
import com.hd.client.model.IdCardInfo;
import com.hd.client.service.FaceAndIdcardService;
import com.hd.client.service.FaceInfoService;
import com.hd.client.service.IDInfoService;
import com.hd.client.util.TransMap2Bean;

/**
 * 身份证服务job 这也是一个将数据转发到后台的服务
 * 
 * @author ligang.yang
 *
 */
@Service("pushFaceAndIDCardJob")
@Scope("prototype")
public class PushFaceAndIDCardJob {

	/**
	 * 查询人脸信息和身份证信息
	 */
	@Autowired
	private FaceAndIdcardService totalserv;

	/**
	 * 注入人脸数据操作的service
	 */
	@Autowired
	private FaceInfoService faceServ;

	/**
	 * 注入身份证数据操作的service
	 */
	@Autowired
	private IDInfoService idInfoServ;

	/**
	 * 发送中的id
	 */
	private Long id;

	/**
	 * 这是一个转发身份证数据的线程池
	 */
	public static final ExecutorService pool = Executors.newFixedThreadPool(10);

	public static final Object obj = new Object();

	/**
	 * 定时任务：定时发送身份证数据给服务器
	 */
	public void pushFaceAndIDCardInfo() {
		// 从本地抓取一条最新的数据， 如果没有就算了
		Map<String, Object> map = totalserv.queryFCInfo();
		if (map == null)
			return;
		String flag = (String) map.get("flagtype");
		Integer id = (Integer) map.get("fc_id");
		if (flag == null)
			return;
		if ("1".equals(flag)) {
			IdCardInfo model = new IdCardInfo();
			TransMap2Bean.transMap2Bean2(map, model);

			pool.execute(new PushIDCardJob(id, model, idInfoServ));
		} else if ("2".equals(flag)) {
			FaceInfoModel model = new FaceInfoModel();
			TransMap2Bean.transMap2Bean2(map, model);
			// 如果有会先更新这条数据的状态为发送中

			// 让线程池去执行http请求去完成数据转发
			pool.execute(new PushFaceJob(id, model, faceServ));
		}
	}
}
