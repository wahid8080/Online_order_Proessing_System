package com.develop.online_order_processing_system.Adepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.online_order_processing_system.ModelClasses.CompanysInfo;
import com.develop.online_order_processing_system.ModelClasses.ImageConvater;
import com.develop.online_order_processing_system.R;
import com.develop.online_order_processing_system.Review;
import com.develop.online_order_processing_system.ViewProduct;

import java.util.ArrayList;

public class CompanysListAdepter extends RecyclerView.Adapter<CompanysListAdepter.MyViewHolder> {

    private Context context;
    private ArrayList<CompanysInfo> companysInfoArrayList;
    private String nothing,shopName;

    String companyKey;

    public CompanysListAdepter(Context context, ArrayList<CompanysInfo> companysInfoArrayList,String companyKey,String shopName,String nothing) {
        this.context = context;
        this.companysInfoArrayList = companysInfoArrayList;
        this.companyKey = companyKey;
        this.shopName = shopName;
    }

    public CompanysListAdepter(Context context, ArrayList<CompanysInfo> companysInfoArrayList, String nothing, String companyKey) {
        this.context = context;
        this.companysInfoArrayList = companysInfoArrayList;
        this.nothing = nothing;
        this.companyKey = companyKey;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompanysListAdepter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.company_list_view, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        ImageConvater imageConvater = new ImageConvater();

        final CompanysInfo companysInfo = companysInfoArrayList.get(position);
        holder.name.setText(companysInfo.getcName());
        float rating = companysInfo.getRating();

        String sRating = String.valueOf(rating);
        holder.companysRating.setText(sRating);

        int like = companysInfo.getLike();
        int unlike = companysInfo.getUnlike();

        holder.numberOfLike.setText(String.valueOf(like));
        holder.numberOfUnLike.setText(String.valueOf(unlike));



        if (companysInfo.getCoverPic() != null) {
            Bitmap returnImage = imageConvater.StringToBitMap(companysInfo.getCoverPic());
            holder.coverImage.setImageBitmap(returnImage);
        }
        if (companysInfo.getProfilePic() != null) {

            Bitmap returnImage = imageConvater.StringToBitMap(companysInfo.getProfilePic());
            holder.profileImage.setImageBitmap(returnImage);
        }


        holder.seeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewProduct.class);
                intent.putExtra("key", companysInfo.getcName());
                intent.putExtra("companyKey", companyKey);
                context.startActivity(intent);
            }
        });

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,companysInfo.getcName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Review.class);
                intent.putExtra("companysId",companysInfo.getcName());
                intent.putExtra("shopName",shopName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companysInfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, companysRating;
        ImageView profileImage, coverImage;
        Button seeProduct;
        TextView numberOfLike,numberOfUnLike;
        Button review;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            companysRating = itemView.findViewById(R.id.companyRating);
            profileImage = itemView.findViewById(R.id.logoForCompany);
            coverImage = itemView.findViewById(R.id.coverPicForCompany);
            name = itemView.findViewById(R.id.CompanyName);
            seeProduct = itemView.findViewById(R.id.seeProduct);
            numberOfLike = itemView.findViewById(R.id.numberOfLike);
            numberOfUnLike = itemView.findViewById(R.id.numberOfUnLike);
            review = itemView.findViewById(R.id.reviewId);
        }
    }
}
