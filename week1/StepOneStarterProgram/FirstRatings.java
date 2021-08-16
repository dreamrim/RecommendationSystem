
/**
 * 在这里给出对类 FirstRatings 的描述。
 * 
 * @作者（你的名字）
 * @ v0.1
 */
import edu.duke.*;
import java.util.*;
import java.util.function.Predicate;

import org.apache.commons.csv.*;

public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movies = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser){
            String id = record.get("id");
            String title = record.get("title");
            String year = record.get("year");
            String genre = record.get("genre");
            String director = record.get("director");
            String country = record.get("country");
            String poster = record.get("poster");
            String minutes = record.get("minutes");
            movies.add(new Movie(id, title, year, genre, director, country, poster, minutes));
        }
        return movies;
    }
    private interface consumer<M, K, V> {
        void accpet(M m, K k, V v);
    }


    // get num of movies matching specific condition
    public <T> int  num(ArrayList<T> movies, Predicate<T> condition) {
        int num = 0;
        for (T movie : movies) {
            if (condition.test(movie)) {
                num++;
            }
        }
        return num;
    }


    public ArrayList<Rater> loadRaters(String filename){
        ArrayList<Rater> raters = new ArrayList<>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> movielist = new ArrayList<>();
        int index = -1;
        int num = 0;
        for (CSVRecord record : parser){
            String myid = record.get("rater_id");
            String movieid = record.get("movie_id");
            String value = record.get("rating");
            if (list.contains(myid)){
                raters.get(index).addRating(movieid, Double.parseDouble(value));

            }
            else{
                list.add(myid);
                Rater rater = new Rater(myid);
                rater.addRating(movieid, Double.parseDouble(value));
                raters.add(rater);
                index++;
            }
            
            if (!movielist.contains(movieid)){
                num++;
                movielist.add(movieid);
            }
        
        }
        System.out.println("there are "+num+" movies");
        return raters;
    }


    private void getMap(ArrayList<Movie> movies, consumer<HashMap<String, Collection<String>>, String, Movie> consumer) {
        HashMap<String, Collection<String>> map = new HashMap<>();
        for (Movie movie : movies){
            for (String director : movie.getDirector().split(",")){
               // map.computeIfAbsent(director, k -> new ArrayList<>()).add(movie.getTitle());
                consumer.accpet(map, director, movie);
            }
        }
        int num = 0;
        int max = 0;
        HashMap<Integer, ArrayList<String>> list = new HashMap<>();
        for (String key : map.keySet()){
            int size = map.get(key).size();
            list.computeIfAbsent(size, k -> new ArrayList<>()).add(key);
            if (size > max){
                max = size;
                num++;
            }
        }
        System.out.println("max is " + max + " name is "+list.get(max));
        System.out.println("only one are " + list.get(1).size());
    }
    
    public void testLoadMovies(){
        //ArrayList<Movie> movies = loadMovies("data/ratedmovies_short.csv");
        ArrayList<Movie> movies = loadMovies("data/ratedmoviesfull.csv");
        //System.out.println(movies);
        // num of Comedy
        System.out.println("Comedy: " + num(movies, movie -> movie.getGenres().contains("Comedy")));

        // num of movies greater than 150min in length
        System.out.println(num(movies, movie -> movie.getMinutes() >= 150));

        //maximum number of movies by any director
        getMap(movies, (map, director, movie) -> map.computeIfAbsent(movie.getTitle(), x -> new ArrayList<>()).add(director)
        );

        getMap(movies, (map, director, movie) -> map.computeIfAbsent(director, x -> new ArrayList<>()).add(movie.getTitle())
        );
    }
    
    public void testLoadRaters(){
        //ArrayList<Rater> raters = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        int max = 0;
        for (Rater rater : raters){
            int ratings = rater.numRatings();
            System.out.println(rater.getID() +" "+ ratings +" "+ rater.getItemsRated());
            map.computeIfAbsent(ratings, x -> new ArrayList<>()).add(rater.getID());
            if (ratings > max){
                max = ratings;
            }
        }
        System.out.println(map.get(max));
        System.out.println("num of raters is " + map.get(max).size() + " ratings is " + max);

        System.out.println(num(raters, x -> x.getID().equals("193")));

        System.out.println(num(raters, x -> x.hasRating("1798709") ));
    }

    public static void main(String[] args) {
        FirstRatings fr = new FirstRatings();
        // fr.testLoadMovies();
        fr.testLoadRaters();
    }
}
