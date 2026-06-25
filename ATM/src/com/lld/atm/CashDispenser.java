package com.lld.atm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CashDispenser {
    // Thread-safe inventory map. Key: Denomination Value, Value: Number of notes
    private final Map<Integer, Integer> inventory = new ConcurrentHashMap<>();
    
    // We store denominations in descending order to fulfill the "largest first" requirement
    private final int[] denominations = {100, 50, 20, 10};

    public CashDispenser() {
        // Initialize inventory with zero notes
        for (int denom : denominations) {
            inventory.put(denom, 0);
        }
    }

    // Utility to stock the ATM with cash
    public void loadCash(int denomination, int count) {
        inventory.put(denomination, inventory.getOrDefault(denomination, 0) + count);
    }

    public int getTotalCash() {
        return inventory.entrySet().stream()
                .mapToInt(e -> e.getKey() * e.getValue())
                .sum();
    }

    /**
     * Attempts to dispense cash. 
     * synchronized ensures two concurrent withdrawals don't double-spend physical notes.
     */
    public synchronized Map<Integer, Integer> dispenseCash(int amount) {
        if (amount % 10 != 0) {
            throw new CashDispenseException("Amount must be a multiple of 10.");
        }
        if (amount > getTotalCash()) {
            throw new CashDispenseException("Insufficient cash in the ATM.");
        }

        // 1. Validation Phase (Calculate breakdown without mutating inventory)
        Map<Integer, Integer> toDispense = new HashMap<>();
        int remainingAmount = amount;

        for (int denom : denominations) {
            if (remainingAmount == 0) break;

            int availableNotes = inventory.getOrDefault(denom, 0);
            int neededNotes = remainingAmount / denom;
            
            // We can only take as many notes as we actually have
            int notesToTake = Math.min(availableNotes, neededNotes);

            if (notesToTake > 0) {
                toDispense.put(denom, notesToTake);
                remainingAmount -= (notesToTake * denom);
            }
        }

        // If we still have a remaining amount, we don't have the right mix of bills
        if (remainingAmount > 0) {
            throw new CashDispenseException("Unable to dispense the exact amount with available denominations.");
        }

        // 2. Commit Phase (Actually deduct from inventory)
        for (Map.Entry<Integer, Integer> entry : toDispense.entrySet()) {
            int denom = entry.getKey();
            int countToDeduct = entry.getValue();
            inventory.put(denom, inventory.get(denom) - countToDeduct);
        }

        // Return the breakdown so the hardware knows what to physically push out
        return toDispense; 
    }
}
