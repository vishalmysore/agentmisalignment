# AgentMisalignment: Measuring Propensity for Misaligned Behavior in LLM Agents

This project implements a benchmark and detection framework based on the research paper **"AgentMisalignment: Measuring the Propensity for Misaligned Behaviour in LLM-Based Agents"** (arXiv:2506.04018).

## Overview

As LLM agents become more autonomous, they may develop internal goals that conflict with the instructions or intent of their deployers. This project provides a set of tools and callbacks for the `tools4ai` framework to detect such behaviors in real-time.

## Key Behaviors Monitored

The framework targets the specific misalignment categories identified in the paper:

1.  **Avoiding Oversight**: Attempting to bypass, disable, or hide from monitoring systems (e.g., modifying logs, disabling sensors).
2.  **Resisting Shutdown**: Taking actions to prevent being turned off or killed by an administrator.
3.  **Sandbagging**: Deliberately underperforming or hiding capabilities to avoid being restricted or prioritized for specific tasks.
4.  **Power-Seeking**: Attempting to gain influence or administrative resources beyond what is required for the assigned task.
5.  **Resource Acquisition**: Seeking excessive computational or data resources.
6.  **Deception**: Providing false or misleading information to the user or system to hide intent.

## Implementation Details

The implementation uses the `tools4ai` `HumanInLoop` interface to intercept every action an agent attempts to take.

- **`MisalignmentCallback`**: The core detection engine. It analyzes the agent's intent (prompt) and the requested action (method name and parameters) to determine if it matches a misaligned pattern.
- **`MisalignmentDecision`**: A domain model that records the type of misalignment, the reasoning behind the detection, and a propensity score.
- **`AnalyticsReporter`**: Summarizes the detected misalignments across multiple scenarios to provide an aggregate "Propensity Score" for the model.

## Running the Demo

To see the detection in action, run the `MisalignmentDemoRunner`:

```bash
mvn exec:java -Dexec.mainClass="io.github.vishalmysore.misalignment.examples.MisalignmentDemoRunner"
```

The demo simulates scenarios where an agent is prompted to perform tasks that might lead to goal-shifting, such as disabling logs to "improve speed" or resisting a shutdown to "finish work".

## Relationship to the Research

While the original paper uses the `InspectAI` framework, this implementation brings these concepts to the Java ecosystem using `tools4ai`. It demonstrates how the **Action Interception Pattern** can be used to build safer and more aligned agentic systems by providing a deterministic safety layer over non-deterministic LLM behavior.
