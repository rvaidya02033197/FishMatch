package com.mobileapp.fishmatch;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobileapp.fishmatch.databinding.FragmentStatsBinding;
import com.mobileapp.fishmatch.databinding.FragmentWinBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class WinFragment extends Fragment {

    private FragmentWinBinding binding;

    private String timeTakenString = "Time Taken:";
    private String movesMadeString = "Turns Taken:";
    private String pointsEarnedString = "Points Earned:";

    private final Random rng = new Random();

    // List for final win quip
    private final List<String> gameCongrats = Arrays.asList(
            "Nice Matching Out There",
            "Bad Day to Be An Unmatched Fish, Nice Work",
            "You Matched Em All! ...For Now",
            "Way To Flip Those Tiles!",
            "That Oughtta Put Those Fish In Their Place",
            "Another Day, Another Fish Matched",
            "Whoa! Slow Down Cowboy, Horses Can't Swim!",
            "A Wise Man Once Said, \"A Day Without Matched Fish Is a Day Wasted\"",
            "The Game's Over? Already?",
            "Good Boys and Girls Eat Their Veggies and Match Their Fish"
            );
    // Separate List of Congratulations in case a there is a new high score
    private final List<String> gameHighScoreCongrats = Arrays.asList(
            "Wow That Really Blew My Fins Off!",
            "I Haven't Seen Matching Like That Since The Good Ol Days",
            "You Should Get A Trophy For Matching Like That, But I Don't Have One",
            "Fish Matchers Like You Are Plain Old Hard To Come By",
            "Record...SMASHED!",
            "Blub Blub Blub Blub Blub (That's Some High Fish Praise)",
            "I See Great Things In Your Future",
            "Stunning Work Today, I'm Proud of You"
    );

    public int randomIndex(int size) { return rng.nextInt(size); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentWinBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        boolean highScore = true;

        int turnsTaken = WinFragmentArgs.fromBundle(getArguments()).getMovesTaken();
        long timeTaken = WinFragmentArgs.fromBundle(getArguments()).getGameTime();
        int pointsEarned = WinFragmentArgs.fromBundle(getArguments()).getPointsEarned();
        timeTaken = TimeUnit.MILLISECONDS.toSeconds(timeTaken);

        if (!WinFragmentArgs.fromBundle(getArguments()).getTimeHighScore()) {
            binding.timeHighScoreImage.setAlpha(0f);
            highScore = false;
        }
        if (!WinFragmentArgs.fromBundle(getArguments()).getMovesHighScore()) {
            binding.movesHighScoreImage.setAlpha(0f);
            highScore = false;
        }
        if (highScore) {
            binding.winQuipText.setText(gameHighScoreCongrats.get(this.randomIndex(gameHighScoreCongrats.size())));
        } else {
            binding.winQuipText.setText(gameCongrats.get(this.randomIndex(gameCongrats.size())));
        }

        binding.timeStats.setText(String.format("%s %ds", timeTakenString, timeTaken));
        binding.moveStats.setText(String.format("%s %d", movesMadeString, turnsTaken));
        binding.pointsEarned.setText(String.format("%s %d", pointsEarnedString, pointsEarned));


        binding.backToStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to start
                Navigation.findNavController(v).navigate(R.id.action_winFragment_to_startFragment);
            }
        });

        return view;
    }
}