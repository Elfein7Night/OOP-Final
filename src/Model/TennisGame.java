package Model;

import java.util.List;
import java.util.stream.Stream;

public class TennisGame extends Game {
    private final int alternativeRounds;

    public TennisGame(String _player1, String _player2) {
        super(_player1, _player2);
        rounds = 5;
        alternativeRounds = 3;
    }

    @Override
    public String playAndGetWinner(List<Integer> p1Scores, List<Integer> p2Scores) throws MyException {
        if (p1Scores.size() != p2Scores.size())
            throw new MyException("uneven size of rounds lists");
        if (p1Scores.size() != rounds && p1Scores.size() != alternativeRounds)
            throw new MyException("Not All Rounds Played!");
        int p1Wins = 0, p2Wins = 0;
        for (int i = 0; i < p1Scores.size(); i++) {
            if (p1Scores.get(i) > p2Scores.get(i))
                p1Wins++;
            else if (p2Scores.get(i) > p1Scores.get(i))
                p2Wins++;
        }
        if (p1Scores.size() == alternativeRounds) {
            if (p1Wins - p2Wins >= 3)
                return player1;
            if (p2Wins - p1Wins >= 3)
                return player2;
        }
        if (p1Scores.size() == rounds) {
            if (p1Wins > p2Wins)
                return player1;
            if (p2Wins > p1Wins)
                return player2;
        }
        throw new MyException("OVERTIME_NEEDED");
    }

}
