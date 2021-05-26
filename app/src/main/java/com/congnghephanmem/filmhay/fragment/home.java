package com.congnghephanmem.filmhay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.congnghephanmem.filmhay.Adapter.RecyclerAnimeAdapter;
import com.congnghephanmem.filmhay.Adapter.RecyclerDanhMucAdapter;
import com.congnghephanmem.filmhay.Interface.InterfaceAnime;
import com.congnghephanmem.filmhay.Interface.InterfaceDanhMuc;
import com.congnghephanmem.filmhay.Model.Film;
import com.congnghephanmem.filmhay.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @BindView(R.id.imgSlide)
//    ImageSlider imgSlide;

    List<SlideModel> slideModels;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @BindView(R.id.recyler_danh_muc)
    RecyclerView recyclerView;
    RecyclerDanhMucAdapter recyclerDanhMucAdapter;
    public static ArrayList<Film> danhMucArrayList;
    @BindView(R.id.recyler_anime_hot)
    RecyclerView recyclerViewAnime;
    public static ArrayList<Film> animeHotArray;
    RecyclerAnimeAdapter recyclerAnimeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ImageSlider imgSlide = (ImageSlider) view.findViewById(R.id.imgSlide);
        slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://i.pinimg.com/originals/7d/c2/7d/7dc27da9630a0904b20fe876e058baf2.jpg","Mushoku tensei: isekai ittara honki dasu"));
        slideModels.add(new SlideModel("https://1.bp.blogspot.com/-PnPJYR7Vn-M/YA0Vt5n_OtI/AAAAAAAAFsQ/4663lXMT2hkLSc-rIpQJcH_iO9iqYIt1ACLcBGAsYHQ/s1600/5ffd804958ddd857b64afe8f_Slime-Datta-Ken-ss2-3.jpg","Slime chuyển sinh"));
        slideModels.add(new SlideModel("https://www.fullphim.net/static/5fe2d564b3fa6403ffa11d1c/5fe2d564b3fa64bb09a12fa4_chuyen-tau-vo-tan-5.jpg", "Chuyến tàu vô tận"));
        imgSlide.setImageList(slideModels,true);

        ButterKnife.bind(this, view);
        danhMucArrayList = new ArrayList<>();
        danhMucArrayList.add(new Film(101,R.drawable.hd1,"Hành động","",""));
        danhMucArrayList.add(new Film(102,R.drawable.hd2,"Phép thuật","",""));
        danhMucArrayList.add(new Film(103,R.drawable.hd3,"Isekai","",""));
        danhMucArrayList.add(new Film(104,R.drawable.hd4,"Đời thường","",""));
        danhMucArrayList.add(new Film(105,R.drawable.hd5,"Harem","",""));
        danhMucArrayList.add(new Film(106,R.drawable.hd6,"Học đường","",""));
        danhMucArrayList.add(new Film(107,R.drawable.hanhdong,"Thể thao","",""));
        danhMucArrayList.add(new Film(108,R.drawable.hanhdong,"Phiêu lưu","",""));
        danhMucArrayList.add(new Film(109,R.drawable.hanhdong,"Kinh dị","",""));
        danhMucArrayList.add(new Film(110,R.drawable.hanhdong,"Hài hước","",""));
        danhMucArrayList.add(new Film(111,R.drawable.hanhdong,"Tình cảm","",""));

        recyclerDanhMucAdapter = new RecyclerDanhMucAdapter(danhMucArrayList, getContext(), new InterfaceDanhMuc() {
            @Override
            public void click(int position) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerDanhMucAdapter);

        animeHotArray = new ArrayList<>();
//        animeHotArray.add(new Film(R.drawable.kimetsu_no_yaiba, "Kimetsu No Yaiba","Amazing, good job!!\nLO LẮNG\n" +
//                "Một phụ nữ nói với bác sĩ:\n" +
//                "\n" +
//                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
//                "\n" +
//                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
//                "\n" +
//                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(1,R.drawable.kimetsu_no_yaiba_mugen_train,"Mugen Train","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(2,R.drawable.naruto_shipuden, "Naruto shipuden","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(3,R.drawable.onepiece, "One piece","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(4,R.drawable.mushoku_tensei, "Mushoku Tensei","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(5,R.drawable.naruto, "Naruto","XẤU XÍ\n" +
                "Có hai vợ chồng nọ sống ở sâu dưới những ngọn đồi và rất hiếm khi thấy người qua lại. Một hôm, có người bán hàng rong đi qua, anh ta nhìn thấy ông chồng ngoài cửa nên vồn vã chào mời:\n" +
                "\n" +
                "- Ông hay bà nhà có muốn mua thứ gì không?\n" +
                "\n" +
                "- Vợ tôi không có ở nhà, nhưng mà anh có gì vậy?\n" +
                "\n" +
                "- Người bán hàng rong liền đưa ra các vật dụng gia đình, nào là chảo, xoong, nồi... Tuy nhiên, ông chồng chẳng chú ý lắm mà chỉ nhìn vào một cái gương. Ông chồng hỏi: Cái gì thế?\n" +
                "\n" +
                "- Đoạn ông ta cầm nó lên nhìn rồi nói tiếp: Ôi lạy Chúa hình của bố tôi đây mà, nó đẹp thật! Bán cho tôi cái này.\n" +
                "\n" +
                "- Sau khi mua chiếc gương, ông ta rất lo lắng vì sợ bà vợ keo kiệt biết mình đã bỏ ra rất nhiều tiền để mua một đồ vật vô bổ, ông ta liền giấu nó vào trong kho sau những thùng đồ.\n" +
                "\n" +
                "- Sau đó, ngày nào ông chồng cũng đến kho và nhìn vào tấm hình trong cái gương hai ba lần khiến bà vợ sinh nghi.\n" +
                "\n" +
                "- Thế là một ngày nọ bà chờ chồng đi ngủ rồi đi vào nhà kho và tìm thấy cái gương, bà ta cầm nó lên nhìn rồi nói thầm: Thì ra đây chính là mụ đàn bà xấu xí mà mấy hôm nay ông ta đang tán tỉnh!!!","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(6,R.drawable.slime, "Slime chuyển sinh","Amazing, good job!!","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        recyclerAnimeAdapter = new RecyclerAnimeAdapter(getContext(), animeHotArray, new InterfaceAnime() {
            @Override
            public void onClick(int positon) {

            }
        });


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewAnime.setLayoutManager(linearLayoutManager1);
        recyclerViewAnime.setAdapter(recyclerAnimeAdapter);
        return view;
    }
    public static Film serach(String n){
        Film film =new Film(0,0,"","","");
        for (int i=0;i<animeHotArray.size();i++){
            if (n.equals(animeHotArray.get(i).getTenTheLoai())){
                film =animeHotArray.get(i);
            }

        }

        return film;
    }

    public static Film serach_theloai(String n){
        Film film=new Film(0,0,"","","");
        for (int i=0;i<danhMucArrayList.size();i++){
            if (n.equals(danhMucArrayList.get(i).getTenTheLoai())){
                film =danhMucArrayList.get(i);
            }
        }
        return film;
    }

    public static ArrayList<Film> dm(){
        return animeHotArray;
    }
}