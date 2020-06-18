package com.develop.online_order_processing_system.Adepter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.online_order_processing_system.ModelClasses.ImageConvater;
import com.develop.online_order_processing_system.ModelClasses.OrderStatus;
import com.develop.online_order_processing_system.ModelClasses.ProductUpload;
import com.develop.online_order_processing_system.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProdectListAdepter extends RecyclerView.Adapter<ProdectListAdepter.MyViewHolder> {


    private Context context;
    private ArrayList<ProductUpload> productListArrayList;
    private DatabaseReference databaseReference,databaseReference2;
    private FirebaseUser user;
    private int total = 0;
    private String companysId;
    private String companyKey;
    private String companysName;
    private Bitmap bitmap;
    private ImageConvater imageConvater;



    public ProdectListAdepter(ArrayList<ProductUpload> productListArrayList, String companyKey,String key ,String empty,Context context) {

        this.context = context;
        this.productListArrayList = productListArrayList;
        this.companyKey = companyKey;
        this.companysName = key;
    }


    public ProdectListAdepter(Context context, ArrayList<ProductUpload> productListArrayList, String companyKey,String key) {
        this.context = context;
        this.productListArrayList = productListArrayList;
        this.companyKey = companyKey;
        this.companysName = key;
    }

    public int getTotal() {
        return total;
    }

    public String getCompanysId() {
        return companysId;
    }

    public String getCompanysName() {
        return companysName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        imageConvater = new ImageConvater();

        if (companyKey.equals("company"))
        {
            holder.removeProduct.setVisibility(View.VISIBLE);
            holder.quentityId.setVisibility(View.GONE);
        }

        Toast.makeText(context,companyKey,Toast.LENGTH_SHORT).show();

        final ProductUpload productUpload = productListArrayList.get(position);
        user = FirebaseAuth.getInstance().getCurrentUser();
        holder.productName.setText("Name : "+productUpload.getProductName());
        holder.productDesc.setText("Our Product : "+productUpload.getProductDesc());

        holder.decresQuantity.setEnabled(false);


        int price = Integer.parseInt(productUpload.getProductPrice());
        final int offer = Integer.parseInt(productUpload.getProductOffer());
        float offerPrice = (float) (offer/100.00);
        offerPrice = offerPrice*price;
        offerPrice = price - offerPrice;

        holder.productPrice.setText(String.valueOf(offerPrice));
        holder.offer.setText("Offer : "+productUpload.getProductOffer()+" %");

        if (offer<=0)
        {
            holder.offer.setVisibility(View.GONE);
        }

        companysId = productUpload.getCompanysId();

        if (productUpload.getFilePth() != null) {
            Bitmap returnImage = imageConvater.StringToBitMap(productUpload.getFilePth());
            holder.productImage.setImageBitmap(returnImage);
        }

        holder.incraseQuantity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String quantity = holder.quantity.getText().toString();
                String productName = productUpload.getProductName();
                String companysId = productUpload.getCompanysId();
                String Sprice = holder.productPrice.getText().toString();


                int pQuantity = Integer.parseInt(quantity);
                float price = Float.parseFloat(Sprice);

                total = (int) (total + price);

                pQuantity = pQuantity + 1;
                if (pQuantity>0)
                {
                    holder.decresQuantity.setEnabled(true);

                }
                holder.quantity.setText(String.valueOf(pQuantity));

                databaseReference = FirebaseDatabase.getInstance().getReference("OrderStatus").child(user.getUid()).child(productName);
                OrderStatus orderStatus = new OrderStatus((int) price,pQuantity,productName,user.getUid(),companysId);
                databaseReference.setValue(orderStatus);
            }
        });

        holder.decresQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity = holder.quantity.getText().toString();
                String Sprice =holder.productPrice.getText().toString();
                String productName = productUpload.getProductName();
                String companysId = productUpload.getCompanysId();

                int pQuantity = Integer.parseInt(quantity);
                float price = Float.parseFloat(Sprice);
                total = (int) (total- price);

                databaseReference = FirebaseDatabase.getInstance().getReference("OrderStatus").child(user.getUid()).child(productName);
                OrderStatus orderStatus = new OrderStatus((int) price,pQuantity,productName,user.getUid(),companysId);
                databaseReference.setValue(orderStatus);

                pQuantity = pQuantity - 1;
                if (pQuantity<1)
                {
                    holder.decresQuantity.setEnabled(false);
                    databaseReference2 = FirebaseDatabase.getInstance().getReference("OrderStatus").child(user.getUid()).child(productName);
                    databaseReference2.removeValue();
                }
                holder.quantity.setText(String.valueOf(pQuantity));

            }
        });

        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product").child(getCompanysName()).child(productUpload.getProductName());
                databaseReference.removeValue();

            }
        });


    }

    @Override
    public int getItemCount() {
        return productListArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDesc, productPrice;
        ImageButton incraseQuantity, decresQuantity;
        TextView quantity;
        TextView offer;
        ImageView productImage;
        Button removeProduct;
        LinearLayout quentityId;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.productName);
            productDesc = itemView.findViewById(R.id.productDesc);
            productPrice = itemView.findViewById(R.id.productPrice);
            incraseQuantity = itemView.findViewById(R.id.increaseQuantity);
            decresQuantity = itemView.findViewById(R.id.decreaseQuantity);
            quantity = itemView.findViewById(R.id.quantity);
            productImage = itemView.findViewById(R.id.productImage);
            offer = itemView.findViewById(R.id.offerId);
            quentityId = itemView.findViewById(R.id.quentityId);
            removeProduct = itemView.findViewById(R.id.removeProduct);


        }
    }

    public void removeAt(int position) {
        productListArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productListArrayList.size());
    }
}
