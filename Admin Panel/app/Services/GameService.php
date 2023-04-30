<?php
namespace App\Services;

use App\Models\Game;

class GameService{


    public function updateGameReview($gameId){

        $game = Game::where('id', $gameId)->where('is_deleted', 0)->first();

        if($game){

            $game->rating = $game->reviews()->where('status', 'Active')->where('is_deleted', 0)->avg('rating');
            $game->rating_count = $game->reviews()->where('status', 'Active')->where('is_deleted', 0)->count();
            $game->save();

        }


    }

}
