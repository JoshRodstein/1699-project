package group1.pittapi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by billyhinard on 3/23/18.
 */

public class BuildingAdapter extends BaseAdapter {
    private static ArrayList<BuildingData> searchArrayList;

    private LayoutInflater mInflater;

    public BuildingAdapter(Context context, ArrayList<BuildingData> results) {
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
            convertView = mInflater.inflate(R.layout.building_list_view, null);
            holder = new ViewHolder();
            holder.buildingName = (TextView) convertView.findViewById(R.id.buildingName);
            holder.buildingHours = (TextView) convertView.findViewById(R.id.buildingHours);
            holder.buildingClosed = (TextView) convertView.findViewById(R.id.buildingClosed);
            holder.buildingOpen = (LinearLayout) convertView.findViewById(R.id.buildingOpen);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.buildingName.setText(searchArrayList.get(position).getBuildingName());
        holder.buildingHours.setText(searchArrayList.get(position).getBuildingHours());
        if(!searchArrayList.get(position).checkHours()) {
            holder.buildingOpen.setBackgroundColor(Color.parseColor("#aaaaaaaa"));
            holder.buildingClosed.setVisibility(View.VISIBLE);


        }

        return convertView;
    }

    static class ViewHolder {
        TextView buildingName;
        TextView buildingHours;
        TextView buildingClosed;
        LinearLayout buildingOpen;
    }
}

