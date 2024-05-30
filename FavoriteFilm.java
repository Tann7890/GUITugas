package guiii;
public class FavoriteFilm {
    private int id;
    private String name;
    private int age;
    private String favoriteMovie;
    private String favoriteCharacter;

    public FavoriteFilm(String name, int age, String favoriteMovie, String favoriteCharacter) {
        this.name = name;
        this.age = age;
        this.favoriteMovie = favoriteMovie;
        this.favoriteCharacter = favoriteCharacter;
    }

    public FavoriteFilm(int id, String name, int age, String favoriteMovie, String favoriteCharacter) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.favoriteMovie = favoriteMovie;
        this.favoriteCharacter = favoriteCharacter;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFavoriteMovie() {
        return favoriteMovie;
    }

    public void setFavoriteMovie(String favoriteMovie) {
        this.favoriteMovie = favoriteMovie;
    }

    public String getFavoriteCharacter() {
        return favoriteCharacter;
    }

    public void setFavoriteCharacter(String favoriteCharacter) {
        this.favoriteCharacter = favoriteCharacter;
    }
}
