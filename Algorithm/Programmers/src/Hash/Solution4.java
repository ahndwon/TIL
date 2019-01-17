package Hash;

import java.util.*;

public class Solution4 {
    public int[] solution(String[] genres, int[] plays) {
        int[] answer = {};

        ArrayList<Song> songList = new ArrayList<>();
        HashMap<String, ArrayList<Song>> genreAlbum = new HashMap<>();
        HashMap<String, Integer> genrePlays = new HashMap<>();


        for (int i = 0; i < genres.length; i++) {
            songList.add(new Song(i, genres[i], plays[i]));
        }

        songList.sort(((o1, o2) ->
                Integer.compare(o2.plays, o1.plays)));


        for (int i = 0; i < genres.length; i++) {
            if (!genrePlays.containsKey(genres[i])) genrePlays.put(genres[i], plays[i]);
            else {
                genrePlays.replace(genres[i], genrePlays.get(genres[i]) + plays[i]);
            }
        }

        ArrayList<String> genrePlayKeys = new ArrayList<>(genrePlays.keySet());
        genrePlayKeys.sort(((o1, o2) -> {
            if (genrePlays.get(o1) > genrePlays.get(o2)) return -1;
            else if (genrePlays.get(o1) < genrePlays.get(o2)) return 1;
            else return 0;
        }));

        for (Song s : songList) {
            if (!genreAlbum.containsKey(s.genre)) {
                ArrayList<Song> list = new ArrayList<>();
                list.add(s);
                genreAlbum.put(s.genre, list);
            }
            else {
                ArrayList<Song> list = genreAlbum.get(s.genre);
                if (list.size() < 2) {
                    list.add(s);
                } else {
                    list.removeIf(song -> s.plays > song.plays);

                    if (list.size() < 2) {
                        list.add(s);
                    }
                }
            }
        }

        ArrayList<Integer> answerList = new ArrayList<>();

        for (String key : genrePlayKeys) {
            for (Song s : genreAlbum.get(key)) {
                answerList.add(s.id);
            }
        }

        answer = new int[answerList.size()];

        for (int i = 0; i < answerList.size(); i++) {
            answer[i] = answerList.get(i);
        }

        return answer;
    }

    class Song {
        int id;
        String genre;
        int plays;

        Song(int id, String genre, int plays) {
            this.id = id;
            this.genre = genre;
            this.plays = plays;
        }
    }
}
