package Listado_Items;

import java.util.List;

import com.example.qr.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

	public class ItemAdapter extends BaseAdapter {
		 
	    private Context context;
	    private List<Item> items;
	 
	    public ItemAdapter(Context context, List<Item> items) {
	        this.context = context;
	        this.items = items;
	    }
	 
	    @Override
	    public int getCount() {
	        return this.items.size();
	    }
	 
	    @Override
	    public Object getItem(int position) {
	        return this.items.get(position);
	    }
	 
	    @Override
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    
	    @Override
	    public View getView(int position, View convertView, ViewGroup parent) {
	    	
	    	//esta view es un pequeño layout al cual modifico dependiendo si quiero Puntajes o Preguntas
	        View rowView = convertView;
	        
		        if (convertView == null) {
		            // Create a new view into the list.
		            LayoutInflater inflater = (LayoutInflater) context
		                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		            rowView = inflater.inflate(R.layout.puntaje_simple, parent, false);
		        }
		 
		//         Set data into the view.
		        TextView tv_nombre = (TextView) rowView.findViewById(R.id.textView2);
		        TextView tv_puntaje = (TextView) rowView.findViewById(R.id.textView1);
		        
		        ItemList item =(ItemList)this.items.get(position);
		        Integer p = item.getPuntaje();
		        tv_puntaje.setText(p.toString());
		        tv_nombre.setText(item.getNombre());;        
	        
	        return rowView;
	    }
}
