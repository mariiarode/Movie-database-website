package model;

public class MVmovie {

    private int id;
    private String title;
    private String producer;
    private String actors;
    private String summary;
    private byte[] poster;
    private int releaseYear;
    private String genre;

    public MVmovie(int id, String title, String producer, String actors, String summary, byte[] poster, int releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.producer = producer;
        this.actors = actors;
        this.summary = summary;
        this.poster = poster;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    public MVmovie() {
        // Default constructor
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getProducer() { return producer; }
    public void setProducer(String producer) { this.producer = producer; }

    public String getActors() { return actors; }
    public void setActors(String actors) { this.actors = actors; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public byte[] getPoster() { return poster; }
    public void setPoster(byte[] poster) { this.poster = poster; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    @Override
    public String toString() {
        return "MVmovie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", producer='" + producer + '\'' +
                ", actors='" + actors + '\'' +
                ", summary='" + summary + '\'' +
                ", releaseYear=" + releaseYear +
                ", genre='" + genre + '\'' +
                '}';
    }
}
