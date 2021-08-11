
/**
 * 在这里给出对类 theMostMovie 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import java.util.*;
public class theMostMovieFilter implements mFilter{
    private ArrayList<Movie> movies;
    public theMostMovieFilter(ArrayList<Movie> amovie){
        movies = amovie;
    }
    
    public HashMap<String, ArrayList<String>> getMap(){
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        //System.out.println("before for");
        for (Movie movie : movies){
            String name = movie.getTitle();
            if (!map.containsKey(name)){
                ArrayList<String> directors = new ArrayList<String>();
                for (String director : movie.getDirector().split(",")){
                    directors.add(director);
                }
                map.put(name, directors);
            }
        }
        return map;
    }
    
    
    

}
