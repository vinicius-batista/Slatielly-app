package com.example.slatielly.app.dress.registerDress;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.slatielly.R;
import com.example.slatielly.app.dress.DressContract;
import com.example.slatielly.view.dress.editPhotos.EditPhotosAdapter;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class EditPhotosDress extends Fragment
{
    private RecyclerView R_edit_photos_dress;
    private EditPhotosAdapter editPhotosAdapter;

    private RegisterDressContract.Presenter presenterRegister;
    private DressContract.Presenter presenterDress;

    private ArrayList<Bitmap> images;

    private MenuItem menuItem;

    @SuppressLint("ValidFragment")
    public EditPhotosDress(RegisterDressContract.Presenter presenterRegister, DressContract.Presenter presenterDress)
    {
        this.presenterDress = presenterDress;
        this.presenterRegister = presenterRegister;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return inflater.inflate( R.layout.fragment_edit_photos_dress, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_edit_photo, menu);
        menuItem = menu.findItem(R.id.trash_image);
        menuItem.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        R_edit_photos_dress = (RecyclerView) view.findViewById(R.id.R_edit_photos_dress);

        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration();

        R_edit_photos_dress.addItemDecoration(verticalSpaceItemDecoration);

        if(this.presenterDress == null)
        {
            this.images = this.presenterRegister.getImages();
        }
        else
        {

        }

        editPhotosAdapter = new EditPhotosAdapter(this.images,this);
        R_edit_photos_dress.setAdapter(editPhotosAdapter);

        GridLayoutManager manager = new GridLayoutManager(this.getContext(),3,GridLayoutManager.VERTICAL,false);

        R_edit_photos_dress.setLayoutManager(manager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == R.id.trash_image)
        {
            if(this.presenterDress == null)
            {
                presenterRegister.updateImages(editPhotosAdapter.getImagesDeleteRegister());
                editPhotosAdapter.setImagesDeleteRegister(new ArrayList<Bitmap>());
                editPhotosAdapter.setImages(presenterRegister.getImages());
                menuItemInvisible();
                editPhotosAdapter.notifyDataSetChanged();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void menuItemVisible()
    {
        menuItem.setVisible(true);
    }

    public void menuItemInvisible()
    {
        menuItem.setVisible(false);
    }

    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration
    {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
        {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.top = 10;
            outRect.bottom = 0;
            outRect.right = 10;
            outRect.left = 10;

            if(parent.getChildAdapterPosition(view)%3 != 0)
            {
                outRect.left = 0;
            }
        }
    }
}
