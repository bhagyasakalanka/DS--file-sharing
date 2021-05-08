package org.uom.cse.cs4262.feature;

import org.uom.cse.cs4262.api.Constant;
import org.uom.cse.cs4262.api.Cred;
import org.uom.cse.cs4262.api.message.Message;
import org.uom.cse.cs4262.api.message.request.RegisterReq;
import org.uom.cse.cs4262.api.message.request.UnregisterReq;
import org.uom.cse.cs4262.api.message.response.RegisterRes;
import org.uom.cse.cs4262.api.message.response.UnregisterRes;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class ConnectionHandler {

    public static Message handle(String message) {

        System.out.println("Message received : " + message);
        StringTokenizer st = new StringTokenizer(message, " ");

        String length = st.nextToken();
        String command = st.nextToken();

        if (command.equals(Constant.Action.REGISTER)) {
            String ip = st.nextToken();
            int port = Integer.parseInt(st.nextToken());
            String username = st.nextToken();
            Cred userCredentials = new Cred(ip, port, username);
            return new RegisterReq(userCredentials);

        } else if (command.equals(Constant.Action.REGISTER_OK)) {
            int numOfNodes = Integer.parseInt(st.nextToken());
            String ip;
            int port;
            List<Cred> nodes = new ArrayList<>();
            if (!(numOfNodes == Constant.ErrorCode.Register.REGISTER_ERROR || numOfNodes == Constant.ErrorCode.Register.DUPLICATE_IP_ERROR || numOfNodes == Constant.ErrorCode.Register.ALREADY_REGISTERED_ERROR || numOfNodes == Constant.ErrorCode.Register.COMMAND_ERROR)) {
                for (int i = 0; i < numOfNodes; i++) {
                    ip = st.nextToken();
                    port = Integer.parseInt(st.nextToken());
                    nodes.add(new Cred(ip, port, null));
                }
            }
            RegisterRes registerRes = new RegisterRes(numOfNodes, nodes);
            return registerRes;

        } else if (command.equals(Constant.Action.UNREGISTER)) {
            String ip = st.nextToken();
            int port = Integer.parseInt(st.nextToken());
            String username = st.nextToken();
            Cred unregUserCredentials = new Cred(ip, port, username);
            return new UnregisterReq(unregUserCredentials);

        } else if (command.equals(Constant.Action.UNREGISTER_OK)) {
            int value = Integer.parseInt(st.nextToken());
            return new UnregisterRes(value);
        }

        return null;
    }
}
