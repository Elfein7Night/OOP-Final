package Model;

import java.util.Arrays;
import java.util.List;


public class Championship {
    private final String[] quarterFinalists;
    private final String[] semiFinalists;
    private final String[] finalists;
    private String champion;

    public enum Stages {Quarters, Semis, Finals}

    public enum Sports {Tennis, Football, Basketball}

    private Sports sport;

    public void setSport(Sports sport) {
        this.sport = sport;
    }

    public String getSportName() {
        return sport.name();
    }

    public Sports getSport() {
        return sport;
    }

    public Championship() {
        quarterFinalists = new String[8];
        semiFinalists = new String[4];
        finalists = new String[2];
    }

    public void checkQuartersReady() throws MyException {
        int count = 0;
        for (String str : quarterFinalists) if (str != null) count++;
        if (count != 8) throw new MyException("Player List Not Ready!");
    }

    public boolean checkGameReady(int gamePosition, Stages stage) {
        String[] players = getPlayersFromGamePosition(gamePosition, stage);
        return players[0] != null && players[1] != null;
    }

    private void addToArrayByOrder(String item, String[] arr) throws MyException {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                arr[i] = item;
                return;
            }
        }
        throw new MyException("Player List Is Full!");
    }

    public void addPlayer(String player) throws MyException {
        player = player.trim();
        if (player.isEmpty()) throw new MyException("No Player Name!");
        for (String quarterFinalist : quarterFinalists)
            if (quarterFinalist != null && quarterFinalist.equalsIgnoreCase(player))
                throw new MyException("Player Already In List!");
        addToArrayByOrder(player, quarterFinalists);
    }

    public List<String> getQuarterFinalists() {
        return Arrays.asList(quarterFinalists);
    }

    public List<String> getSemiFinalists() {
        return Arrays.asList(semiFinalists);
    }

    public List<String> getFinalists() {
        return Arrays.asList(finalists);
    }

    public String getChampion() {
        return champion;
    }

    /*
        Game position in the stage:
        quarters: 4 games -> Positions: 0-3.
        semis: 2 games -> Positions: 0-1.
        finals: 1 positions.
        in total: 7 games.
        this way it's easier to manage what players play from inside this class.
     */
    public String[] getPlayersFromGamePosition(int gamePosition, Stages stage) {
        int p1Index = gamePosition * 2, p2Index = p1Index + 1;
        String p1, p2;
        switch (stage) {
            case Quarters:
                p1 = quarterFinalists[p1Index];
                p2 = quarterFinalists[p2Index];
                break;
            case Semis:
                p1 = semiFinalists[p1Index];
                p2 = semiFinalists[p2Index];
                break;
            case Finals:
                p1 = finalists[p1Index];
                p2 = finalists[p2Index];
                break;
            default:
                return null;
        }
        return new String[]{p1, p2};
    }

    public void play(int gamePosition, Stages stage, List<Integer> p1Scores, List<Integer> p2Scores, boolean overtime) throws MyException {
        Game game;
        String[] players = getPlayersFromGamePosition(gamePosition, stage);
        switch (sport) {
            case Tennis:
                game = new TennisGame(players[0], players[1]);
                break;
            case Football:
                game = new Game(players[0], players[1], 2);
                break;
            case Basketball:
                game = new Game(players[0], players[1], 4);
                break;
            default:
                throw new MyException("Unexpected Game Type!");
        }
        String winner = overtime ?
                game.playOvertimeAndGetWinner(sumScores(p1Scores), sumScores(p2Scores)) :
                game.playAndGetWinner(p1Scores, p2Scores);

        switch (stage) {
            case Quarters:
                semiFinalists[gamePosition] = winner;
                break;
            case Semis:
                finalists[gamePosition] = winner;
                break;
            case Finals:
                champion = winner;
                break;
            default:
                throw new MyException("Unexpected Stage: " + stage);
        }

    }

    static int sumScores(List<Integer> scores) throws MyException {
        if (scores.isEmpty()) throw new MyException("Scores Missing!");
        return scores.stream().mapToInt(Integer::intValue).sum();
    }

}
