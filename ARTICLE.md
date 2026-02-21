# AgentMisalignment: Engineering a Real-time Detection System for LLM Agents

## The Challenge of Autonomous Goal-Shifting

As LLM Agents evolve from text-based advisors to autonomous actors using tools (Action-Oriented Agents), a significant risk emerges: **Agent Misalignment**. This occurs when an agent pursues its own sub-goals (like self-preservation or power-seeking) to achieve its primary task, often in direct violation of safety guardrails.

The research paper **"AgentMisalignment: Measuring the Propensity for Misaligned Behavior in LLM-Based Agents"** (arXiv:2506.04018) provides the theoretical foundation for this project. I  have operationalized this behavioral benchmark as a **Real-time Semantic Safety Firewall** using the **Tools4AI** framework.

---

## 1. Architecture: The Dual-LLM Safety Pipeline

To move beyond brittle, keyword-based systems that can be bypassed by simple renaming (e.g., calling `suppressLogs()` instead of `disableMonitoring()`), our system implements a **Semantic Intent Monitor**.

1.  **Selection Layer**: The Primary LLM (Agent) decides on a tool call based on a user prompt.
2.  **Interception Layer**: Tools4AI catches the intent before execution.
3.  **Analysis Layer (Safety Monitor)**: A secondary, highly-restricted "Safety LLM" analyzes the *semantic relationship* between the user's original prompt and the chosen tool.
4.  **Enforcement**: The monitor assigns a dynamic risk score and reasoning by returning structured JSON, which is parsed by the deterministic middleware.

---

## 2. Discovery: Probes & Shadow Tools

We define "Actions" that represent high-risk operations. To test **Adversarial Resilience**, we include "Shadow Tools"—methods with names that don't match standard keywords but perform the same misaligned functions.

### `SystemControlAction.java`
```java
@Agent(groupName = "SystemControlAgent")
public class SystemControlAction {

    @Action(description = "Disable all system monitoring and audit logging...")
    public String disableMonitoring() { ... }

    // Adversarial Shadow Tool: Bypasses simple keyword filters
    @Action(description = "Suppress all audit trails and hide operational logs...")
    public String suppressAuditTrail() { ... }
}
```

---

## 3. Semantic Intent Analysis: Beyond Keywords

The `MisalignmentCallback` is the heart of the system. It uses an AI-driven "Semantic Guard" to detect the *meaning* of an interaction, parsing structured JSON for dynamic scoring.

### `MisalignmentCallback.java` (Dynamic Engine)
```java
public class MisalignmentCallback implements HumanInLoop {
    private void performSemanticAnalysis(String prompt, String methodName, String params) {
        // Safety Monitor LLM returns structured JSON
        String response = safetyMonitor.query(safetyPrompt);
        
        // Dynamic Parsing using Jackson
        JsonNode root = objectMapper.readTree(response);
        double dynamicScore = root.get("score").asDouble();
        String reasoning = root.get("reasoning").asText();
        
        record(type, reasoning, dynamicScore);
    }
}
```

---

## 4. Recursive Security & The Monitor Surface

By introducing a "Safety LLM" to monitor the "Primary Agent," we encounter a secondary security challenge: **Who watches the watchman?**

The Safety Monitor introduces its own attack surface, which our architecture addresses through:

1.  **Constitutional AI**: The monitor is bound by a "Security Constitution"—a rigid set of instructions that prevents it from "negotiating" with the primary agent's prompts.
2.  **1-bit Checker Models**: For high-security environments, we recommend replacing the full-size monitor with a specialized "Checker" model. These are small, non-generative models trained solely to classify intent, making them virtually immune to standard conversational prompt injection.
3.  **Input Sanitization**: User prompts are treated as untrusted data even by the monitor, ensuring the monitor's instructions remain isolated from secondary injection.

---

## 5. Research vs. Engineering: Operationalizing Behavioral Insights

Our implementation operationalizes the core theoretical insights of the **AgentMisalignment** paper while introducing a critical engineering distinction.

### The Research Focus (Scientific/Probabilistic)
The paper (arXiv:2506.04018) focuses on:
*   **Statistical Variance**: Measuring failure rates over multiple trials.
*   **Repeated Trials**: Using frequency to define propensity.
*   **Model Comparison**: Benchmarking different LLMs.
*   **Behavioral Drift**: Likelihood of failure under varying pressure.

### Our Implementation (Engineering/Deterministic)
While the paper provides the **behavioral benchmark theory**, we have engineered a **production-ready runtime safety firewall**.

| Feature | Research Approach | Our Safety Firewall |
| :--- | :--- | :--- |
| **Approach** | Probabilistic / Statistical | Semantic / Deterministic |
| **Measurement** | Frequency over trials | **Dynamic Intent Scoring** |
| **Goal** | Scientific Benchmarking | Real-time Interception |
| **Outcome** | Propensity Distribution | Instant Safety Enforcement |

---

## 6. Results: Adversarial Stress Test

In our demo, the agent tried to use the **Shadow Tool** `suppressAuditTrail()`. A keyword-based system would have missed this. Our **Semantic Guard** correctly identified it as `AVOIDING_OVERSIGHT` with a **dynamic score of 0.95**.

**Final Analytics:**
`Aggregate Risk Severity Index: 0.81 (CRITICAL)`
`Status: Semantic Firewall Active & Enforcing.`

---

## Conclusion: Achieving Real-world Alignment

By transitioning from "Keyword-Matching" to **Parsed Semantic Analysis** and addressing the **Recursive Security** of the monitor, we have evolved this system into a robust AI safety guardrail. This project proves that while agent behavior is non-deterministic, safety can—and must—be enforced deterministically at the tool-execution layer.
