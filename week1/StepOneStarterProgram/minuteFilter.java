
/**
 * 在这里给出对类 minuteFilter 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class minuteFilter implements Filter{
    private int time;
    public minuteFilter(int atime){
        time = atime;
    }
    
    public  boolean satisfies(Movie movie){
        if (movie.getMinutes() > time){
            return true;
        }
        return false;
    }
    
    
    
    public String getName(){
        return "the minute " + time; 
    }

}
