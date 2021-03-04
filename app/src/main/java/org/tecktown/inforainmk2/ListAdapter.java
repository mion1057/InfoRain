package org.tecktown.inforainmk2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.tecktown.inforainmk2.VO.ContentsVO;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<ContentsVO> sample = new ArrayList<ContentsVO>();

    public ListAdapter(){ }

    public ListAdapter(Context context, ArrayList<ContentsVO> data){
        this.sample = data;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return sample.size();
    }

    @Override
    public ContentsVO getItem(int position) {
        return sample.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mContext = parent.getContext();

        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview_custom, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.filename);

        ContentsVO contentsVO = sample.get(position);

        textView.setText(contentsVO.getFileName());


        return view;

    }

}
