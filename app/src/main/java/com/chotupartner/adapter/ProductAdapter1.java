package com.chotupartner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chotupartner.BuildConfig;
import com.chotupartner.R;
import com.chotupartner.activity.ui.store.ProductOnOutlet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class ProductAdapter1 extends RecyclerView.Adapter<ProductAdapter1.ViewHolder> {

    private List<ProductOnOutlet> productOnOutlet;
    private List<ProductOnOutlet> productOnOutletFilter;

    private LayoutInflater mInflater;
    private ProductAdapter1.ItemClickListener mClickListener;
    boolean check = true;
    Context context;
    Context cartListActivity;
    ItemClickListener itemClickListener;
    int value = 0, count = 1;

    // data is passed into the constructor
    public ProductAdapter1(Context cartListActivity, List<ProductOnOutlet> productOnOutlet, ItemClickListener itemClickListener) {
        this.productOnOutlet = productOnOutlet;
        this.cartListActivity = cartListActivity;
        this.itemClickListener = itemClickListener;
        this.productOnOutletFilter=productOnOutlet;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ProductAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        this.context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.product_info, parent, false);


        return new ProductAdapter1.ViewHolder(itemView);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ProductAdapter1.ViewHolder holder, final int position) {


        //  Picasso.get().load(moviesList.get(position).getProductImg()).into(holder.ivItem);

       // holder.deliverBy.setText("Deliver By "+moviesList.get(position).getOutlet().getOutletName());



        holder.productName.setText("" + productOnOutletFilter.get(position).getProductName());
        if (TextUtils.isEmpty(productOnOutletFilter.get(position).getMrp())){
            holder.mrp.setText("Rs " + productOnOutletFilter.get(position).getPrice());
            holder.finalPrice.setText("Rs " + (!TextUtils.isEmpty(productOnOutletFilter.get(position).getDiscountPrice())?productOnOutletFilter.get(position).getDiscountPrice():"0"));
            holder.discount.setText(""+0);
            holder.checkbox.setVisibility(View.GONE);
            holder.checkbox.setImageResource(R.drawable.ic_check_normal);
        }else{
            holder.mrp.setText("Rs " + productOnOutletFilter.get(position).getMrp());
            holder.finalPrice.setText("Rs " + productOnOutletFilter.get(position).getDiscountPrice());
            holder.discount.setText(""+productOnOutletFilter.get(position).getDiscount());
            holder.checkbox.setImageResource(R.drawable.ic_check_selected);
            holder.checkbox.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(productOnOutletFilter.get(position).getProductImages()) && productOnOutletFilter.get(position).getProductImages().length()>5){

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(productOnOutletFilter.get(position).getProductImages());
                String imagePath = BuildConfig.API_SERVER_IP+"assets/uploads/product/"+jsonArray.get(0);
                Log.d("Path", ""+imagePath);
                Glide.with(context)
                    .load(imagePath)
                    .addListener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(true)
                    .error(R.drawable.place_holder_img)

                    .fitCenter() // scale to fit entire image within ImageView
                    .into(holder.productImage);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            holder.productImage.setImageResource(R.drawable.place_holder_img);

        }




        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (!TextUtils.isEmpty(productOnOutlet.get(position).getAvailability()) && productOnOutlet.get(position).getAvailability().equalsIgnoreCase("1")){
                     itemClickListener.updateAvailable(productOnOutlet.get(position),"0", position);
                 }else{
                     itemClickListener.updateAvailable(productOnOutlet.get(position),"1", position);
                 }
            }
        });

        holder.editproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onEdit(productOnOutlet.get(position),position);


            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return productOnOutlet.size();
    }





    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView productImage,checkbox,editproduct;

        TextView  mrp,productName, discount,finalPrice;

        ViewHolder(View itemView) {
            super(itemView);


            productImage = itemView.findViewById(R.id.imagePlaceHolder);
            checkbox = itemView.findViewById(R.id.checkbox);
            mrp = itemView.findViewById(R.id.mrp);
            productName = itemView.findViewById(R.id.productName);
            discount = itemView.findViewById(R.id.discount);
            finalPrice = itemView.findViewById(R.id.finalPrice);
            editproduct = itemView.findViewById(R.id.editProduct);

        }
    }

    // allows clicks events to be caught
    void setClickListener(ProductAdapter1.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void updateAvailable(ProductOnOutlet productOnOutlet, String available, int position);
        void onEdit(ProductOnOutlet productOnOutlet,int position);
    }
}
