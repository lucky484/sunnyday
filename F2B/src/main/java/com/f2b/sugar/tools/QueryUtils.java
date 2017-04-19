package com.f2b.sugar.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Author: 居泽平  Date: 13-8-23, 下午5:33
 */
public final class QueryUtils {

	private final static Logger logger = LoggerFactory.getLogger(QueryUtils.class);

	/**
	 * 通过传入相应的参数，获取TypedQuery对象以进行下一步的数据查询
	 * 注意：当进行模糊查询时需自行判断参数是否为NULL
	 *
	 * @param selectInfo    JPQL选择语句
	 * @param condition     JPQL条件语句hash列表
	 * @param orderInfo     JPQL排序语句
	 * @param entityManager EntityManager Object.
	 * @param resultClass   T.class
	 * @param <T>           T
	 * @return TypedQuery Object.
	 */
	public static <T> TypedQuery<T> getTypedQueryByCondition(String selectInfo, Map<String, Object> condition, String orderInfo, EntityManager entityManager, Class<T> resultClass) {

		StringBuilder query = new StringBuilder();
		query.append(selectInfo);
		query.append("where 1 = 1 ");

		int i = 1;
		if (condition != null) {

			for (Entry<String, Object> entry : condition.entrySet()) {
				String key = entry.getKey();
				Object conditionValue = entry.getValue();
				if (conditionValue != null && !"".equals(conditionValue) && key != null) {
					key = key.replace("{paramIndex}", String.valueOf(i++));
					query.append(" and ");
					query.append(key);
					query.append(" ");
				}
			}
		}

		query.append(orderInfo);
		logger.info("拼接完成的sql语句为：[" + query.toString() + "]");

		TypedQuery<T> typedQuery = entityManager.createQuery(query.toString(), resultClass);

		if (condition != null) {
			i = 1;
			for (Entry<String, Object> entry : condition.entrySet()) {
				Object conditionValue = entry.getValue();
				if (conditionValue != null && !"".equals(conditionValue)) {
					typedQuery.setParameter(i, conditionValue);
					logger.info("为拼接完成的JPQL第[" + i++ + "]个参数赋值:[" + conditionValue + "]");
				}
			}
		}

		logger.info("---------------------------------------\r\n");

		return typedQuery;
	}
}
