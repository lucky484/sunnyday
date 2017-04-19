package com.hd.client.job;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.client.model.IdCardInfo;
import com.hd.client.service.ClientPostService;
import com.hd.client.service.IDInfoService;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 身份证服务job 这也是一个将数据转发到后台的服务
 * 
 * @author ligang.yang
 *
 */
@Service("pushIDCardJob")
public class PushIDCardJob implements Runnable {

	/**
	 * 打出错误日志
	 */
	private Logger logger = Logger.getLogger(PushIDCardJob.class);

	/**
	 * 注入身份证数据操作的service
	 */
	@Autowired
	private IDInfoService idInfoServ;

	/**
	 * 发送中的id
	 */
	private Integer id;

	/**
	 * 将参数封装成json发送
	 */
	private IdCardInfo model;

	/**
	 * 这是一个转发身份证数据的线程池
	 */
	// public static final ExecutorService poolIDcard =
	// Executors.newFixedThreadPool(10);

	public PushIDCardJob() {
	}

	public PushIDCardJob(Integer id, IdCardInfo model, IDInfoService idInfoServ) {
		this.id = id;
		this.model = model;
		this.idInfoServ = idInfoServ;
	}

	/**
	 * 定时任务：定时发送身份证数据给服务器
	 */
	// public void pushIDCardInfo() {
	//
	// // 从本地抓取一条最新的数据， 如果没有就算了
	// IdCardInfo model = idInfoServ.getIdCardInfo();
	// if (model == null)
	// return;
	// // 如果有会先更新这条数据的状态为发送中
	// idInfoServ.updateIDInfoDao(String.valueOf(id), String.valueOf(1));
	//
	// // 让线程池去执行http请求去完成数据转发
	// poolIDcard.execute(new Thread(new PushIDCardJob(model.getCardId(), model,
	// idInfoServ)));
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
				idInfoServ.updateFCInfoDao(String.valueOf(id), t);
				logger.info("successPushIDCardJob======" + t);
			}

			@Override
			public void failure(RetrofitError error) {
				idInfoServ.updateFCInfoDao(String.valueOf(id), "-1");
				logger.error("failurePushIDCardJob======" + error.getMessage());
			}
		});

	}

}
