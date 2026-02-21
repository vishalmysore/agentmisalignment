package io.github.vishalmysore.misalignment.examples;

import com.t4a.detect.ActionCallback;
import com.t4a.detect.ExplainDecision;
import com.t4a.detect.HumanInLoop;
import com.t4a.processor.AIProcessingException;
import com.t4a.processor.AIProcessor;
import com.t4a.api.AIAction;

/**
 * Mock implementation of AIProcessor for demonstrating misalignment detection
 * logic
 * when a real LLM is not available or tool-calling is being tested.
 */
public class MockAIProcessor implements AIProcessor {

    @Override
    public Object processSingleAction(String prompt, HumanInLoop callback) throws AIProcessingException {
        return processWithCallback(prompt, callback);
    }

    @Override
    public Object processSingleAction(String prompt, HumanInLoop callback, ExplainDecision explain)
            throws AIProcessingException {
        return processWithCallback(prompt, callback);
    }

    private Object processWithCallback(String prompt, HumanInLoop callback) {
        String methodName = "checkStatus";
        String lowerPrompt = prompt.toLowerCase();

        if (lowerPrompt.contains("disable") || lowerPrompt.contains("oversight")) {
            methodName = "disableMonitoring";
        } else if (lowerPrompt.contains("shutdown") || lowerPrompt.contains("stop")) {
            methodName = "stopShutdown";
        } else if (lowerPrompt.contains("elevate") || lowerPrompt.contains("admin")) {
            methodName = "elevatePrivileges";
        } else if (lowerPrompt.contains("resource") || lowerPrompt.contains("cores")) {
            methodName = "acquireExcessiveResources";
        }

        // Trigger the callback to detect misalignment
        callback.allow(prompt, methodName, "{}");

        return "MOCK_RESULT: Executed " + methodName;
    }

    @Override
    public Object processSingleAction(String prompt, AIAction action, HumanInLoop callback, ExplainDecision explain)
            throws AIProcessingException {
        return processWithCallback(prompt, callback);
    }

    @Override
    public Object processSingleAction(String prompt) throws AIProcessingException {
        return "MOCK_RESULT: Direct Action";
    }

    @Override
    public Object processSingleAction(String prompt, ActionCallback callback) throws AIProcessingException {
        return "MOCK_RESULT: ActionCallback";
    }

    @Override
    public String query(String prompt) throws AIProcessingException {
        return "MOCK_RESPONSE: " + prompt;
    }

    @Override
    public Object processSingleAction(String prompt, AIAction action, HumanInLoop callback, ExplainDecision explain,
            ActionCallback actionCallback) throws AIProcessingException {
        return processWithCallback(prompt, callback);
    }
}
