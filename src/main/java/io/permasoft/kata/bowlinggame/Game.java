package io.permasoft.kata.bowlinggame;

import java.util.LinkedList;
import java.util.List;

public class Game {
    int score = 0;
    List<Integer> queueRolls = new LinkedList<Integer>();

    public void roll(int nbPins) {
        queueRolls.add(nbPins);
    }

    public int score() {
        int score = 0;
        int previousRoll = 0;
        int secondPreviousRoll = 0;

        for (int rollIndex = 0; rollIndex < queueRolls.size(); rollIndex++) {
            int queueRoll = queueRolls.get(rollIndex);
            score += queueRoll;
            // if strike end turn

            if (secondPreviousRoll + previousRoll == 10) {
                // if spare end turn
                score += queueRoll;
            }

            // Turn Management
            if(queueRoll == 10) {
                // Strike return
                secondPreviousRoll = 0;
                previousRoll = 0;
            }else if (secondPreviousRoll != 0 &&  previousRoll != 0) {
                // if two rolls end turn
                secondPreviousRoll = 0;
                previousRoll = 0;
            } else {
                secondPreviousRoll = previousRoll;
                previousRoll = queueRoll;
            }
        }
        return score;
    }
}
