package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public SongServiceImpl(SongRepository songRepository, ArtistRepository artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        Song existingSong = songRepository.findById(song.getTrackId())
                .orElseThrow(() -> new RuntimeException("Song not found"));
        Artist existingArtist = artistRepository.findById(artist.getId())
                .orElseThrow(() -> new RuntimeException("Artist not found"));

        List<Artist> performers = existingSong.getPerformers();
        performers.add(existingArtist);

        existingSong.setPerformers(performers);
        songRepository.save(existingSong);

        return existingArtist;
    }

    @Override
    public Song findByTrackId(Long trackId) {
        return songRepository.findByTrackId(trackId);
    }

    @Override
    public Optional<Song> save(String title, String genre, Integer releaseYear, Album album) {
        return Optional.of(songRepository.save(new Song(title, genre, releaseYear, album)));
    }

    @Override
    public void deleteById(Long id) {
        this.songRepository.deleteById(id);
    }

    @Override
    public List<Song> findAllByAlbumId(Long albumId) {
        return this.songRepository.findAllByAlbumId(albumId);
    }
}
