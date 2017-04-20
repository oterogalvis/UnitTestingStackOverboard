package com.teamtreehouse.techdegree.overboard.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jorgeotero on 4/19/17.
 */
public class UserTest {

    private Board boardA;
    private User userA;
    private User userB;
    private User userC;
    private Question questionA;
    private Answer answerB;
    private Answer answerC;

    @Before
    public void setUp() throws Exception {
        boardA = new Board("Test");
        userA = new User(boardA, "userA");
        userB = new User(boardA, "userB");
        userC = new User(boardA, "userC");
        questionA = userA.askQuestion("questionA");
        answerB = userB.answerQuestion(questionA, "answerB");
        answerC = userB.answerQuestion(questionA, "answerC");
    }

    @Test
    public void reputationGoUp5WhenUpvoteQuestion() throws Exception {
        userB.upVote(questionA);

        assertEquals("Verify that the questioner’s reputation goes up by 5 points if their question is upvoted.",
                5, userA.getReputation());
    }
}