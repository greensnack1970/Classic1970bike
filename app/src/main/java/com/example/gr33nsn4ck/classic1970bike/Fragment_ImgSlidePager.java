package com.example.gr33nsn4ck.classic1970bike;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class Fragment_ImgSlidePager extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private int position;

    private static final String ARG_PARAM2 = "param2";
    private String img_path;


    public static Fragment_ImgSlidePager newInstance(int position, String img_path) {
        Fragment_ImgSlidePager fragment = new Fragment_ImgSlidePager();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        args.putString(ARG_PARAM2, img_path);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragment_ImgSlidePager() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
            img_path = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_imgsliderpager, container, false);
        TextView txt_viewpager = (TextView) view.findViewById(R.id.txt_viewpager);
        ImageView img_viewpager = (ImageView) view.findViewById(R.id.img_viewpager);
        img_viewpager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShowFullScreenImageActivity.class);
                intent.putExtra("image_path", img_path);
                getActivity().startActivity(intent);
            }
        });

        txt_viewpager.setText("ภาพที่ : "+(position+1));
        Picasso.with(getActivity().getApplicationContext())
                .load(img_path)
                .into(img_viewpager);

        return view;
    }


}
