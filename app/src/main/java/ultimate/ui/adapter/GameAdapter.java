package ultimate.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.panker.ultimate.R;
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
            viewHolder.tittle=(TextView)view.findViewById(R.id.game_title);
            viewHolder.date= (TextView)view.findViewById(R.id.game_date);
            viewHolder.img = (ImageView)view.findViewById(R.id.game_img);
            viewHolder.state = (TextView)view.findViewById(R.id.game_state);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder=(GameAdapter.ViewHolder)view.getTag();
        }
        viewHolder.tittle.setText(game.getGame_tittle());
        viewHolder.date.setText(game.getGame_date());
        viewHolder.img.setImageBitmap(game.getGame_img());
        viewHolder.state.setText(game.getGame_state());
        return view;
    }
    //内部类，用以提高读取效率。
    class ViewHolder {
        TextView tittle;
        TextView date;
        ImageView img;
        TextView state;
    }
}
