package com.develop.online_order_processing_system.Adepter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develop.online_order_processing_system.ModelClasses.ImageConvater;
import com.develop.online_order_processing_system.ModelClasses.ShopKeeperInfo;
import com.develop.online_order_processing_system.R;

import java.util.ArrayList;

public class ShopKepperListAdepter extends RecyclerView.Adapter<ShopKepperListAdepter.MyViewHolder> {


    private Context context;
    private ImageConvater imageConvater;
    private ArrayList<ShopKeeperInfo> shopKeeperInfoArrayList;

    public ShopKepperListAdepter(Context context, ArrayList<ShopKeeperInfo> shopKeeperInfoArrayList) {
        this.context = context;
        this.shopKeeperInfoArrayList = shopKeeperInfoArrayList;
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShopKepperListAdepter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_kepper_list_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        imageConvater = new ImageConvater();

        ShopKeeperInfo shopKeeperInfo = shopKeeperInfoArrayList.get(position);
        holder.name.setText("Name: "+shopKeeperInfo.getsName());
        holder.contuct.setText("Phone: "+shopKeeperInfo.getsPhone());
        holder.area.setText("Area: "+shopKeeperInfo.getsArea());
        holder.road.setText("Road: "+shopKeeperInfo.getsRoad());
        holder.house.setText("House: "+shopKeeperInfo.getsNumber());


        if (shopKeeperInfo.getProfilePic() != null) {
            Bitmap returnImage = imageConvater.StringToBitMap(shopKeeperInfo.getProfilePic());
            holder.profileImageViewShopKeeperId.setImageBitmap(returnImage);
        }

    }

    @Override
    public int getItemCount() {
        return shopKeeperInfoArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView name,contuct,area,road,house;
        ImageView profileImageViewShopKeeperId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.shopNameView);
            contuct = itemView.findViewById(R.id.shopContuctView);
            area = itemView.findViewById(R.id.shopAreaView);
            road = itemView.findViewById(R.id.shopRoadView);
            house = itemView.findViewById(R.id.shopHouseView);
            profileImageViewShopKeeperId = itemView.findViewById(R.id.profileImageViewShopKeeperId);
        }
    }
}
