package com.chotupartner.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chotupartner.OrderDetailsActivity;
import com.chotupartner.R;
import com.chotupartner.modelClass.forOutLet.OrderProduct;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {

    private List<OrderProduct> moviesList;
    private OrderDetailsAdapter.ItemClickListener mClickListener;
    boolean check = true;
    OrderDetailsActivity orderDetailsActivity;

    // data is passed into the constructor
    public OrderDetailsAdapter(OrderDetailsActivity orderDetailsActivity, List<OrderProduct> moviesList) {

        this.moviesList = moviesList;
        this.orderDetailsActivity = orderDetailsActivity;
    }

    // inflates the cell layout from xml when needed
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.myorder_detais_item, parent, false);


        return new OrderDetailsAdapter.ViewHolder(itemView);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder holder,  int position) {
       if (moviesList.get(position).getDiscount().equalsIgnoreCase("0")) {
           holder.tvOrdderNuber.setVisibility(View.GONE);
           holder.rlDiscount.setVisibility(View.GONE);
       }else {
           int discountPrice = (int) Float.parseFloat(moviesList.get(position).getRegularPrice());
           holder.tvOrdderNuber.setText("Rs " + discountPrice);
           holder.tvOrderDate1.setPaintFlags(holder.tvOrderDate1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

           holder.rlDiscount.setVisibility(View.VISIBLE);
       }




        holder.tvOrdderName1.setText(moviesList.get(position).getProductTitle());
        holder.tvOrderDate1.setText("Rs " + moviesList.get(position).getPrice());
        holder.qtyNumber.setText("Items " + moviesList.get(position).getQuantity());

        Picasso.get().load(moviesList.get(position).getImage()).into(holder.ivImage);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView  ivImage;
        RelativeLayout rlDiscount;
        TextView tvOrdderNuber, tvOrdderName1, qtyNumber,tvOrderDate1, DeliveryDate1;
        LinearLayout llTop;

        ViewHolder(View itemView) {
            super(itemView);

            tvOrdderNuber = itemView.findViewById(R.id.tvOrdderNuber);
            tvOrdderName1 = itemView.findViewById(R.id.tvOrdderName1);
            qtyNumber = itemView.findViewById(R.id.qtyNumber);
            tvOrderDate1 = itemView.findViewById(R.id.tvOrderDate1);
            DeliveryDate1 = itemView.findViewById(R.id.DeliveryDate1);
            llTop = itemView.findViewById(R.id.llTop);
            rlDiscount = itemView.findViewById(R.id.rlDiscount);
            ivImage= itemView.findViewById(R.id.ivImage);

        }


    }

    // convenience method for getting data at click position


    // allows clicks events to be caught
    void setClickListener(OrderDetailsAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
