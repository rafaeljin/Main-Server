package edu.thu.rlab.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import edu.thu.rlab.pojo.Course;

public class MainServer {

	/**
     * Runs the server.
     */
    public static void main(String[] args) throws IOException {
    	
        DBEditor dbeditor = new DBEditor("mainDB","root","rlab");
        dbeditor.connect();
        ResourceManager rmanager = new ResourceManager();
        
        // Listening for connection from 
        ServerSocket listener = new ServerSocket(9091,50); // modify: max connections = 50
        int i = 0;
        try {
            while (true) {
                Socket socket = listener.accept();
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Course cs = (Course) inputStream.readObject();
                try {
                    System.out.print(new Date().toString());
                    System.out.print(cs.getName());
                } finally {
                    socket.close();
                }
            }
        } catch (ClassNotFoundException e) {
			System.out.print("class not found");
			e.printStackTrace();
		}
        finally {
            listener.close();
        }
        /*public ChatClient() {

        // Layout GUI
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, "North");
        frame.getContentPane().add(new JScrollPane(messageArea), "Center");
        frame.pack();

        // Add Listeners
        textField.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    
    private String getServerAddress() {
        return JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Chatter",
            JOptionPane.QUESTION_MESSAGE);
    }

    
    private String getName() {
        return JOptionPane.showInputDialog(
            frame,
            "Choose a screen name:",
            "Screen name selection",
            JOptionPane.PLAIN_MESSAGE);
    }


    private void run() throws IOException {

        // Make connection and initialize streams
        String serverAddress = getServerAddress();
        Socket socket = new Socket(serverAddress, 9001);
        in = new BufferedReader(new InputStreamReader(
            socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        // Process all messages from server, according to the protocol.
        while (true) {
            String line = in.readLine();
            if (line.startsWith("SUBMITNAME")) {
                out.println(getName());
            } else if (line.startsWith("NAMEACCEPTED")) {
                textField.setEditable(true);
            } else if (line.startsWith("MESSAGE")) {
                messageArea.append(line.substring(8) + "\n");
            }
        }
    }

    
    public static void main(String[] args) throws Exception {
        ChatClient client = new ChatClient();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }*/
    }
    
}
