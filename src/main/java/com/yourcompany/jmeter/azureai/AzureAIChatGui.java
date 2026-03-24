package com.yourcompany.jmeter.azureai;

import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.testelement.TestElement;
import javax.swing.*;
import java.awt.*;

public class AzureAIChatGui extends AbstractConfigGui {
    private static final long serialVersionUID = 1L;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public AzureAIChatGui() {
        init();
    }

    @Override
    public String getLabelResource() {
        return "azure_ai_assistant_custom"; // Changed: No spaces!
    }

    @Override
    public String getStaticLabel() {
        return "Azure AI JMeter Assistant"; // Defines the name in the JMeter UI menu
    }

    @Override
    public TestElement createTestElement() {
        AzureAIChatElement element = new AzureAIChatElement();
        modifyTestElement(element);
        return element;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        super.configureTestElement(element);
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));
        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Send");
        
        // Actions
        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        String text = inputField.getText();
        if(!text.trim().isEmpty()) {
            chatArea.append("🤖 You: " + text + "\n");
            inputField.setText("");
            sendButton.setEnabled(false);
            
            // Run the API call in a background thread so the JMeter UI doesn't freeze
            new Thread(() -> {
                String response = AzureOpenAIService.getChatResponse(text);
                SwingUtilities.invokeLater(() -> {
                    chatArea.append("✨ Azure AI: " + response + "\n\n");
                    sendButton.setEnabled(true);
                });
            }).start();
        }
    }
}