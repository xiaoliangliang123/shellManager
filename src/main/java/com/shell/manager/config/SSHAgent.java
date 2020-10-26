package com.shell.manager.config;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 模拟交互式终端
 *
 * @author doctor
 *
 * @time 2015年8月6日
 *
 *
 */
public final class SSHAgent {
    private Logger log = LoggerFactory.getLogger(getClass());
    private Connection connection;
    private Session session;
    private BufferedReader stdout;
    private PrintWriter printWriter;
    private BufferedReader stderr;
    private ExecutorService service = Executors.newFixedThreadPool(3);
    private Scanner scanner = new Scanner(System.in);

    public void initSession(String hostName, String userName, String passwd) throws IOException {
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
    }

    public void execCommand() throws IOException {
        service.submit(new Runnable() {

            @Override
            public void run() {


                String line;
                try {
                    while ((line = stdout.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        service.submit(new Runnable() {

            @Override
            public void run() {
//                while (true) {
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//
//                        e.printStackTrace();
//                    }
//                    System.out.print("input:");
//                    String nextLine = scanner.nextLine();
                    printWriter.write("cd /home\r\n");
                    printWriter.write("ls\r\n");
                    //printWriter.write(nextLine + "\r\n");
                    printWriter.flush();
//                }

            }
        });

    }

    public void close() {
        IOUtils.closeQuietly(stdout);
        IOUtils.closeQuietly(stderr);
        IOUtils.closeQuietly(printWriter);
        IOUtils.closeQuietly(scanner);
        session.close();
        connection.close();
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void run() throws IOException {

        String host = "154.8.162.93";
        String user = "root";
        String password = "Madness1111";
        SSHAgent sshAgent = new SSHAgent();
        sshAgent.initSession(host, user, password);

        sshAgent.execCommand();

        // sshAgent.close();

    }

}