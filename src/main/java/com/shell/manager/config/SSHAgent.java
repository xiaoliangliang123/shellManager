package com.shell.manager.config;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.shell.manager.data.db.DatabaseUtil;
import com.shell.manager.ui.listener.UIUpdateListener;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * *
 *
 * @author alan.wang
 */

public class SSHAgent extends UIUpdateListener {


    private BlockingQueue<String> msgQueue = new ArrayBlockingQueue<String>(1000);
    private BlockingQueue<String> outPutQueue = new ArrayBlockingQueue<String>(10000);

    private Logger log = LoggerFactory.getLogger(getClass());
    private Connection connection;
    private Session session;
    private BufferedReader stdout;
    private PrintWriter printWriter;
    private BufferedReader stderr;
    private ExecutorService service = Executors.newFixedThreadPool(3);
    private boolean startOutputStream = false;
    private String name ;
    private FileWriter fileWriter = null;

    public void initSession(String name,String hostName, String userName, String passwd) throws IOException, InterruptedException {
        connection = new Connection(hostName);
        connection.connect();
        this.name = name;
        boolean authenticateWithPassword = connection.authenticateWithPassword(userName, passwd);
        if (!authenticateWithPassword) {
            throw new RuntimeException("Authentication failed. Please check hostName, userName and passwd");
        }
        session = connection.openSession();
        session.requestDumbPTY();
        session.startShell();
        execCommand("");
        session.waitForCondition(ChannelCondition.STDOUT_DATA | ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS, 30000);
        stdout = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout()), StandardCharsets.UTF_8));
        stderr = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStderr()), StandardCharsets.UTF_8));
        printWriter = new PrintWriter(session.getStdin());
        onCallback();

    }

    public void onCallback() {
        service.submit(()-> {

                String line;
                try {
                    while ((line = stdout.readLine()) != null) {
                        onUpdate(line);
                        if(startOutputStream&&line.equals("")) {
                            outPutQueue.add(GenerateUtil.currentTime());
                        }
                        else if(startOutputStream) {
                            outPutQueue.add(line);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        });

        service.submit(() -> {

            try {

                String cmd = null;
                while ((cmd = msgQueue.take()) != null) {
                    printWriter.write(cmd);
                    printWriter.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        service.submit(() -> {
            try {
                String line = null;
                while ((line = outPutQueue.take()) != null) {
                     fileWriter.write(line+"\r\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    public void execCommand(String cmd) throws IOException {
        msgQueue.add(cmd);
        msgQueue.add("\n\r");
        //session.execCommand(cmd+"\n\r");
    }


    public void close() throws IOException {
        IOUtils.close(stdout);
        IOUtils.close(stderr);
        IOUtils.close(printWriter);
        session.close();
        connection.close();
    }

    /**
     * @throws IOException
     */
    public static void main(String[] arges) throws IOException, InterruptedException {

        String name = "test";
        String host = "154.8.162.93";
        String user = "root";
        String password = "Madness1111";
        SSHAgent sshAgent = new SSHAgent();
        sshAgent.initSession(name,host, user, password);
        sshAgent.onCallback();
        sshAgent.execCommand("");

        // sshAgent.close();

    }

    public void startOutputStream() throws IOException {
        String path = System.getProperty("user.dir");
        File file = new File(path+File.separator+name+"_"+ GenerateUtil.currentFileTime()+".log");
        if(!file.exists()){
            file.createNewFile();
        }
        if(fileWriter!=null){
            fileWriter.close();
            fileWriter = null;
        }
        fileWriter = new FileWriter(file);
        startOutputStream = true;
    }

    public void stopOutputStream() {
        startOutputStream = false;
    }
}