package org.uom.cse.cs4262.controller;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.uom.cse.cs4262.api.Constant;
import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.Node;
import org.uom.cse.cs4262.api.message.request.DownloadReq;
import org.uom.cse.cs4262.api.message.request.JoinReq;
import org.uom.cse.cs4262.api.message.request.LeaveReq;
import org.uom.cse.cs4262.api.message.request.SearchReq;
import org.uom.cse.cs4262.api.message.response.DownloadRes;
import org.uom.cse.cs4262.api.message.response.SearchRes;
import org.uom.cse.cs4262.ui.GUI;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Configuration
@ComponentScan("org.uom.cse.cs4262")
@EnableAutoConfiguration
@RestController
public class BSNode extends SpringBootServletInitializer {

    private static NodeOperationsImpl nodeOperationsImpl;

    public static void main(String[] args) {

        HashMap<String, String> paramMap = new HashMap<>();

        for (int i = 0; i < args.length; i = i + 2) {
            paramMap.put(args[i], args[i + 1]);
        }

        String bootstrapIp = paramMap.get("-b") != null ? paramMap.get("-b") : Constant.BS_IP;
        String nodeIp = paramMap.get("-i") != null ? paramMap.get("-i") : Constant.BS_IP;
        int nodePort = paramMap.get("-p") != null ? Integer.parseInt(paramMap.get("-p")) : new Random().nextInt(Constant.MAX_PORT - Constant.MIN_PORT) + Constant.MIN_PORT;
        String nodeUsername = paramMap.get("-u") != null ? paramMap.get("-u") : UUID.randomUUID().toString();

        System.setProperty(Constant.SERVER_PORT, String.valueOf(nodePort));

        Cred bootstrapCred = new Cred(bootstrapIp, Constant.BS_PORT, Constant.BS_USERNAME);
        Cred nodeCred = new Cred(nodeIp, nodePort, nodeUsername);

        Node node = new Node(nodeCred, createFileList(), new ArrayList<>(), new ArrayList<>(), bootstrapCred, 0, 0, 0, 0, new LinkedHashMap<>(), new ArrayList<>());

        nodeOperationsImpl = new NodeOperationsImpl(node);
        nodeOperationsImpl.start();
        nodeOperationsImpl.register();

        while (true) {
            try {
                Thread.sleep(1000);
                if (nodeOperationsImpl.isRegisterOk()) {
                    // TODO: stop node socket which is listening
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        javax.swing.SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            GUI mainFrame = new GUI(nodeOperationsImpl);
            mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            mainFrame.pack();
            mainFrame.setTitle("Distributed Systems Client App");
            mainFrame.setLocationRelativeTo(null);
            mainFrame.start();
        });

        SpringApplication.run(BSNode.class, args);
    }

    private static List<String> createFileList() {
        ArrayList<String> fileList = new ArrayList<>();
        fileList.add("Adventures of Tintin");
        fileList.add("Jack and Jill");
        fileList.add("Glee");
        fileList.add("The Vampire Diarie");
        fileList.add("King Arthur");
        fileList.add("Windows XP");
        fileList.add("Harry Potter");
        fileList.add("Kung Fu Panda");
        fileList.add("Lady Gaga");
        fileList.add("Twilight");
        fileList.add("Windows 8");
        fileList.add("Mission Impossible");
        fileList.add("Turn Up The Music");
        fileList.add("Super Mario");
        fileList.add("American Pickers");
        fileList.add("Microsoft Office 2010");
        fileList.add("Happy Feet");
        fileList.add("Modern Family");
        fileList.add("American Idol");
        fileList.add("Hacking for Dummies");
        Collections.shuffle(fileList);
        List<String> subFileList = fileList.subList(0, 5);
        return subFileList;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BSNode.class);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public String search(@RequestBody String json) {
        System.out.println("heresearch................................");
        nodeOperationsImpl.getNode().incReceivedQueryCount();
        SearchReq searchReq = new Gson().fromJson(json, SearchReq.class);
        Executors.newScheduledThreadPool(1).schedule(
                () -> nodeOperationsImpl.passSearchRequest(searchReq),
                10, TimeUnit.MILLISECONDS
        );
        if (nodeOperationsImpl.getNode().getFileList().contains(searchReq.getFileName())) {
            return String.valueOf(HttpStatus.ACCEPTED);
        } else {
            return String.valueOf(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/searchok", method = RequestMethod.POST)
    @ResponseBody
    public String searchOk(@RequestBody String json) {
        nodeOperationsImpl.getNode().incReceivedQueryCount();
        SearchRes searchRes = new Gson().fromJson(json, SearchRes.class);
        nodeOperationsImpl.searchSuccess(searchRes);
        return String.valueOf(HttpStatus.OK);
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ResponseBody
    public String join(@RequestBody String json) {
        nodeOperationsImpl.getNode().incReceivedQueryCount();
        JoinReq joinReq = new Gson().fromJson(json, JoinReq.class);
        nodeOperationsImpl.joinMe(joinReq);
        return Constant.Action.JOIN_OK;
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    @ResponseBody
    public String download(@RequestBody String json) {
//        DownloadReq downloadReq = new Gson().fromJson(json, DownloadReq.class);
//        int fileSize = nodeOperationsImpl.getNode().getRandFileSize();
//        String fileHash = nodeOperationsImpl.getNode().generateSHAHash(nodeOperationsImpl.getNode().createDataSize(fileSize));
//        DownloadRes downloadRes = new DownloadRes(downloadReq.getCred(), fileHash, fileSize);
//        nodeOperationsImpl.downloadOk(downloadRes);
//        if (nodeOperationsImpl.getNode().getFileList().contains(downloadReq.getFileName())) {
//            return String.valueOf(HttpStatus.ACCEPTED);
//        } else {
//            return String.valueOf(HttpStatus.OK);
//        }
        nodeOperationsImpl.getNode().incReceivedQueryCount();
        DownloadReq downloadReq = new Gson().fromJson(json, DownloadReq.class);
        System.out.println("here...........");
        Executors.newScheduledThreadPool(1).schedule(
                () -> nodeOperationsImpl.passDownloadRequest(downloadReq),
                10, TimeUnit.MILLISECONDS
        );
        if (nodeOperationsImpl.getNode().getFileList().contains(downloadReq.getFileName())) {
            return String.valueOf(HttpStatus.ACCEPTED);
        } else {
            return String.valueOf(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/downloadok", method = RequestMethod.POST)
    @ResponseBody
    public String downloadOk(@RequestBody String json) {

        DownloadRes downloadRes = new Gson().fromJson(json, DownloadRes.class);
        nodeOperationsImpl.downloadSuccess(downloadRes);
        return String.valueOf(HttpStatus.OK);
    }

    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    @ResponseBody
    public String leave(@RequestBody String json) {
        nodeOperationsImpl.getNode().incReceivedQueryCount();
        LeaveReq leaveReq = new Gson().fromJson(json, LeaveReq.class);
        nodeOperationsImpl.removeMe(leaveReq);
        return Constant.Action.LEAVE_OK;
    }
}