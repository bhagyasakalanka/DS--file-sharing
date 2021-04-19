package org.example.com.ds;

import org.example.com.ds.ui.MainUI;
import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TestClass {

    public String REGISTER = "0036 REG 129.82.123.45 5001 1234abcd";
    public String UNREGISTER = "0028 UNREG 64.12.123.190 432 1234abcd";
    public String JOIN = "0027 JOIN 64.12.123.190 432";
    public String LEAVE = "0028 LEAVE 64.12.123.190 432";
    public String SEARCH = "0047 SER 12 129.82.62.142 5070 Lord_of_the_rings 10";

    public String REGISTER_OK = "0051 REGOK 2 129.82.123.45 5001 64.12.123.190 34001";
    public String UNREGISTER_OK = "0012 UNROK 0";
    public String JOIN_OK = "0014 JOINOK 0";
    public String LEAVE_OK = "0014 LEAVEOK 0";
    public String SEARCH_OK = "0114 SEROK 4 3 129.82.128.123 8823 01 baby_go_home.mp3 baby_come_back.mp3 baby.mpeg";
    public String ERROR_OK = "0010 ERROR";

    @Test
    public void testSearch() {
        List<String> fileList = new ArrayList<>();
        fileList.add("Twilight");
        fileList.add("Jack");
        fileList.add("Jack and Jill");
        fileList.add("Twilight saga");
        fileList.add("My Twilight");
        Collections.shuffle(fileList);
        fileList = fileList.subList(0, 5);

        Pattern pattern = Pattern.compile("Twilight");
        List<String> matching = fileList.stream().filter(pattern.asPredicate()).collect(Collectors.toList());

        for (String s : matching) {
            Assert.assertTrue(s.equals("Twilight") || s.equals("Twilight saga") || s.equals("My Twilight"));
        }
    }

    @Test
    public void testUI() {
        MainUI mainFrame = new MainUI();
        mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        mainFrame.pack();
        mainFrame.setTitle("Distributed Systems Client App");
        mainFrame.setLocationRelativeTo(null);
        System.out.println("tttt");
        mainFrame.setVisible(true);
        System.out.println("yyyyy");
        SwingUtilities.invokeLater(() -> {
            mainFrame.setExtendedState(mainFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
            mainFrame.pack();
            mainFrame.setTitle("Distributed Systems Client App");
            mainFrame.setLocationRelativeTo(null);
            new MainUI().start();
        });
    }
}