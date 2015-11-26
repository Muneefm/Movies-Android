package moviez.mnf.com.movie.tools;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import moviez.mnf.com.movie.R;

/**
 * Created by Muneef on 27/11/15.
 */
public class Utils {
    public static void loadImage(ImageView view, String url) {
        if(url!=null && !url.equals(""))
            Picasso.with(view.getContext())
                    .load(url)
                    .error(R.mipmap.ic_imgcan)
                    .placeholder(R.drawable.dwn)
                    .into(view);
    }

}
