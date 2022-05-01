package com.chotupartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chotupartner.OrderDetailsActivity;
import com.chotupartner.R;
import com.chotupartner.modelClass.orderlist.OrderInfo;
import com.chotupartner.utils.DateFormats;
import com.chotupartner.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class ConfirmedOrderAdapter extends RecyclerView.Adapter<ConfirmedOrderAdapter.ViewHolder> {

    private List<OrderInfo> moviesList;
    private LayoutInflater mInflater;
    private ConfirmedOrderAdapter.ItemClickListener mClickListener;
    boolean check = true;
    Context context;
    Context cartListActivity;
    ItemClickListener itemClickListener;
    int value = 0, count = 1;

    // data is passed into the constructor
    public ConfirmedOrderAdapter(Context cartListActivity, List<OrderInfo> moviesList, ItemClickListener itemClickListener) {

        this.moviesList = moviesList;
        this.cartListActivity = cartListActivity;
        this.itemClickListener = itemClickListener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ConfirmedOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.orderrequest_accepted, parent, false);


        return new ConfirmedOrderAdapter.ViewHolder(itemView);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ConfirmedOrderAdapter.ViewHolder holder, final int position) {


        //  Picasso.get().load(moviesList.get(position).getProductImg()).into(holder.ivItem);

        // holder.deliverBy.setText("Deliver By "+moviesList.get(position).getOutlet().getOutletName());
        holder.tvPrice.setText("Rs " + moviesList.get(position).getAmount());

        holder.tvDeliveryChar.setText("Rs " + moviesList.get(position).getDeliveryCharge());
        holder.deliveryCode.setText(""+moviesList.get(position).getOutlet_otp());

        holder.tvOrderID1.setText(""+moviesList.get(position).getOrderCustomerId());
        holder.deliveryPeronName.setText("" + moviesList.get(position).getDelivery_name());
        holder.deliveryPersonNumber.setText(" " + moviesList.get(position).getContact_no());
        if (moviesList.get(position).getPaymentType().equalsIgnoreCase("ROZERPAY")){
            holder.paymentMode.setText("Online");

        }else{
            holder.paymentMode.setText("CASH");

        }

        holder.callImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + moviesList.get(position).getContact_no()));
//                context.startActivity(intent);
            }
        });

        int total = (int) Float.parseFloat(moviesList.get(position).getAmount());


        if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("Pending")) {

            Picasso.get().load(R.drawable.circle).into(holder.iv1);

            Picasso.get().load(R.drawable.ring).into(holder.iv2);
            Picasso.get().load(R.drawable.ring).into(holder.iv3);
            Picasso.get().load(R.drawable.ring).into(holder.iv4);
        } else if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("processing")) {
            Picasso.get().load(R.drawable.circle).into(holder.iv1);
            holder.iv1Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            Picasso.get().load(R.drawable.circle).into(holder.iv2);
            Picasso.get().load(R.drawable.ring).into(holder.iv3);
            Picasso.get().load(R.drawable.ring).into(holder.iv4);
        } else if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("dispatched")) {

            Picasso.get().load(R.drawable.circle).into(holder.iv1);
            holder.iv1Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv2Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));



            Picasso.get().load(R.drawable.circle).into(holder.iv2);
            Picasso.get().load(R.drawable.circle).into(holder.iv3);
            Picasso.get().load(R.drawable.ring).into(holder.iv4);
        } else if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("complete")) {
            holder.iv1Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv2Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv3Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));

            Picasso.get().load(R.drawable.circle).into(holder.iv1);
            Picasso.get().load(R.drawable.circle).into(holder.iv2);
            Picasso.get().load(R.drawable.circle).into(holder.iv3);
            Picasso.get().load(R.drawable.circle).into(holder.iv4);

        }

        holder.tvOrderPlaced.setText("ORDER Placed on :  " + Utils.orderPlaced(moviesList.get(position).getDateAdded()));
        holder.deliveryTime.setText("Delivery Date :   " + Utils.startTimeFormat(Long.parseLong(moviesList.get(position).getSlotBook())*1000));

        if (!TextUtils.isEmpty(moviesList.get(position).getDelivery_name())) {
            holder.deliveryPeronName.setText("" + moviesList.get(position).getDelivery_name());
            holder.deliveryPersonNumber.setText("" + moviesList.get(position).getContact_no());
            holder.contactType.setText("Delivery Person :");
        } else {
            holder.deliveryPeronName.setText("" + moviesList.get(position).getOutlet_name());
            holder.deliveryPersonNumber.setText("" + moviesList.get(position).getOutlet_no());
            holder.contactType.setText("Outlet Name: ");

        }


        holder.totalPaidAmount.setText("Rs " + total);


        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(moviesList.get(position).getSlotBook())*1000);  //here your time in miliseconds
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);


//        holder.DeliveryDate.setText("Scheduled for " + date + " " + time);

        holder.viewOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iii = new Intent(view.getContext(), OrderDetailsActivity.class);
                iii.putExtra("orderID", moviesList.get(position).getOrderId());
                view.getContext().startActivity(iii);

            }
        });


    }

    // total number of cells
    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView   callImg, viewOrderDetail, iv1, iv2, iv3, iv4;
        public View iv1Line, iv2Line, iv3Line;

        RelativeLayout rl;
        TextView  tvOrderPlaced,deliveryTime,contactType, tvPrice,deliveryPeronName, acceptTV,rejectTv,deliveryCode,deliveryPersonNumber,tvOrderID1, tvDeliveryChar, totalPaidAmount,paymentMode;
        LinearLayout llImage, llOutLetText, llTop;
        Button btnViewDetails;

        ViewHolder(View itemView) {
            super(itemView);


            callImg = itemView.findViewById(R.id.callToUser);
            deliveryCode = itemView.findViewById(R.id.deliveryCode);
            tvOrderPlaced = itemView.findViewById(R.id.tvOrderPlaced);
            contactType = itemView.findViewById(R.id.contactType);
            deliveryTime = itemView.findViewById(R.id.deliveryTime);
            deliveryPeronName = itemView.findViewById(R.id.deliveryPeronName);
            deliveryPersonNumber = itemView.findViewById(R.id.deliveryPersonNumber);

            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDeliveryChar = itemView.findViewById(R.id.tvDeliveryChar);
            tvOrderID1 = itemView.findViewById(R.id.tvOrderID1);
            totalPaidAmount = itemView.findViewById(R.id.totalPaidAmount);
            paymentMode = itemView.findViewById(R.id.paymentMode);
            acceptTV = itemView.findViewById(R.id.acceptTV);
            rejectTv = itemView.findViewById(R.id.rejectTv);
            viewOrderDetail = itemView.findViewById(R.id.viewOrderDetail);


            iv1 = itemView.findViewById(R.id.iv1);
            iv2 = itemView.findViewById(R.id.iv2);
            iv3 = itemView.findViewById(R.id.iv3);
            iv4 = itemView.findViewById(R.id.iv4);

            iv1Line = itemView.findViewById(R.id.v1line);
            iv2Line = itemView.findViewById(R.id.v2line);
            iv3Line = itemView.findViewById(R.id.v3line);

        }


    }

    // convenience method for getting data at click position


    // allows clicks events to be caught
    void setClickListener(ConfirmedOrderAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onAccept(int orderId);
        void onReject(int orderId);
    }
}
