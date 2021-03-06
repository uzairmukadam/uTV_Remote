package utv.uzitech.umusic.ui.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

import utv.uzitech.umusic.PlaybackActivity;
import utv.uzitech.umusic.R;
import utv.uzitech.umusic.adapter.TracksAdapter;

public class TracksFragment extends Fragment {

    ListView musicList;

    ArrayList<String> allMusic, allArtworks;
    ArrayList<Bitmap> finArtworks;

    MediaMetadataRetriever metadataRetriever;

    MediaPlayer mediaPlayer;

    int prev;

    TracksFragment(MediaPlayer mediaPlayer, ArrayList<String> allMusic, ArrayList<String> allArtworks){
        this.allMusic = allMusic;
        this.mediaPlayer = mediaPlayer;
        this.allArtworks = allArtworks;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_tracks, container, false);

        musicList =view.findViewById(R.id.music_list);

        metadataRetriever = new MediaMetadataRetriever();

        final Intent intent = new Intent(getActivity(), PlaybackActivity.class);

        getArtwork();

        ListAdapter adapter = new TracksAdapter(Objects.requireNonNull(getActivity()), allMusic, finArtworks, metadataRetriever);
        musicList.setAdapter(adapter);

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("pos", i);
                intent.putExtra("allMusic", allMusic);
                intent.putExtra("allArtwork", allArtworks);
                startActivity(intent);
                /*
                try {
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        if(prev != i){
                            mediaPlayer.setDataSource(allMusic.get(i).split(", ")[3]);
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                        }
                    }else{
                        mediaPlayer.setDataSource(allMusic.get(i).split(", ")[3]);
                        prev = i;
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    }
                }catch (Exception ignored){}

                 */
            }
        });

        return view;
    }

    private void getArtwork() {
        finArtworks = new ArrayList<>();

        for(String path: allArtworks){
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            byte[] temp = retriever.getEmbeddedPicture();
            assert temp != null;
            finArtworks.add(BitmapFactory.decodeByteArray(temp, 0, temp.length));
        }
    }
}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     