package com.hd.client.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.client.model.FaceInfoModel;
import com.hd.client.service.ClientPostService;
import com.hd.client.service.FaceInfoService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 人脸推送服务job 这是一个将数据转发到后台的服务
 * 
 * @author ligang.yang
 *
 */
@Service("pushFaceJob")
public class PushFaceJob implements Runnable {
	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(PushFaceJob.class);
	/**
	 * 注入人脸数据操作的service
	 */
	@Autowired
	private FaceInfoService faceServ;

	/**
	 * 发送中的id
	 */
	private Integer id;

	/**
	 * 将参数封装成json发送
	 */
	private FaceInfoModel model;

	/**
	 * 这是一个转发人脸数据的线程池
	 */
	// public static final ExecutorService poolFace =
	// Executors.newFixedThreadPool(10);

	public PushFaceJob() {
	}

	public PushFaceJob(Integer id, FaceInfoModel model, FaceInfoService faceServ) {
		this.id = id;
		this.model = model;
		this.faceServ = faceServ;
	}

	/**
	 * 定时任务：定时发送人脸数据给服务器
	 */
	// public void pushFaceInfo() {
	// // 从本地抓取一条最新的数据， 如果没有就算了
	// FaceInfoModel model = faceServ.getFaceInfo();
	// if (model == null)
	// return;
	// // 如果有会先更新这条数据的状态为发送中
	// faceServ.updateFaceInfo(String.valueOf(id), String.valueOf(1));
	//
	// // 让线程池去执行http请求去完成数据转发
	// poolFace.execute(new Thread(new PushFaceJob(model.getFaceId(), model,
	// faceServ)));
	// }

	/**
	 * 任务：发送http请求完成数据转发
	 */
	@Override
	public void run() {

		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://localhost:8080/hengdong").build();
		ClientPostService service = restAdapter.create(ClientPostService.class);

		service.sendRow(this.model, new Callback<String>() {

			@Override
			public void success(String t, Response response) {
				faceServ.updateFCInfo(String.valueOf(id), t);
				logger.info("successPushFaceJob======" + t);
			}

			@Override
			public void failure(RetrofitError error) {
				faceServ.updateFCInfo(String.valueOf(id), "-1");
				logger.error("failurePushFaceJob======" + error.getMessage());
			}
		});

	}

}
