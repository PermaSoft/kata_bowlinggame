package io.permasoft.kata.bowlinggame;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

/**
 * The game consists of 10 frames. In each frame the player has two rolls to knock down 10 pins. The score for the frame is the total number of pins knocked down, plus bonuses for strikes and spares.
 * <p>
 * A spare is when the player knocks down all 10 pins in two rolls. The bonus for that frame is the number of pins knocked down by the next roll.
 * <p>
 * A strike is when the player knocks down all 10 pins on his first roll. The frame is then completed with a single roll. The bonus for that frame is the value of the next two rolls.
 * <p>
 * In the tenth frame a player who rolls a spare or strike is allowed to roll the extra balls to complete the frame. However no more than three balls can be rolled in tenth frame.
 * <p>
 * Requirements
 * Write a class Game that has two methods
 * <p>
 * void roll(int) is called each time the player rolls a ball. The argument is the number of pins knocked down.
 * int score() returns the total score for that game.
 */
class BowlingGameTest {
    @Test
    void should_return_0_when_no_pin_knocked_down() {
        var game = new Game();
        game.roll(0);
        assertThat(game.score()).isEqualTo(0);
    }

    @Test
    void should_return_1_when_1_pin_knocked_down() {
        var game = new Game();
        game.roll(1);
        assertThat(game.score()).isEqualTo(1);
    }

    @TestFactory
    Stream<DynamicTest> should_return_sum_when_2_rolls() {
        return Stream.of(
                dynamicTest("addition", () -> doTestIt(2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)),
                dynamicTest("game of ones with workaround", () -> doTestIt(20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0)),
                dynamicTest("game of ones", () -> doTestIt(20, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)));
    }

    @TestFactory
    Stream<DynamicTest> should_spare_when_2_rolls_in_row_give_10() {
        return Stream.of(
                dynamicTest("simple spare", () -> doTestIt((7 + 3 + 1 + 1), 7, 3, 1, 0, 0, 0, 0, 0, 0, 0)),
                dynamicTest("simple spare and no more bonuses", () -> doTestIt(((7 + 3 + 1) + 1 + 2), 7, 3, 1, 2, 0, 0, 0, 0, 0, 0, 14)),
                dynamicTest("no spare when 10 is split in two turns", () -> doTestIt(((1 + 4) + (6 + 3) + (5)), 1, 4, 6, 3, 5, 0, 0, 0, 0, 0)),
                dynamicTest("spare in middle game", () -> doTestIt(((7 + 1) + (4 + 6 + 3) + (3 + 5)), 7, 1, 4, 6, 3, 5, 0, 0, 0, 0)));
    }

    @TestFactory
    Stream<DynamicTest> should_strike_when_first_roll_in_row_give_10() {
        return Stream.of(
                dynamicTest("simple strike", () -> doTestIt(((10 + 3 + 1) + (3 + 1)), 10, 3, 1, 0, 0, 0, 0, 0, 0, 0)),
                dynamicTest("simple strike and no more bonuses", () -> doTestIt(((10 + 3 + 1) + (3 + 1) + (2)), 10, 3, 1, 2, 0, 0, 0, 0, 0, 0)),
                dynamicTest("strike is not a spare", () -> doTestIt(((0 + 10 + 6) + (6 + 3) + (5)), 0, 10, 6, 3, 5, 0, 0, 0, 0, 0)),
                dynamicTest("strike in mid game", () -> doTestIt(((7 + 1) + (10 + 6 + 3) + (6 + 3) + (5)), 7, 1, 10, 6, 3, 5, 0, 0, 0, 0)),
                dynamicTest("two strike in a row", () -> doTestIt(((7 + 1) + (10 + 10 + 3) + (10 + 3 + 5) + (3 + 5) + (4)), 7, 1, 10, 10, 3, 5, 4, 0, 0, 0)),
                dynamicTest("perfect game", () -> doTestIt(200, 10, 10, 10, 10, 10, 10, 10, 10)));
    }

    private static void doTestIt(int expectedScore, int... rolls) {
        var game = new Game();
        for (int i = 0; i < rolls.length - 1; i++) {
            game.roll(rolls[i]);
        }
        assertThat(game.score()).isEqualTo(expectedScore);
    }
}
