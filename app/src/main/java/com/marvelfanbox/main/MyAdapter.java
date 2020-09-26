package com.marvelfanbox.main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<String> values;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView title;
        public TextView type;
        public TextView release_date;
        public ImageView thumb;
        public View layout;
        String mid;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            title = (TextView) v.findViewById(R.id.movieTitle);
            type = (TextView) v.findViewById(R.id.type);
            release_date = (TextView) v.findViewById(R.id.release_date);
            thumb = (ImageView) v.findViewById(R.id.thumb);
        }
    }

//    public void add(int position, String item) {
//        Log.e("POPOPO", "add called");
//
//        values.add(position, item);
//        notifyItemInserted(position);
//    }
//
//    public void remove(int position) {
//        Log.e("POPOPO", "remove called");
//        values.remove(position);
//        notifyItemRemoved(position);
//    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<String> myDataset, Context context) {
        this.context = context;
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("POPOPO", "onCreateViewHolder called");

        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item, parent, false);

        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element]
        Log.e("POPOPO", "onBindViewHolder called");
        final String mid = values.get(position);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.getContext());

        holder.mid = mid;
        final String url = "http://www.omdbapi.com/?i=" + mid +"&apikey=" + context.getResources().getString(R.string.OMDB_API_KEY);
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Log.e("RNHG", response.toString());
                    holder.title.setText(response.getString("Title"));
                    holder.type.setText(response.getString("Type"));
                    holder.release_date.setText(response.getString("Released"));
                    String icon_uri = response.getString("Poster");

                    Glide.with(context).load(icon_uri)
                            .transition(withCrossFade())
                            .apply(new RequestOptions().override(800, 400)
                                    .error(R.drawable.placeholder_movieimage)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .centerCrop()
                            )
                            .into(holder.thumb);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);

//        holder.title.setText(name);
//        holder.thumb.setImageResourcße(R.drawable.marvel);
        holder.layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //openInfoActivity(view, mid, url);
            }
        });
    }

    private void openInfoActivity(View view, String mid, String url) {
        Intent intent = new Intent(context, InfoActivity.class);
        TextView title = view.findViewById(R.id.movieTitle);
        ImageView img = view.findViewById(R.id.thumb);
        intent.putExtra("MOVIE_TITLE", title.getText());
        intent.putExtra("MOVIE_ID", mid);
        intent.putExtra("MOVIE_POSTER__URL", url);
        context.startActivity(intent);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        Log.e("POPOPO", "getItemCount called");

        return values.size();
    }

    public void updateDataSet(List movies) {
        this.values = movies;
    }

}