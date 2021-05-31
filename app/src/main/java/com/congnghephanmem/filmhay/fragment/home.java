package com.congnghephanmem.filmhay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    DatabaseReference reference;

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
        reference = FirebaseDatabase.getInstance().getReference();
        ButterKnife.bind(this, view);
        danhMucArrayList = new ArrayList<>();
        danhMucArrayList.add(new Film(101,"https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/App_TH%2F1616179013485?alt=media&token=b5d67e19-e6d5-410d-9321-19255c8b8af5","Hành động","",""));
        danhMucArrayList.add(new Film(102,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1621789030845.png?alt=media&token=d5fe7547-1fa9-44c2-9765-027bf4bfa140","Phép thuật","",""));
        danhMucArrayList.add(new Film(103,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1621788546837.png?alt=media&token=5313eac2-22d1-42ca-a99d-293ced4ed21e","Isekai","",""));
        danhMucArrayList.add(new Film(104,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1621788546837.png?alt=media&token=5313eac2-22d1-42ca-a99d-293ced4ed21e","Đời thường","",""));


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
        animeHotArray.add(new Film(1,"https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/App_TH%2F1616179013485?alt=media&token=b5d67e19-e6d5-410d-9321-19255c8b8af5","Mugen Train","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(2,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1621788546837.png?alt=media&token=5313eac2-22d1-42ca-a99d-293ced4ed21e", "Naruto shipuden","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(3,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1621789030845.png?alt=media&token=d5fe7547-1fa9-44c2-9765-027bf4bfa140", "One piece","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(4,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1621792159738.png?alt=media&token=0e83c650-9d22-434f-8100-f78dd7989676", "Mushoku Tensei","LO LẮNG\n" +
                "Một phụ nữ nói với bác sĩ:\n" +
                "\n" +
                "- Thưa bác sĩ, xin ông đừng giấu giếm tôi điều gì cả. Hãy nói tôi nghe tình trạng sức khoẻ của chồng tôi như thế nào sau khi bị gãy tay. Không biết rõ điều này thì tôi chết mất.\n" +
                "\n" +
                "- Bà yên tâm, ông ấy rồi sẽ khoẻ dần thôi mà.\n" +
                "\n" +
                "- Vậy có nghĩa là ông ấy sẽ rửa bát được chứ ạ?","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        animeHotArray.add(new Film(5,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1622050627363.png?alt=media&token=96870ac3-ebc8-4336-916a-4b7844314ea3", "Naruto","XẤU XÍ\n" +
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
        animeHotArray.add(new Film(6,"https://firebasestorage.googleapis.com/v0/b/appfilm-b6b73.appspot.com/o/image1621788546837.png?alt=media&token=5313eac2-22d1-42ca-a99d-293ced4ed21e", "Slime chuyển sinh","Amazing, good job!!","https://firebasestorage.googleapis.com/v0/b/tets-c58bb.appspot.com/o/Video_Social%2F1612513927367?alt=media&token=7f297cdb-cb59-4487-a06a-ae090b358993"));
        recyclerAnimeAdapter = new RecyclerAnimeAdapter(getContext(), animeHotArray, new InterfaceAnime() {
            @Override
            public void onClick(int positon) {

            }
        });


        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        recyclerViewAnime.setLayoutManager(linearLayoutManager1);
        recyclerViewAnime.setAdapter(recyclerAnimeAdapter);
        set_getdata_film_firebase();
        return view;
    }
    void set_getdata_film_firebase(){
        reference.child("Film").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Film f = snapshot.getValue(Film.class);
                animeHotArray.add(f);
                recyclerAnimeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static Film serach(String n){
        Film film =new Film(0,"","","","");
        for (int i=0;i<animeHotArray.size();i++){
            if (n.equals(animeHotArray.get(i).getTenTheLoai())){
                film =animeHotArray.get(i);
            }

        }

        return film;
    }

    public static Film serach_theloai(String n){
        Film film=new Film(0,"","","","");
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