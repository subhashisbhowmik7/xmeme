package com.crio.starter.data;

import java.util.Comparator;

public class MemeDateComparator implements Comparator<Meme> {
    @Override
    public int compare(Meme meme1, Meme meme2) {
        return meme2.getCreatedAt().compareTo(meme1.getCreatedAt());
    }
}