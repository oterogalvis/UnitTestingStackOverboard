package com.teamtreehouse.techdegree.overboard.model;

import com.teamtreehouse.techdegree.overboard.exc.AnswerAcceptanceException;
import com.teamtreehouse.techdegree.overboard.exc.VotingException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by jorgeotero on 4/19/17.
 */
public class UserTest {

    private Board board;
    private User userA;
    private User userB;
    private Question questionA;
    private Answer answerB;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        board = new Board("Test");
        userA = board.createUser("userA");
        userB = board.createUser("userB");
        questionA = userA.askQuestion("questionA");
        answerB = userB.answerQuestion(questionA, "answerB");
    }

    @Test
    public void reputationGoUp5WhenQuestionUpvoted() throws Exception {
        userB.upVote(questionA);

        assertEquals("Verify that the questioner’s reputation goes up by 5 points if their question is upvoted.",
                5, userA.getReputation());
    }

    @Test
    public void reputationGoUp10WhenAnswerUpvoted() throws Exception {
        userA.upVote(answerB);

        assertEquals("Verify that the answerer’s reputation goes up by 10 points if their answer is upvoted.",
                10, userB.getReputation());
    }

    @Test
    public void reputationGoUp15WhenAnswerAccepted() throws Exception {
        userA.acceptAnswer(answerB);

        assertEquals("Verify that having an answer accepted gives the answerer a 15 point reputation boost.",
                15, userB.getReputation());
    }

    @Test
    public void reputationGoDown1WhenAnswerDownvoted() throws Exception {
        userA.downVote(answerB);

        assertEquals("Verify down-voting costs 1 point",
                -1, userB.getReputation());
    }

    @Test
    public void reputationDoNotChangeWhenQuestionDownvoted() throws Exception {
        userB.downVote(questionA);

        assertEquals("Verify down-voting of questions affects nothing.",
                0, userA.getReputation());
    }

    @Test
    public void voteUpQuestionByQuestioner() throws Exception {
        expectedException.expect(VotingException.class);
        expectedException.expectMessage("You cannot vote for yourself!");

        userA.upVote(questionA);
    }

    @Test
    public void voteDownQuestionByQuestioner() throws Exception {
        expectedException.expect(VotingException.class);
        expectedException.expectMessage("You cannot vote for yourself!");

        userA.downVote(questionA);
    }

    @Test
    public void voteUpAnswerByAnswerer() throws Exception {
        expectedException.expect(VotingException.class);
        expectedException.expectMessage("You cannot vote for yourself!");

        userB.upVote(answerB);
    }

    @Test
    public void voteDownAnswerByAnswerer() throws Exception {
        expectedException.expect(VotingException.class);
        expectedException.expectMessage("You cannot vote for yourself!");

        userB.downVote(answerB);
    }

    @Test
    public void acceptAnswerFromAnotherAuthorQuestion() throws Exception {
        expectedException.expect(AnswerAcceptanceException.class);
        expectedException.expectMessage(String.format("Only %s can accept this answer as it is their question", userA.getName()));

        userB.acceptAnswer(answerB);
    }
}
