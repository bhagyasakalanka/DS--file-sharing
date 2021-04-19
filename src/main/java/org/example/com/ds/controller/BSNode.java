package org.example.com.ds.controller;

import com.google.gson.Gson;
import org.example.com.ds.api.message.req.SearchReq;
import org.example.com.ds.api.message.res.SearchRes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.example.com.ds.api.Constant;
import org.example.com.ds.api.Cred;
import org.example.com.ds.api.Node;
import org.example.com.ds.api.message.req.JoinReq;
import org.example.com.ds.api.message.req.LeaveReq;
import org.example.com.ds.ui.MainUI;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Configuration
@ComponentScan("org.uom.cse.cs4262")
@EnableAutoConfiguration
@RestController
public class BSNode extends SpringBootServletInitializer {

    private static NodeOperationsWS nodeOperations;

    public static void main(String[] args) {

        HashMap<String, String> parameters = new HashMap<>();

        for (int i = 0; i < args.length; i = i + 2) {
            parameters.put(args[i], args[i + 1]);
        }


        String bsIP = parameters.get("-b") != null ? parameters.get("-b") : Constant.BS_IP;
        String nodeIp = parameters.get("-i") != null ? parameters.get("-i") : Constant.BS_IP;
        int nodePort = parameters.get("-p") != null ? Integer.parseInt(parameters.get("-p")) : new Random().nextInt(Constant.MAX_PORT - Constant.MIN_PORT) + Constant.MIN_PORT;
        String nodeUsername = parameters.get("-u") != null ? parameters.get("-u") : UUID.randomUUID().toString();

        System.setProperty(Constant.S_PORT, String.valueOf(nodePort));

        Cred bsCred = new Cred(bsIP, Constant.BS_PORT, Constant.BS_USERNAME);
        Cred nodeCred = new Cred(nodeIp, nodePort, nodeUsername);

        Node node = new Node(nodeCred, selectRandomFileList(), new ArrayList<>(), new ArrayList<>(), bsCred, 0, 0, 0, 0, new LinkedHashMap<>(), new ArrayList<>());

        nodeOperations = new NodeOperationsWS(node);
        nodeOperations.start();
        nodeOperations.register();

        while (true) {
            try {
                Thread.sleep(1000);
                if (nodeOperations.isRegisterOK()) {
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

            MainUI mainFrame = new MainUI(nodeOperations);
            mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            mainFrame.pack();
            mainFrame.setTitle("File Search");
            mainFrame.setLocationRelativeTo(null);
            mainFrame.start();
        });

        SpringApplication.run(BSNode.class, args);
    }

    private static List<String> selectRandomFileList() {
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
        nodeOperations.getNode().incReceivedQueryCount();
        SearchReq searchReq = new Gson().fromJson(json, SearchReq.class);
        Executors.newScheduledThreadPool(1).schedule(
                () -> nodeOperations.passSearchRequest(searchReq),
                10, TimeUnit.MILLISECONDS
        );
        if (nodeOperations.getNode().getNodeFileList().contains(searchReq.getFileName())) {
            return String.valueOf(HttpStatus.ACCEPTED);
        } else {
            return String.valueOf(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/searchok", method = RequestMethod.POST)
    @ResponseBody
    public String searchOk(@RequestBody String json) {
        nodeOperations.getNode().incReceivedQueryCount();
        SearchRes searchRes = new Gson().fromJson(json, SearchRes.class);
        nodeOperations.searchSuccess(searchRes);
        return String.valueOf(HttpStatus.OK);
    }

    @RequestMapping(value = "/join", method = RequestMethod.POST)
    @ResponseBody
    public String join(@RequestBody String json) {
        nodeOperations.getNode().incReceivedQueryCount();
        JoinReq joinReq = new Gson().fromJson(json, JoinReq.class);
        nodeOperations.joinMe(joinReq);
        return Constant.Task.JOIN_OK;
    }


    @RequestMapping(value = "/leave", method = RequestMethod.POST)
    @ResponseBody
    public String leave(@RequestBody String json) {
        nodeOperations.getNode().incReceivedQueryCount();
        LeaveReq leaveReq = new Gson().fromJson(json, LeaveReq.class);
        nodeOperations.removeMe(leaveReq);
        return Constant.Task.LEAVE_OK;
    }
}