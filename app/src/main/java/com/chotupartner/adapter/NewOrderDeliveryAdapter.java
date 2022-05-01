package com.chotupartner.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.chotupartner.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

public class NewOrderDeliveryAdapter extends RecyclerView.Adapter<NewOrderDeliveryAdapter.ViewHolder> {

    private List<OrderInfo> moviesList;
    private LayoutInflater mInflater;
    private NewOrderDeliveryAdapter.ItemClickListener mClickListener;
    boolean check = true;
    Context context;
    Context cartListActivity;
    ItemClickListener itemClickListener;
    int value = 0, count = 1;

    // data is passed into the constructor
    public NewOrderDeliveryAdapter(Context cartListActivity, List<OrderInfo> moviesList, ItemClickListener itemClickListener) {

        this.moviesList = moviesList;
        this.cartListActivity = cartListActivity;
        this.itemClickListener = itemClickListener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public NewOrderDeliveryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.orderrequest_delivery, parent, false);


        return new NewOrderDeliveryAdapter.ViewHolder(itemView);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull NewOrderDeliveryAdapter.ViewHolder holder, final int position) {


        //  Picasso.get().load(moviesList.get(position).getProductImg()).into(holder.ivItem);

        // holder.deliverBy.setText("Deliver By "+moviesList.get(position).getOutlet().getOutletName());
        holder.tvPrice.setText("Rs " + moviesList.get(position).getAmount());

        holder.tvDeliveryChar.setText("Rs " + moviesList.get(position).getDeliveryCharge());
        holder.deliveryCode.setText("" + moviesList.get(position).getOutlet_otp());

        holder.tvOrderID1.setText("" + moviesList.get(position).getOrderCustomerId());

        int total = (int) Float.parseFloat(moviesList.get(position).getAmount());
        if (!TextUtils.isEmpty(moviesList.get(position).getDelivery_name())) {
            holder.deliveryPeronName.setText("" + moviesList.get(position).getDelivery_name());
            holder.deliveryPersonNumber.setText("" + moviesList.get(position).getContact_no());
            holder.contactType.setText("Outlet Name: ");
        } else {
            holder.deliveryPeronName.setText("" + moviesList.get(position).getOutlet_name());
            holder.deliveryPersonNumber.setText("" + moviesList.get(position).getOutlet_no());
            holder.contactType.setText("Delivery Person : ");

        }
        if (moviesList.get(position).getPaymentType().equalsIgnoreCase("ROZERPAY")){
            holder.paymentMode.setText("Online");

        }else{
            holder.paymentMode.setText("CASH");

        }
        String deliveryDetails=""+moviesList.get(position).getFirstname()+" "+(!TextUtils.isEmpty(moviesList.get(position).getLastname())?""+moviesList.get(position).getLastname():"")
                +"\n"+moviesList.get(position).getPhone()+"\n"+moviesList.get(position).getAddress()+"\n"+moviesList.get(position).getSub_service_area()+"\n"+
                moviesList.get(position).getCity();
        holder.addressTv.setText(""+deliveryDetails);


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
        } else if (moviesList.get(position).getOrderStatus().equalsIgnoreCase("Complete")) {
            holder.iv1Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv2Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));
            holder.iv3Line.setBackgroundColor(context.getResources().getColor(R.color.green_color));

            Picasso.get().load(R.drawable.circle).into(holder.iv1);
            Picasso.get().load(R.drawable.circle).into(holder.iv2);
            Picasso.get().load(R.drawable.circle).into(holder.iv3);
            Picasso.get().load(R.drawable.circle).into(holder.iv4);

        } else {
            holder.llOutLetText.setVisibility(View.GONE);

            holder.llImage.setVisibility(View.GONE);
        }


        holder.totalPaidAmount.setText("Rs " + total);
        holder.deliverTv.setVisibility(View.GONE);
        holder.acceptTV.setVisibility(View.VISIBLE);

        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(Long.parseLong(moviesList.get(position).getSlotBook()) * 1000);  //here your time in miliseconds
        String date = "" + cl.get(Calendar.DAY_OF_MONTH) + "-" + cl.get(Calendar.MONTH) + "-" + cl.get(Calendar.YEAR);
        String time = "" + cl.get(Calendar.HOUR_OF_DAY) + ":" + cl.get(Calendar.MINUTE) + ":" + cl.get(Calendar.SECOND);

        holder.tvOrderPlaced.setText("ORDER Placed on :  " + Utils.orderPlaced(moviesList.get(position).getDateAdded()));
        holder.deliveryTime.setText("Delivery Date :   " + Utils.startTimeFormat(Long.parseLong(moviesList.get(position).getSlotBook())*1000));

//        holder.DeliveryDate.setText("Scheduled for " + date + " " + time);

        holder.viewOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iii = new Intent(view.getContext(), OrderDetailsActivity.class);
                iii.putExtra("orderID", moviesList.get(position).getOrderId());
                view.getContext().startActivity(iii);

            }
        });


        holder.acceptTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onAcceptPickup(moviesList.get(position).getOrderId(),moviesList.get(position).getOrderCustomerId());
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

        public ImageView viewOrderDetail, iv1, iv2, iv3, iv4;
        public View iv1Line, iv2Line, iv3Line;

        RelativeLayout rl;
        TextView tvOrderPlaced,deliveryTime, addressTv,contactType,tvPrice, deliveryPeronName, acceptTV, deliveryCode, deliverTv, deliveryPersonNumber, tvOrderID1, tvDeliveryChar, totalPaidAmount, paymentMode;
        LinearLayout llImage, llOutLetText, llTop;
        Button btnViewDetails;

        ViewHolder(View itemView) {
            super(itemView);


            deliveryCode = itemView.findViewById(R.id.deliveryCode);
            tvOrderPlaced = itemView.findViewById(R.id.tvOrderPlaced);
            deliveryTime = itemView.findViewById(R.id.deliveryTime);
            deliveryPeronName = itemView.findViewById(R.id.deliveryPeronName);
            deliveryPersonNumber = itemView.findViewById(R.id.deliveryPersonNumber);
            deliverTv = itemView.findViewById(R.id.deliverTv);
            contactType = itemView.findViewById(R.id.contactType);
            addressTv = itemView.findViewById(R.id.addressTv);


            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDeliveryChar = itemView.findViewById(R.id.tvDeliveryChar);
            tvOrderID1 = itemView.findViewById(R.id.tvOrderID1);
            totalPaidAmount = itemView.findViewById(R.id.totalPaidAmount);
            paymentMode = itemView.findViewById(R.id.paymentMode);
            acceptTV = itemView.findViewById(R.id.acceptTV);
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
    void setClickListener(NewOrderDeliveryAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onAcceptPickup(int orderId, String orderCustomerId);

        void onDeliveryOrder(int orderId,String orderCustomerId);
    }
}
