import redis.clients.jedis.Jedis;

/**
* @ClassName: Test
* @Description: TODO(这里用一句话描述这个类的作用)
* @author Jacob Shen
* @date Aug 17, 2016 3:49:26 PM
*/

/**
 * @author jacob.shen
 *
 */
public class Test {
	public static void main(String[] args) {
		// Connecting to Redis server on localhost
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server sucessfully");
		// check whether server is running or not
		System.out.println("Server is running: " + jedis.ping());
	}
}
