package parsing.entities;

public enum Season {
    FALL("Fall"),
    SPRING("Spring"),
    SUMMER("Summer");

    private final String seasonName;

    private Season(String name) {
        this.seasonName = name;
    }

    public String getSeasonName() {
        return this.seasonName;
    }
}
