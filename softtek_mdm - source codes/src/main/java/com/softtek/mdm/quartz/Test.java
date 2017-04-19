package com.softtek.mdm.quartz;

/**
 * 测试类
 * @author Jackson.zhu
 *
 */
public class Test {

    public static void main(String[] args) {
        BackupJob job2 = new BackupJob();
        String job_name ="11";
        try {
            System.out.println("【系统启动】");
            //QuartzManager.addJob(job_name,job,"0 */1 * * * ?"); //每2秒钟执行一次
            QuartzManager.addJob(job_name,job2,"*/5 * * * * ?"); //每2秒钟执行一次
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
