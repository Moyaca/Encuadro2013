package com.example.formulario_dinamico;

import java.util.List;


import android.content.Context;
import android.graphics.Color;
import android.provider.CalendarContract.Colors;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
 
public class ItemAdapter extends BaseAdapter {
 
    private Context context;
    private List<Item> items;
//    private String tipo;
 
    public ItemAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
//        this.tipo = tipo;
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
        
        //pregunto por el contenido del item, que clase es para saber que mostrar
        if(this.items.get(0).getClass().equals(Pregunta.class)){
        
            if (convertView == null) {
                // Create a new view into the list.
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.pregunta_respuesta, parent, false);
	            
                //Set data into the view.
	            
	            TextView tv_pregunta = (TextView) rowView.findViewById(R.id.textView1);
	            RadioGroup rg_respuestas = (RadioGroup) rowView.findViewById(R.id.radioGroup1);
	
	            final Pregunta pregunta =(Pregunta)this.items.get(position);
	            
	            tv_pregunta.setText(pregunta.getPregunta());;     
	            tv_pregunta.setTextColor(Color.BLACK);

	            for (Respuesta r : pregunta.getRes()) {
	            	RadioButton rb = new RadioButton(context);
	            	rb.setText(r.getText());
	            	rb.setTextColor(Color.DKGRAY);
	            	rb.setGravity(Gravity.CENTER_VERTICAL);
					rg_respuestas.addView(rb);
						
				}
	            
	            rg_respuestas.setOnCheckedChangeListener(new OnCheckedChangeListener(){
		    		 
		 		    @Override
		 		    public void onCheckedChanged(RadioGroup group, int checkedId) {
		 		        // TODO Auto-generated method stub
	//	 		    	int res_id = -1;
		 		    	RadioButton rb = (RadioButton) group.findViewById(checkedId);
		 		    	outerloop:
		 		    	for(int i=0; i<group.getChildCount();i++){
		 		    		if(rb.getId()==group.getChildAt(i).getId()){
		 		    			Respuesta res= (Respuesta) pregunta.getItem(i);
		 		    			pregunta.refreshRes();
		 		    			res.setSelected(true);
		 		    			Toast.makeText(context, "id: " + res.getSelected(), Toast.LENGTH_SHORT).show();
		 		    			break outerloop;
		 		    		}
		 		    	}
		 		    	
		 		   }
		 		     
		 		});
            }
        }else{
        	Toast.makeText(context, "asd", Toast.LENGTH_LONG).show();
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
        }
        return rowView;
    }
 
}
