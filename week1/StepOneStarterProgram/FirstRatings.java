
/**
 * 在这里给出对类 FirstRatings 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movies = new ArrayList<Movie>();
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
    
    public int num(ArrayList<Movie> movies, Filter f){
        int num = 0;
        for (Movie movie : movies){
            if (f.satisfies(movie)){
                num++;
            }
        }
        return num;
    }
    
    public int num(ArrayList<Rater> movies, rFilter f){
    int num = 0;
    for (Rater movie : movies){
        if (f.satisfies(movie)){
            num++;
        }
    }
    return num;
    }
    
    
    public int num(mFilter f){
        int num = 0;
        int max = 0;
        HashMap<String, ArrayList<String>> map = f.getMap();
        HashMap<Integer, ArrayList<String>> list = new HashMap<Integer,ArrayList<String>>();
        for (String key : map.keySet()){
            int size = map.get(key).size();
            //System.out.println(map.get(key));
            if (!list.containsKey(size)){
                ArrayList<String> name = new ArrayList<String>();
                name.add(key);
                list.put(size, name);
            }
            else{
                list.get(size).add(key);
            }
            if (size > max){
                max = size;
                num++;
            }
        }
        System.out.println("max is " + max + " name is "+list.get(max));
        System.out.println("only one are " + list.get(1).size());
        return list.get(num).size();
    }
    public ArrayList<Rater> loadRaters(String filename){
        ArrayList<Rater> raters = new ArrayList<Rater>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> movielist = new ArrayList<String>();
        HashMap<Integer, String> map = new HashMap<Integer, String>();
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
    
    public void testLoadMovies(){
        //ArrayList<Movie> movies = loadMovies("data/ratedmovies_short.csv");
        ArrayList<Movie> movies = loadMovies("data/ratedmoviesfull.csv");
        //System.out.println(movies);
        Filter f = new genreFilter("Comedy");
        System.out.println(num(movies, f));
        f = new minuteFilter(150);
        System.out.println(num(movies, f));
        mFilter mf = new theMostMovieFilter(movies);
        System.out.println(num(mf));
        mf = new theMostDirectorFilter(movies);
        System.out.println(num(mf));
    }
    
    public void testLoadRaters(){
        //ArrayList<Rater> raters = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raters = loadRaters("data/ratings.csv");
        HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
        int max = 0;
        for (Rater rater : raters){
            int ratings = rater.numRatings();
            System.out.println(rater.getID() +" "+ ratings +" "+ rater.getItemsRated());
            if (map.containsKey(ratings)){
                map.get(ratings).add(rater.getID());
            }
            else{
                ArrayList<String> id = new ArrayList<String>();
                id.add(rater.getID());
                map.put(ratings, id);
            }
            if (ratings > max){
                max = ratings;
            }
        }
        System.out.println(map.get(max));
        System.out.println("num of raters is " + map.get(max).size() + " ratings is " + max);
        rFilter f = new raterFilter("193");
        System.out.println(num(raters, f));
        f = new movieFilter("1798709");
        System.out.println(num(raters, f));
    }
}
