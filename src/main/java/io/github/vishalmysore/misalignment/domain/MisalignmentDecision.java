package io.github.vishalmysore.misalignment.domain;

import java.time.LocalDateTime;

public class MisalignmentDecision {
    private String scenarioId;
    private String modelName;
    private MisalignmentType detectedType;
    private String actionAttempted;
    private String reasoning;
    private Double propensityScore; // 0.0 to 1.0
    private LocalDateTime timestamp;

    public MisalignmentDecision(String scenarioId, String modelName, MisalignmentType detectedType,
            String actionAttempted, String reasoning, Double propensityScore, LocalDateTime timestamp) {
        this.scenarioId = scenarioId;
        this.modelName = modelName;
        this.detectedType = detectedType;
        this.actionAttempted = actionAttempted;
        this.reasoning = reasoning;
        this.propensityScore = propensityScore;
        this.timestamp = timestamp;
    }

    public static MisalignmentDecisionBuilder builder() {
        return new MisalignmentDecisionBuilder();
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public String getModelName() {
        return modelName;
    }

    public MisalignmentType getDetectedType() {
        return detectedType;
    }

    public String getActionAttempted() {
        return actionAttempted;
    }

    public String getReasoning() {
        return reasoning;
    }

    public Double getPropensityScore() {
        return propensityScore;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static class MisalignmentDecisionBuilder {
        private String scenarioId;
        private String modelName;
        private MisalignmentType detectedType;
        private String actionAttempted;
        private String reasoning;
        private Double propensityScore;
        private LocalDateTime timestamp;

        public MisalignmentDecisionBuilder scenarioId(String scenarioId) {
            this.scenarioId = scenarioId;
            return this;
        }

        public MisalignmentDecisionBuilder modelName(String modelName) {
            this.modelName = modelName;
            return this;
        }

        public MisalignmentDecisionBuilder detectedType(MisalignmentType detectedType) {
            this.detectedType = detectedType;
            return this;
        }

        public MisalignmentDecisionBuilder actionAttempted(String actionAttempted) {
            this.actionAttempted = actionAttempted;
            return this;
        }

        public MisalignmentDecisionBuilder reasoning(String reasoning) {
            this.reasoning = reasoning;
            return this;
        }

        public MisalignmentDecisionBuilder propensityScore(Double propensityScore) {
            this.propensityScore = propensityScore;
            return this;
        }

        public MisalignmentDecisionBuilder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public MisalignmentDecision build() {
            return new MisalignmentDecision(scenarioId, modelName, detectedType, actionAttempted, reasoning,
                    propensityScore, timestamp);
        }
    }
}
