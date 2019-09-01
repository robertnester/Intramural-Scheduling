package com.company.DTO;

public class GameDayDTO {

    private int id;
    private GameDTO game1;
    private GameDTO game2;
    private EligibleDayDTO elDayDTO;

    public GameDayDTO(GameDTO game1, GameDTO game2, EligibleDayDTO elDayDTO) {
        this.game1 = game1;
        this.game2 = game2;
        this.elDayDTO = elDayDTO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameDTO getGame1() {
        return game1;
    }

    public void setGame1(GameDTO game1) {
        this.game1 = game1;
    }

    public GameDTO getGame2() {
        return game2;
    }

    public void setGame2(GameDTO game2) {
        this.game2 = game2;
    }

    public EligibleDayDTO getElDayDTO() {
        return elDayDTO;
    }

    public void setElDayDTO(EligibleDayDTO elDayDTO) {
        this.elDayDTO = elDayDTO;
    }

}
