package com.shell.manager.config;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.shell.manager.ui.listener.UIUpdateListener;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * *
 * @author alan.wang
 *
 *
 *
 */

public class SSHAgent extends UIUpdateListener {


    private BlockingQueue<String> msgQueue = new ArrayBlockingQueue<String>(1000);
    private Logger log = LoggerFactory.getLogger(getClass());
    private Connection connection;
    private Session session;
    private BufferedReader stdout;
    private PrintWriter printWriter;
    private BufferedReader stderr;
    private ExecutorService service = Executors.newFixedThreadPool(3);

    public void initSession(String hostName, String userName, String passwd) throws IOException, InterruptedException {
        connection = new Connection(hostName);
        connection.connect();

        boolean authenticateWithPassword = connection.authenticateWithPassword(userName, passwd);
        if (!authenticateWithPassword) {
            throw new RuntimeException("Authentication failed. Please check hostName, userName and passwd");
        }
        session = connection.openSession();
        session.requestDumbPTY();
        session.startShell();
        stdout = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout()), StandardCharsets.UTF_8));
        stderr = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStderr()), StandardCharsets.UTF_8));
        printWriter = new PrintWriter(session.getStdin());
        onCallback();
    }

    public void onCallback(){
        service.submit(new Runnable() {

            @Override
            public void run() {


                String line;
                try {
                    while ((line = stdout.readLine()) != null) {
                        onUpdate(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        service.submit(new Runnable() {

            @Override
            public void run() {

                try {

                    String cmd = null;
                    while ((cmd = msgQueue.take()) != null) {
                        printWriter.write(cmd);
                        printWriter.flush();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    public void execCommand(String cmd) throws IOException {
        msgQueue.add(cmd);
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
    public static void run() throws IOException, InterruptedException {

        String host = "154.8.162.93";
        String user = "root";
        String password = "Madness1111";
        SSHAgent sshAgent = new SSHAgent();
        sshAgent.initSession(host, user, password);


        // sshAgent.close();

    }

}