package ultimate.bean;

/**
 * Created by user on 2017/3/1.
 * 轮播图实体类，用以设置和获取轮播图的图片和链接
 */

public class Rollpage {
    private String imgs;
    private String url;
    public Rollpage(String url,String img){
        this.imgs = img;
        this.url = url;
    }
    public String getImgs() {
        return imgs;
    }

    public String getUrl() {
        return url;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
