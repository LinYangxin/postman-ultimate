package ultimate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import ultimate.bean.News;
import com.example.panker.ultimate.R;

import java.util.List;

/**
 * Created by user on 2016/7/13.
 * 资讯界面的ListView适配器
 */
public class news_adapter extends ArrayAdapter<News> {
    private int resourceId;

    public news_adapter(Context context, int textViewResourceId, List<News> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder=new ViewHolder();
            viewHolder.tittle=(TextView)view.findViewById(R.id.tittle);
            viewHolder.sumarize= (TextView)view.findViewById(R.id.sumarize);
            viewHolder.img = (ImageView)view.findViewById(R.id.news_img);
            viewHolder.date = (TextView)view.findViewById(R.id.date);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tittle.setText(news.getTittle());
        viewHolder.sumarize.setText(news.getSumarize());
        viewHolder.img.setImageBitmap(news.getNews_img());
        viewHolder.date.setText(news.getNews_date());
        return view;
    }
//内部类，用以提高读取效率。
    class ViewHolder {
        TextView tittle;
        TextView sumarize;
        ImageView img;
        TextView date;
    }
}
