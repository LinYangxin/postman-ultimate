package ultimate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.postman.ultimate.R;
import ultimate.bean.Game;

import java.util.List;

/**
 * Created by user on 2017/4/27.
 */

public class GameAdapter extends ArrayAdapter<Game> {
    private int resourceId;

    public GameAdapter(Context context, int textViewResourceId, List<Game> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Game game = getItem(position);
        View view;
        GameAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder=new GameAdapter.ViewHolder();
            viewHolder.title=(TextView)view.findViewById(R.id.tvTitle);
            viewHolder.date= (TextView)view.findViewById(R.id.tvDate);
            viewHolder.img = (ImageView)view.findViewById(R.id.imgGame);
            viewHolder.state = (TextView)view.findViewById(R.id.tvState);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder=(GameAdapter.ViewHolder)view.getTag();
        }
        viewHolder.title.setText(game.getTitle());
        viewHolder.date.setText(game.getDate());
        viewHolder.img.setImageBitmap(game.getImg());
        viewHolder.state.setText(game.getState());
        return view;
    }
    //内部类，用以提高读取效率。
    class ViewHolder {
        TextView title;
        TextView date;
        ImageView img;
        TextView state;
    }
}
