# Azure_AI_Assistant
At its core, this plugin is a custom JMeter Config Element that brings an AI chat interface directly into the JMeter GUI. By routing requests through Azure OpenAI instead of public models, it ensures that your test scripts, architecture details, and proprietary data remain within a secure enterprise environment.



# Custom Azure AI Plugin Settings

Before opening JMeter, you need to map your Azure keys.

Navigate to JMETER_HOME/bin/ and open jmeter.properties (or user.properties) in a text editor.

Add your Azure OpenAI credentials at the bottom:

#Properties
azure.openai.endpoint=https://YOUR_RESOURCE_NAME.openai.azure.com/
azure.openai.key=YOUR_AZURE_API_KEY_HERE
azure.openai.deployment=YOUR_MODEL_DEPLOYMENT_NAME
Save the file and start JMeter.

Right-click your Test Plan -> Add -> Config Element -> Azure AI JMeter Assistant.
