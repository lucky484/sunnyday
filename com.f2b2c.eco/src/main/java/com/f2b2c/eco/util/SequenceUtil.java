
package com.f2b2c.eco.util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 数据库序列生成
 * date: Apr 28, 2016 2:44:14 PM
 *
 * @author brave.chen
 */
public class SequenceUtil
{
	private static final long ONE_STEP = 10;
	private static final Lock LOCK = new ReentrantLock();
	private static long lastTime = System.currentTimeMillis();
	private static short lastCount = 0;
	private static int count = 0;

	/**
	 * 
	 * @param sequenceType（F端表示平台，B商铺,C消费者,M合并订单号,G代表商品编号,R代表充值）
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String nextId(String sequenceType)
	{
		LOCK.lock();
		try
		{
			if (lastCount == ONE_STEP)
			{
				boolean done = false;
				while (!done)
				{
					long now = System.currentTimeMillis();
					if (now == lastTime)
					{
						try
						{
							Thread.currentThread();
							Thread.sleep(1);
						}
						catch (java.lang.InterruptedException e)
						{
						}
						continue;
					}
					else
					{
						lastTime = now;
						lastCount = 0;
						done = true;
					}
				}
			}
			count = lastCount++;
		}
		finally
		{
			LOCK.unlock();
			return sequenceType + lastTime + "" + String.format("%03d", count);
		}
	}
}