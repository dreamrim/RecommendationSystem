
/**
 * 在这里给出对类 genreFilter 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class genreFilter implements Filter{
    private String genre;
    public genreFilter(String aGenre){
        genre = aGenre;
    }
    
    public  boolean satisfies(Movie movie){
        if (movie.getGenres().indexOf(genre) != -1){
            return true;
        }
        return false;
    }
    
    
    
    public String getName(){
        return "the genre " + genre; 
    }
}
