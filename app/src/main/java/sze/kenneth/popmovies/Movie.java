package sze.kenneth.popmovies;

public class Movie {
    private int id;
    private String title;
    private String overview;
    private String posterPath;
    private String releaseDate;
    private double vote_average;

    public Movie (int id, String title, String overview, String posterPath, String releaseDate, double vote_average)
    {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.vote_average = vote_average;
    }

    public String getTitle() { return title; }
    public String getPosterPath() { return posterPath; }
}
