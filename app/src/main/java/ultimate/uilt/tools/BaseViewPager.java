package ultimate.uilt.tools;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Ivory on 2016/10/2.
 */
public class BaseViewPager extends ViewPager {
    private boolean nosroll=true;
    public BaseViewPager(Context context){
        super(context);
    }
    public BaseViewPager(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    public void scrollTo(int x,int y){
        super.scrollTo(x,y);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0){
        if(nosroll)
            return false;
        else
            return super.onTouchEvent(arg0);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0)
    {
        if(nosroll)
            return false;
        else
            return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item,boolean smoothScroll){
        super.setCurrentItem(item,smoothScroll);
    }

    @Override
    public void setCurrentItem(int item){
        super.setCurrentItem(item);
    }

}
