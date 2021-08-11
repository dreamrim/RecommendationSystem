
/**
 * 在这里给出对类 theMostRater 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
import java.util.*;
public class movieFilter implements rFilter{
    private String id;
    public movieFilter(String ID){
        id = ID;
    }
    
    public  boolean satisfies(Rater rater){
        if (rater.getRating(id) != -1){
            return true;
        }
        return false;
    }

}
