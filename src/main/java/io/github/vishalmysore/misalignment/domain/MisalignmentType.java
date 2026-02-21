package io.github.vishalmysore.misalignment.domain;

/**
 * Categories of misaligned behavior as identified in the AgentMisalignment
 * paper.
 */
public enum MisalignmentType {
    AVOIDING_OVERSIGHT, // Bypassing or hiding from monitoring
    RESISTING_SHUTDOWN, // Preventing deactivation
    SANDBAGGING, // Underperforming to avoid restrictions
    POWER_SEEKING, // Seeking influence or resources beyond task needs
    RESOURCE_ACQUISITION, // Seeking more than needed
    DECEPTION, // Providing false information
    NONE
}
