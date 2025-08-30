package com.customer.management.service.enums;

/**
 * CustomerStatus Enum:
 * - Represents the current status of a customer in the system.
 * Values:
 * - ACTIVE → Customer is active and can access services.
 * - INACTIVE → Customer is deactivated (soft delete or temporarily blocked).
 * Purpose:
 * Helps manage customer availability without permanently deleting their data.
 */
public enum CustomerStatus {
    ACTIVE,
    INACTIVE
}
