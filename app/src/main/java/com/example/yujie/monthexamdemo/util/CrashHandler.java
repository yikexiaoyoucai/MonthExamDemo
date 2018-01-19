package com.example.yujie.monthexamdemo.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Dash on 2017/12/7.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler crashHandler;
    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler;
    //存日志文件的目录(文件夹)
    private File logDir = new File(Environment.getExternalStorageDirectory(),"error");
    //时间格式化
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS", Locale.CHINA);

    /**
     * 对外提供获取实例对象的方法....单例模式
     * @return
     */
    public static CrashHandler getInstace(){
        if (crashHandler == null){
            synchronized (CrashHandler.class){
                if (crashHandler == null){
                    crashHandler = new CrashHandler();
                }
            }
        }

        return crashHandler;
    }

    /**
     * 初始化的时候.....
     * @param context
     */
    public void init(Context context){
        this.context = context;

        //先拿到系统默认的异常处理器
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        //设置当前作为我们处理异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当异常发生的时候会自动调用uncaughtException方法
     * @param thread
     * @param throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        //自己没有处理异常,交给系统来处理
        if (!handlerException(throwable) && defaultHandler != null){
            defaultHandler.uncaughtException(thread,throwable);
        }else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //否则自己处理了异常....上传服务器
            uploadLogToServer();
            //结束进程 退出程序
            Process.killProcess(Process.myPid());//杀死应用的进程
            //非正常退出应用....0表示正常退出,,,非0表示不正常
            System.exit(10);
        }
    }

    /**
     * 上传文件到服务器
     */
    private void uploadLogToServer() {
        //遍历sd卡中crash文件夹,,,得到每一个文件

        //使用okhttp的post方式上传file文件

        //把上传的文件crash文件夹中删除

    }

    /**
     * 自己处理异常的方法
     * @return
     * @param throwable
     */
    private boolean handlerException(Throwable throwable) {
        if (throwable == null){
            return false;
        }
        //吐司提示一下
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context,"程序异常",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        //日志写到文件中去
        if (! logDir.exists()){
            logDir.mkdirs();
        }

        //文件夹中创建文件...crash-2017-12-07-HH-mm-ss-SSS.log
        File logFile = new File(logDir,"crash-"+dateFormat.format(new Date())+"-.log");
        if (!logFile.exists()){
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //写入sd卡...使用打印流
        try {
            PrintWriter printWriter = new PrintWriter(logFile);

            collectLogInfoToSdCard(printWriter,throwable);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return true;
    }

    /**
     * 写入sd卡
     * @param printWriter
     * @param throwable
     */
    private void collectLogInfoToSdCard(PrintWriter printWriter, Throwable throwable) {
        //日志里面可能会哟版本号 版本名称 时间

        //获取pakage管理者对象
        PackageManager packageManager = context.getPackageManager();
        //获取当前应用的信息....(context.getPackageName()获取当前应用的包名
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            printWriter.print("版本:");
            printWriter.println(packageInfo.versionCode);
            printWriter.print("版本名称:");
            printWriter.println(packageInfo.versionName);

            printWriter.print("time");
            printWriter.println(dateFormat.format(new Date()));

            //把日志信息放到打印流中去打印
            throwable.printStackTrace(printWriter);

            printWriter.close();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
