package ultimate.uilt.interfaces;

import java.util.List;

import ultimate.bean.News;

/**
 * Created by user on 2018/1/11.
 */

public interface HomeListView extends MvpView {

    void refresh(List<News> data);

    void loadMore(List<News> data);

}