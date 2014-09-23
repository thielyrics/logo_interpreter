package editor;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.*;

//import com.sun.xml.internal.ws.api.server.Container;

import edu.hendrix.earley.MalformedGrammarException;
import parser.*;


public class CommandLine extends JPanel {
	private JLabel comm;
	private JTextField commandField;
	private JButton submit;
	private Parser parser;
	private EditorPane pane;
	
	public CommandLine(EditorPane p, Parser par) {
		super();
		
		comm = new JLabel("Enter a command:");
		comm.setBounds(50, 100, 100, 150);
		commandField = new JTextField(20);
		submit = new JButton("Submit");
		submit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String message = parser.parse(commandField.getText());
					if(message.equals("")) { pane.sendToConsole(commandField.getText()); }
					else { 
						pane.sendToConsole(commandField.getText());
						pane.sendToConsole(">> " + message); 
					}
					commandField.setText("");
					pane.repaint();
					
				} catch(Exception error) {
					pane.sendToConsole(error.toString());
				}
			}
		});
		parser = par;
		pane = p;
		
		add(comm, BorderLayout.WEST);
		add(commandField, BorderLayout.CENTER);
		add(submit, BorderLayout.EAST);
	}
}
