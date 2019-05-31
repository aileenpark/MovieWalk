package capstone.moviewalk.moviewalk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class SwitchListviewAdapter extends ArrayAdapter<String>{
    private ArrayList<String> items;
    private Context context;
    private int id;
    public SwitchListviewAdapter(Context context, int resourceId, ArrayList<String> objects) {
        super(context, resourceId, objects);
        this.context = context;
        this.id = resourceId;
        this.items = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        SwitchListviewAdapter that = SwitchListviewAdapter.this;


        if(v == null) {
            LayoutInflater viewInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = viewInflater.inflate(R.layout.switch_list, null);
        }

        TextView textView = (TextView) v.findViewById(R.id.switchText);
        textView.setText(items.get(position));

        Switch switchButton = (Switch)v.findViewById(R.id.switch1);
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        return v;
    }

    public ArrayList<String> getItems() {
        return this.items;
    }

}
