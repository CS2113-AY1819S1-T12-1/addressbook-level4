package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.model.Model;

/**
 * Holds all assertions used for comments testing
 */
public class CommentAssertion {

    /**
     * Testing all events in the model are the same
     * @param expectedModel
     * @param actualModel
     */
    public void assertSuccessModel(Model expectedModel, Model actualModel) {
        int expectedSize = expectedModel.getFilteredEventList().size();
        int actualSize = actualModel.getFilteredEventList().size();
        assertEquals(expectedSize, actualSize);
        for (int index = 0; index < actualSize; index++) {
            assertEquals(expectedModel.getFilteredEventList().get(index),
                    actualModel.getFilteredEventList().get(index));
        }
    }
}
