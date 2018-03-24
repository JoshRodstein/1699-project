package group1.pittapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by billyhinard on 3/23/18.
 */

public class ScoresAdapter extends BaseAdapter {
    private static ArrayList<ScoreData> searchArrayList;

    private LayoutInflater mInflater;

    public ScoresAdapter(Context context, ArrayList<ScoreData> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sports_list_view, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.PittLogo);
            holder.pittScore = (TextView) convertView.findViewById(R.id.pittScore);
            holder.vs = (TextView) convertView.findViewById(R.id.vs);
            holder.oppScore = (TextView) convertView.findViewById(R.id.oppScore);
            holder.oppName = (TextView) convertView.findViewById(R.id.oppName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.pittScore.setText("" + searchArrayList.get(position).getPittScore());
        holder.oppScore.setText("" + searchArrayList.get(position).getOppScore());
        holder.oppName.setText(searchArrayList.get(position).getOppName());


        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView pittScore;
        TextView vs;
        TextView oppScore;
        TextView oppName;
    }
}

