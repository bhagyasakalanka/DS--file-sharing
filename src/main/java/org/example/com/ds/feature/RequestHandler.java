package org.example.com.ds.feature;

import org.example.com.ds.api.message.res.RegisterRes;
import org.example.com.ds.api.Constant;
import org.example.com.ds.api.Cred;
import org.example.com.ds.api.message.Message;
import org.example.com.ds.api.message.req.RegisterReq;
import org.example.com.ds.api.message.req.UnregisterReq;
import org.example.com.ds.api.message.res.UnregisterRes;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class RequestHandler {

    public static Message handle(String message) {

        System.out.println("Received Message : " + message);
        StringTokenizer st = new StringTokenizer(message, " ");

        String command = st.nextToken();

        if (command.equals(Constant.Task.REGISTER)) {
            String ip = st.nextToken();
            int port = Integer.parseInt(st.nextToken());
            String username = st.nextToken();
            Cred userCredentials = new Cred(ip, port, username);
            return new RegisterReq(userCredentials);

        } else if (command.equals(Constant.Task.REGISTER_OK)) {
            int numOfNodes = Integer.parseInt(st.nextToken());
            String ip;
            int port;
            List<Cred> nodes = new ArrayList<>();
            if (!(numOfNodes == Constant.Codes.RegisterCodes.REGISTER_ERROR || numOfNodes == Constant.Codes.RegisterCodes.MULTIPLE_IP_ERROR || numOfNodes == Constant.Codes.RegisterCodes.NODE_EXISTING_ERROR || numOfNodes == Constant.Codes.RegisterCodes.ERROR)) {
                for (int i = 0; i < numOfNodes; i++) {
                    ip = st.nextToken();
                    port = Integer.parseInt(st.nextToken());
                    nodes.add(new Cred(ip, port, null));
                }
            }
            RegisterRes registerRes = new RegisterRes(numOfNodes, nodes);
            return registerRes;

        } else if (command.equals(Constant.Task.UNREGISTER)) {
            String ip = st.nextToken();
            int port = Integer.parseInt(st.nextToken());
            String username = st.nextToken();
            Cred unregisterUserCredentials = new Cred(ip, port, username);
            return new UnregisterReq(unregisterUserCredentials);

        } else if (command.equals(Constant.Task.UNREGISTER_OK)) {
            int value = Integer.parseInt(st.nextToken());
            return new UnregisterRes(value);
        }

        return null;
    }
}
