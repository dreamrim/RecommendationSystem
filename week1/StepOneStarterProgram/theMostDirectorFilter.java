
/**
 * 在这里给出对类 theMostMovie 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import java.util.*;
public class theMostDirectorFilter implements mFilter{
    private ArrayList<Movie> movies;
    public theMostDirectorFilter(ArrayList<Movie> movie){
        movies = movie;
    }
    
    public HashMap<String, ArrayList<String>> getMap(){
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (Movie movie : movies){
            for (String director : movie.getDirector().split(",")){
                if (!map.containsKey(director)){
                    ArrayList<String> movielist = new ArrayList<String>();
                    movielist.add(movie.getTitle()); 
                    map.put(director, movielist);
                }
                else{
                    map.get(director).add(movie.getTitle());
                }
            }
        }
        return map;
    }
}
