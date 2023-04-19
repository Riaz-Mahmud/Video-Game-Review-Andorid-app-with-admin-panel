<?php

namespace App\Http\Controllers\API\Game;

use App\Helpers\ImageHelper;
use App\Http\Controllers\API\APIController;
use App\Http\Controllers\Controller;
use App\Models\Game;
use Illuminate\Http\Request;

class GameController extends APIController
{
    public function __construct(){

        $this->middleware('auth:api');

    }

    public function list(Request $request){

        $games = Game::where('status', 'Active')->where('is_deleted', 0)->latest('id')->get();

        foreach($games as $game){
            $game->banner = ImageHelper::generateImage($game->banner, 'medium');
        }

        return parent::apiResponse(true, null, $games);
    }

    public function details(Request $request, $id){

        $currentUser = auth('api')->user();

        $game = Game::where('id', $id)->where('status', 'Active')->where('is_deleted', 0)->first();

        if($game){
            $game->banner = ImageHelper::generateImage($game->banner, 'medium');

            $allReviews = $game->reviews()->where('status', 'Active')->where('is_deleted', 0)->get()->sortByDesc('id');

            $reviews = array();

            foreach($allReviews as $review){
                if($review->user_id == $currentUser->id){
                    $review->is_mine = true;
                }else{
                    $review->is_mine = false;
                }
                $review->user = $review->user()->first()->profile()->first();

                $reviews[] = $review;
            }

            // sort $reviews by the currentuser id
            usort($reviews, function($a, $b) use ($currentUser) {
                return $a->user_id == $currentUser->id ? -1 : 1;
            });

            in_array($currentUser->id, $allReviews->pluck('user_id')->toArray()) ? $game->is_mine_review = true : $game->is_mine_review = false;


            $game->reviews = $reviews;

            return parent::apiResponse(true, null, $game);
        }else{
            return parent::apiResponse(false, 'Game not found', null);
        }

    }
}
