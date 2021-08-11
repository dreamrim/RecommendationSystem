
/**
 * 在这里给出对类 raterFilter 的描述。
 * 
 * @作者（你的名字）
 * @版本（一个版本号或者一个日期）
 */
public class raterFilter implements rFilter{
    private String id;
    public raterFilter(String ID){
        id = ID;
    }
    
    public  boolean satisfies(Rater rater){
        if (rater.getID().equals(id)){
            System.out.println(id + " have ratings "+ rater.numRatings());
            return true;
        }
        return false;
    }
}
